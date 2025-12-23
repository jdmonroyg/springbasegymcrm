package com.epam.controller;

import com.epam.dto.request.CreateTrainingRequestDto;
import com.epam.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
@RestController
@RequestMapping(value = "/trainings", //consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainingController {

    private final TrainingService trainingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingController.class);

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    @Operation(summary = "Create a new training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request: one or more required fields " +
                    "are missing or contain invalid values"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "404", description = "Trainee or Trainer not found")
    })
    public ResponseEntity<Void> createTraining(@RequestBody @Valid CreateTrainingRequestDto trainingRequest) {
        LOGGER.info("creating a training");
        trainingService.createTraining(trainingRequest);
        LOGGER.info("A training was created");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Training deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: user does not have the required role"),
            @ApiResponse(responseCode = "404", description = "Training not found")
    })
    public ResponseEntity<Void> deleteTraining(@PathVariable("id")Long id){
        LOGGER.info("Deleting a training");
        trainingService.deleteTraining(id);
        LOGGER.info("A training was deleting");
        return ResponseEntity.noContent().build();
    }


}
