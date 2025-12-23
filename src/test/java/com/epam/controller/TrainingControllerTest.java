package com.epam.controller;

import com.epam.dto.request.CreateTrainingRequestDto;
import com.epam.exception.GlobalExceptionHandler;
import com.epam.exception.NotFoundException;
import com.epam.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 14/09/2025
 * @project springbasegymcrm
 */
@ExtendWith(MockitoExtension.class)
class TrainingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;

    private LocalDate trainingDate;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(trainingController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        trainingDate = LocalDate.now().plusDays(10);
    }

    @Test
    @DisplayName("POST createTraining 200")
    void createTraining() throws Exception {
        String token = getToken();

        String body = """
        {
          "traineeUsername": "Jesus.Monroy",
          "trainerUsername": "Carlos.Perez",
          "trainingName": "Cardio Blast",
          "trainingDate": "%s",
          "durationInMinutes": 60
        }
        """.formatted(trainingDate);

        doNothing().when(trainingService)
                .createTraining(any(CreateTrainingRequestDto.class));

        mockMvc.perform(post("/trainings")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST createTraining 400")
    void createTrainingInvalidPayload() throws Exception {
        String token = getToken();

        String badJson = """
        {
          "traineeUsername": "",
          "trainerUsername": "",
          "trainingName": "",
          "trainingDate": null,
          "durationInMinutes": 10
        }
        """;

        mockMvc.perform(post("/trainings")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST createTraining 404")
    void createTrainingUserNotFound() throws Exception {
        String token = "Bearer valid-token";

        String jsonRequest = """
        {
          "traineeUsername": "unknown.trainee",
          "trainerUsername": "unknown.trainer",
          "trainingName": "Cardio Blast",
          "trainingDate": "%s",
          "durationInMinutes": 60
        }
        """.formatted(trainingDate);

        doThrow(new NotFoundException("Trainee or Trainer not found"))
                .when(trainingService)
                .createTraining(any(CreateTrainingRequestDto.class));

        mockMvc.perform(post("/trainings")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }

    private static String getToken() {
        return "Bearer " + UUID.randomUUID();
    }

}