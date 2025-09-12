package com.epam.controller;

import com.epam.dto.request.CreateTraineeRequestDto;
import com.epam.dto.response.CreateUserResponseDto;
import com.epam.dto.response.TraineeResponseDto;
import com.epam.service.TraineeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author jdmon on 11/09/2025
 * @project springbasegymcrm
 */
class TraineeControllerTest {

    private MockMvc mockMvc;

    private AutoCloseable closeable;

    @Mock
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        TraineeController controller = new TraineeController(traineeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("POST createTrainee 200")
    void createTraineeSuccess() throws Exception {
        CreateTraineeRequestDto reqDto = new CreateTraineeRequestDto("Juan","Monroy",
                "juanmonroy@gmail.com",null,null);
        CreateUserResponseDto responseDto = new CreateUserResponseDto("Juan.Monroy",
                "aSiG05Xop1");

        when(traineeService.createTrainee(reqDto)).thenReturn(responseDto);

        String body = """
            {
            "firstName":"Juan",
            "lastName":"Monroy",
            "email":"juanmonroy@gmail.com"
            }
            """;
        mockMvc.perform(post("/trainees")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
        ;
    }

    @Test
    @DisplayName("POST createTrainee with bad payload 400 ")
    void createTrainee_invalidPayload_returns400() throws Exception {
        String body = """
            {
            "firstName":"Juan"
            }
            """;
        mockMvc.perform(post("/trainees")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET trainee username 200")
    void getTraineeByUsername_success() throws Exception {
        TraineeResponseDto respDto = new TraineeResponseDto("Juan","Monroy",
                "juanmonroy@gmail.com",null, null, true,
                Set.of()
        );
        String token = getToken();

        when(traineeService.selectTraineeByUsername(token, "Juan.Monroy"))
                .thenReturn(respDto);

        mockMvc.perform(get("/trainees/Juan.Monroy")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Monroy"))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.trainers").isArray());
    }

    @Test
    @DisplayName("DELETE Trainee username 204")
    void deleteTrainee_success() throws Exception {
        String token = getToken();
        doNothing().when(traineeService).deleteTrainee(token, "Juan.Monroy");

        mockMvc.perform(delete("/trainees/Juan.Monroy")
                        .header("Authorization", token))
                .andExpect(status().isNoContent());
    }

    private static String getToken() {
        return "Bearer " + UUID.randomUUID();
    }

}