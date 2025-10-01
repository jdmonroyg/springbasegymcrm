//package com.epam.controller;
//
//import com.epam.dto.request.CreateTraineeRequestDto;
//import com.epam.dto.request.PatchUserRequestDto;
//import com.epam.dto.request.TraineeTrainingsFilterRequestDto;
//import com.epam.dto.request.UpdateTraineeRequestDto;
//import com.epam.dto.response.*;
//import com.epam.exception.GlobalExceptionHandler;
//import com.epam.service.TraineeService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Set;
//import java.util.UUID;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * @author jdmon on 11/09/2025
// * @project springbasegymcrm
// */
//@ExtendWith(MockitoExtension.class)
//class TraineeControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private TraineeService traineeService;
//
//    @InjectMocks
//    private TraineeController controller;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(controller)
//                .setControllerAdvice(new GlobalExceptionHandler()).build();
//    }
//
//    @Test
//    @DisplayName("POST createTrainee 200")
//    void createTraineeSuccess() throws Exception {
//        CreateTraineeRequestDto reqDto = new CreateTraineeRequestDto("Juan","Monroy",
//                "juanmonroy@gmail.com",null,null);
//        CreateUserResponseDto responseDto = new CreateUserResponseDto("Juan.Monroy",
//                "aSiG05Xop1");
//
//        when(traineeService.createTrainee(reqDto)).thenReturn(responseDto);
//
//        String body = """
//            {
//            "firstName":"Juan",
//            "lastName":"Monroy",
//            "email":"juanmonroy@gmail.com"
//            }
//            """;
//        mockMvc.perform(post("/trainees")
//                        .contentType("application/json")
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//        ;
//    }
//
//    @Test
//    @DisplayName("POST createTrainee with bad payload 400 ")
//    void createTrainee_invalidPayload_returns400() throws Exception {
//        String body = """
//            {
//            "firstName":"Juan"
//            }
//            """;
//        mockMvc.perform(post("/trainees")
//                        .contentType("application/json")
//                        .content(body))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("GET trainee username 200")
//    void getTraineeByUsername_success() throws Exception {
//        TraineeResponseDto respDto = new TraineeResponseDto("Juan","Monroy",
//                "juanmonroy@gmail.com",null, null, true,
//                Set.of()
//        );
//        String token = getToken();
//
//        when(traineeService.selectTraineeByUsername(token, "Juan.Monroy"))
//                .thenReturn(respDto);
//
//        mockMvc.perform(get("/trainees/Juan.Monroy")
//                        .header("Authorization", token))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.firstName").value("Juan"))
//                .andExpect(jsonPath("$.lastName").value("Monroy"))
//                .andExpect(jsonPath("$.active").value(true))
//                .andExpect(jsonPath("$.trainers").isArray());
//    }
//
//    @Test
//    @DisplayName("DELETE Trainee username 204")
//    void deleteTrainee_success() throws Exception {
//        String token = getToken();
//        doNothing().when(traineeService).deleteTrainee(token, "Juan.Monroy");
//
//        mockMvc.perform(delete("/trainees/Juan.Monroy")
//                        .header("Authorization", token))
//                .andExpect(status().isNoContent());
//    }
//
//
//
//    @Test
//    @DisplayName("Update Trainee 200")
//    void updateTrainee() throws Exception {
//        String token = getToken();
//
//        UpdateTraineeRequestDto req = new UpdateTraineeRequestDto("Jesus.Monroy",
//                "Jesus", "Monroy",null,null, false);
//
//        String body = """
//            {
//            "username": "Jesus.Monroy",
//            "firstName": "Jesus",
//            "lastName": "Monroy",
//            "active": false
//            }
//            """;
//
//        TraineeUpdatedResponseDto resp = new TraineeUpdatedResponseDto("Jesus.Monroy",
//                new TraineeResponseDto("Jesus","Monroy","jd@gmail.com",
//                        null,null,false, Set.of()));
//
//        when(traineeService.updateTrainee(token,req)).thenReturn(resp);
//
//        mockMvc.perform(put("/trainees")
//                        .header("Authorization", token)
//                        .contentType("application/json")
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.traineeResponseDto.firstName").value("Jesus"))
//                .andExpect(jsonPath("$.traineeResponseDto.lastName").value("Monroy"))
//                .andExpect(jsonPath("$.traineeResponseDto.email").value("jd@gmail.com"))
//                .andExpect(jsonPath("$.traineeResponseDto.active").value(false));
//    }
//
//    @Test
//    @DisplayName("Update active status Trainee 204")
//    void changeActiveStatus() throws Exception {
//        String token = getToken();
//        PatchUserRequestDto req = new PatchUserRequestDto("Pablo.Miranda", false);
//        String body = """
//            {
//            "username": "Pablo.Miranda",
//            "active": false
//            }
//            """;
//        doNothing().when(traineeService).changeActiveStatus(token, req);
//
//        mockMvc.perform(patch("/trainees")
//                        .header("Authorization", token)
//                        .contentType("application/json")
//                        .content(body))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @DisplayName("Get trainees username trainings 200")
//    void getTraineeByUsername() throws Exception {
//        String token = getToken();
//        String username = "Jesus.Monroy";
//
//        List<TraineeTrainingsResponseDto> resp = getTraineeTrainingsResponseDto();
//
//        when(traineeService.getTraineeTrainings(eq(token), eq(username),
//                any(TraineeTrainingsFilterRequestDto.class)))
//                .thenReturn(resp);
//
//        mockMvc.perform(get("/trainees/{username}/trainings", username)
//                .header("Authorization", token)
//                .param("periodFrom", "2025-09-01")
//                .param("periodTo", "2025-09-30")
//                .param("trainerName", "Carlos")
//                .param("trainingTypeId", "2"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].trainingName").value("Morning Cardio"))
//                .andExpect(jsonPath("$[0].trainingType.id").value(2))
//                .andExpect(jsonPath("$[0].trainingType.name").value("Cardio"))
//                .andExpect(jsonPath("$[1].trainingName").value("Evening Strength"))
//                .andExpect(jsonPath("$[1].trainingType.id").value(1))
//                .andExpect(jsonPath("$[1].trainingType.name").value("Strength"));
//    }
//
//    private static String getToken() {
//        return "Bearer " + UUID.randomUUID();
//    }
//
//    private static List<TraineeTrainingsResponseDto> getTraineeTrainingsResponseDto() {
//        TraineeTrainingsResponseDto traineeTraining1 = new TraineeTrainingsResponseDto(
//                "Morning Cardio",
//                LocalDate.of(2025, 9, 22),
//                new TrainingTypeResponseDto(2L, "Cardio"),
//                60,
//                "Carlos"
//        );
//
//        TraineeTrainingsResponseDto traineeTraining2 = new TraineeTrainingsResponseDto(
//                "Evening Strength",
//                LocalDate.of(2025, 9, 19),
//                new TrainingTypeResponseDto(1L, "Strength"),
//                45,
//                "Ana"
//        );
//
//        return List.of(traineeTraining1, traineeTraining2);
//    }
//}