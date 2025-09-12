package com.epam.controller;

import com.epam.dto.response.TrainingTypeResponseDto;
import com.epam.service.TrainingTypeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
class TrainingTypeControllerTest {

    private MockMvc mockMvc;
    private TrainingTypeController controller;

    private AutoCloseable closeable;

    @Mock
    private TrainingTypeService trainingTypeService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        controller = new TrainingTypeController(trainingTypeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
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
                .andExpect(status().isBadRequest());
    }
}