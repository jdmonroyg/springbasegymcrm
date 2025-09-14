package com.epam.controller;

import com.epam.dto.response.TrainingTypeResponseDto;
import com.epam.exception.GlobalExceptionHandler;
import com.epam.service.TrainingTypeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author jdmon on 11/09/2025
 * @project springbasegymcrm
 */
@ExtendWith(MockitoExtension.class)
class TrainingTypeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingTypeController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    @DisplayName("GET with header Authorization 200")
    void getTrainingTypesSuccess() throws Exception {
        List<TrainingTypeResponseDto> types = List.of(
                new TrainingTypeResponseDto(1L,"Cardio"),
                new TrainingTypeResponseDto(2L,"Cardio")
        );
        String token = "Bearer "+ UUID.randomUUID();

        when(trainingTypeService.getAllTrainingTypes(token)).thenReturn(types);

        mockMvc.perform(get("/trainingTypes")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("GET without header Authorization 400")
    void getTrainingTypesFailed() throws Exception {
        mockMvc.perform(get("/trainingTypes"))
                .andExpect(status().isUnauthorized());
    }
}