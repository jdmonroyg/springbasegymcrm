package com.epam.controller;

import com.epam.dto.response.TrainingTypeResponseDto;
import com.epam.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author jdmon on 9/09/2025
 * @project springbasegymcrm
 */
@RestController
@RequestMapping(value = "/trainingTypes",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeController.class);

    public TrainingTypeController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @GetMapping
    @Operation(summary = "Get trainings type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of TrainingsType"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token")
    })
    public ResponseEntity<List<TrainingTypeResponseDto>> getTrainingsType (@RequestHeader("Authorization") String token){
        LOGGER.info("Getting a trainingTypes in the app");
        return ResponseEntity.ok(trainingTypeService.getAllTrainingTypes(token));
    }
}
