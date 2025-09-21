package com.epam.controller;

import com.epam.dto.request.*;
import com.epam.dto.response.*;
import com.epam.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
@RestController
@RequestMapping(value = "/trainers", //consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainerController {

    private final TrainerService trainerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerController.class);

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    @Operation(summary = "Create a new trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request: one or more required fields " +
                    "are missing or contain invalid values"),
            @ApiResponse(responseCode = "404", description = "TrainingType not found"),
            @ApiResponse(responseCode = "422", description = "Email is already associated with an existing user")
    })
    public ResponseEntity<CreateUserResponseDto> createTrainer(@RequestBody @Valid CreateTrainerRequestDto trainerRequest) {
        TrainerController.LOGGER.info("creating a trainer");
        CreateUserResponseDto responseDto = trainerService.createTrainer(trainerRequest);
        TrainerController.LOGGER.info("A trainer was created");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get trainer by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer found and returned successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "404", description = "Trainer not found")
    })
    public ResponseEntity<TrainerResponseDto> getTrainerByUsername(@RequestHeader("Authorization") String token,
                                                                   @PathVariable("username") String username) {
        LOGGER.info("Getting a trainer with your list of trainers");
        TrainerResponseDto responseDto = trainerService.selectTrainerByUsername(token, username);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    @Operation(summary = "Update a trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request: one or more required fields " +
                    "are missing or contain invalid values"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "404", description = "Trainer or TrainingType not found")
    })
    public ResponseEntity<TrainerUpdatedResponseDto> updateTrainer(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid UpdateTrainerRequestDto trainerRequest) {
        LOGGER.info("Updating a trainer");
        TrainerUpdatedResponseDto responseDto = trainerService.updateTrainer(token, trainerRequest);
        LOGGER.info("A trainer was updated");
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping
    @Operation(summary = "Change the active status of a trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trainer patched successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "404", description = "trainer not found")
    })
    public ResponseEntity<Void> changeActiveStatus(@RequestHeader("Authorization") String token,
                                                   @RequestBody @Valid PatchUserRequestDto requestDto) {
        LOGGER.info("Changing the active status of a trainer");
        trainerService.changeActiveStatus(token, requestDto);
        LOGGER.info("The active status of a trainer was changing");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/trainings")
    @Operation(summary = "Get trainer trainings by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer trainings list was successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "404", description = "Trainer not found")
    })
    public ResponseEntity<List<TrainerTrainingsResponseDto>> getTrainerTrainingsByUsername(
            @RequestHeader("Authorization") String token, @PathVariable("username") String username,
            @ModelAttribute TrainerTrainingsFilterRequestDto filterDto) {
        LOGGER.info("Getting a trainer trainings");
        List<TrainerTrainingsResponseDto> responseDto = trainerService.getTrainerTrainings(token, username,
                filterDto);
        LOGGER.info("The trainer trainings list was getting");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/unassigned/{traineeUsername}")
    @Operation(summary = "Get not assigned on trainee active trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unassigned Active Trainers by trainee username"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<List<TrainersResponseDto>> getUnassignedActiveTrainers(
            @RequestHeader("Authorization") String token, @PathVariable("traineeUsername") String traineeUsername) {
        LOGGER.info("Get a list of active trainers not assigned to the trainee username");
        List<TrainersResponseDto> responseDto = trainerService
                .getUnassignedTrainersByTraineeUsername(token, traineeUsername);
        LOGGER.info("The list of active trainer was getting");
        return ResponseEntity.ok(responseDto);
    }



}
