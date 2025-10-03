package com.epam.controller;

import com.epam.dto.request.CreateTrainerRequestDto;
import com.epam.dto.request.PatchUserRequestDto;
import com.epam.dto.request.TrainerTrainingsFilterRequestDto;
import com.epam.dto.request.UpdateTrainerRequestDto;
import com.epam.dto.response.*;
import com.epam.exception.GlobalExceptionHandler;
import com.epam.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author jdmon on 13/09/2025
 * @project springbasegymcrm
 */
@ExtendWith(MockitoExtension.class)
class TrainerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    void setUp() {
        UserDetails userDetails = new User(
                "Jesus.Monroy",
                "irrelevant-password",
                List.of(new SimpleGrantedAuthority("ROLE_TRAINER"))
        );
        var auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        mockMvc = MockMvcBuilders
                .standaloneSetup(trainerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .defaultRequest(get("/")
                        .principal(auth))
                .build();
    }

    @Test
    @DisplayName("POST create trainer 200")
    void createTrainer() throws Exception {
        CreateUserResponseDto responseDto = new CreateUserResponseDto("Jesus.Chacon", "3securePas");

        when(trainerService.createTrainer(any(CreateTrainerRequestDto.class)))
                .thenReturn(responseDto);

        String body = """
            {
              "firstName": "Jesus",
              "lastName": "Chacon",
              "email": "Jesus.Chacon@example.com",
              "specializationId": 2
            }
            """;

        mockMvc.perform(post("/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Jesus.Chacon"))
                .andExpect(jsonPath("$.password").value("3securePas"));
    }

    @Test
    @DisplayName("GET getTrainerByUsername 200")
    void getTrainerByUsername() throws Exception {
        String token = getToken();
        String username = "carlos.perez";

        TrainerResponseDto responseDto = new TrainerResponseDto(
                "Carlos",
                "Perez",
                "carlos.perez@example.com",
                new TrainingTypeResponseDto(2L, "Cardio"),
                true,
                Set.of()
        );

        when(trainerService.selectTrainerByUsername(username))
                .thenReturn(responseDto);

        mockMvc.perform(get("/trainers/{username}", username)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Carlos"))
                .andExpect(jsonPath("$.lastName").value("Perez"))
                .andExpect(jsonPath("$.email").value("carlos.perez@example.com"))
                .andExpect(jsonPath("$.specialization.name").value("Cardio"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("PUT updateTrainer 200")
    void updateTrainer() throws Exception {

        TrainerUpdatedResponseDto updatedResponse = new TrainerUpdatedResponseDto(
                "Jesus.Monroy",
                new TrainerResponseDto(
                        "Carlos",
                        "Perez",
                        "carlos.perez@example.com",
                        new TrainingTypeResponseDto(1L, "Strength"),
                        true,
                        Set.of()
                )
        );

        when(trainerService.updateTrainer(eq("Jesus.Monroy"), any(UpdateTrainerRequestDto.class)))
                .thenReturn(updatedResponse);

        String body = """
            {
            "username": "Jesus.Monroy",
            "firstName": "Carlos",
            "lastName": "Perez",
            "specializationId": 2,
            "active": true
            }
            """;

        mockMvc.perform(put("/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Jesus.Monroy"))
                .andExpect(jsonPath("$.trainerResponseDto.firstName").value("Carlos"))
                .andExpect(jsonPath("$.trainerResponseDto.lastName").value("Perez"))
                .andExpect(jsonPath("$.trainerResponseDto.specialization.id").value(1))
                .andExpect(jsonPath("$.trainerResponseDto.specialization.name").value("Strength"))
                .andExpect(jsonPath("$.trainerResponseDto.active").value(true));
    }

    @Test
    @DisplayName("PATCH changeActiveStatus 204")
    void changeActiveStatus() throws Exception {
        String token = getToken();
        PatchUserRequestDto req = new PatchUserRequestDto("Jesus.Monroy", false);
        doNothing().when(trainerService)
                .changeActiveStatus("Jesus.Monroy", req);
        String body = """
        {
          "username": "Jesus.Monroy",
          "active": false
        }
        """;
        mockMvc.perform(patch("/trainers")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET getTrainerTrainingsByUsername 200")
    void getTrainerTrainingsByUsername() throws Exception {
        String username = "Carlos.Miranda";

        TrainerTrainingsResponseDto training1 = new TrainerTrainingsResponseDto(
                "Morning Cardio",
                LocalDate.of(2025, 9, 10),
                new TrainingTypeResponseDto(2L, "Cardio"),
                60,
                "Juan"
        );

        TrainerTrainingsResponseDto training2 = new TrainerTrainingsResponseDto(
                "Evening Strength",
                LocalDate.of(2025, 9, 11),
                new TrainingTypeResponseDto(1L, "Strength"),
                45,
                "Pedro"
        );

        when(trainerService.getTrainerTrainings(eq(username),any(TrainerTrainingsFilterRequestDto.class)))
                .thenReturn(List.of(training1, training2));

        mockMvc.perform(get("/trainers/{username}/trainings", username)
                        .param("periodFrom", "2025-09-01")
                        .param("periodTo", "2025-09-30")
                        .param("traineeName", "Juan")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].trainingName").value("Morning Cardio"))
                .andExpect(jsonPath("$[0].trainingType.id").value(2))
                .andExpect(jsonPath("$[0].trainingType.name").value("Cardio"))
                .andExpect(jsonPath("$[1].trainingName").value("Evening Strength"))
                .andExpect(jsonPath("$[1].trainingType.id").value(1))
                .andExpect(jsonPath("$[1].trainingType.name").value("Strength"));
    }

    @Test
    @DisplayName("GET getUnassignedActiveTrainers 200")
    void getUnassignedActiveTrainers() throws Exception {
        String token = getToken();
        String traineeUsername = "Jesus.Monroy";

        TrainersResponseDto trainer1 = new TrainersResponseDto(
                "Carlos.Perez",
                "Carlos",
                "Perez",
                "carlos.perez@example.com",
                new TrainingTypeResponseDto(1L, "Strength")
        );

        TrainersResponseDto trainer2 = new TrainersResponseDto(
                "ana.gomez",
                "Ana",
                "Gomez",
                "ana.gomez@example.com",
                new TrainingTypeResponseDto(2L, "Cardio")
        );

        when(trainerService.getUnassignedTrainersByTraineeUsername(traineeUsername))
                .thenReturn(List.of(trainer1, trainer2));

        mockMvc.perform(get("/trainers/unassigned/{traineeUsername}", traineeUsername)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("Carlos.Perez"))
                .andExpect(jsonPath("$[0].specialization.id").value(1))
                .andExpect(jsonPath("$[0].specialization.name").value("Strength"))
                .andExpect(jsonPath("$[1].username").value("ana.gomez"))
                .andExpect(jsonPath("$[1].specialization.id").value(2))
                .andExpect(jsonPath("$[1].specialization.name").value("Cardio"));
    }

    private static String getToken() {
        return "Bearer " + UUID.randomUUID();
    }


}