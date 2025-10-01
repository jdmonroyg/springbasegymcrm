package com.epam.controller;

import com.epam.dto.request.CreateTraineeRequestDto;
import com.epam.dto.request.PatchUserRequestDto;
import com.epam.dto.request.TraineeTrainingsFilterRequestDto;
import com.epam.dto.request.UpdateTraineeRequestDto;
import com.epam.dto.response.CreateUserResponseDto;
import com.epam.dto.response.TraineeResponseDto;
import com.epam.dto.response.TraineeUpdatedResponseDto;
import com.epam.dto.response.TraineeTrainingsResponseDto;
import com.epam.service.TraineeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
@RestController
@RequestMapping(value = "/trainees", //consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class TraineeController {

    private final TraineeService traineeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeController.class);

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping
    @Operation(summary = "Create a new trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request: one or more required fields " +
                    "are missing or contain invalid values"),
            @ApiResponse(responseCode = "422", description = "Email is already associated with an existing user")
    })
    public ResponseEntity<CreateUserResponseDto> createTrainee(@RequestBody @Valid CreateTraineeRequestDto traineeRequest) {
        LOGGER.info("creating a trainee");
        CreateUserResponseDto responseDto = traineeService.createTrainee(traineeRequest);
        LOGGER.info("A trainee was created");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get trainee by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee found and returned successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: user does not have the required role"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<TraineeResponseDto> getTraineeByUsername(@PathVariable("username") String username) {
        LOGGER.info("Getting a trainee with your list of trainers");
        TraineeResponseDto responseDto = traineeService.selectTraineeByUsername(username);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    @Operation(summary = "Update a trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request: one or more required fields " +
                    "are missing or contain invalid values"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: user does not have the required role"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<TraineeUpdatedResponseDto> updateTrainee(@AuthenticationPrincipal UserDetails user,
                                                                   @RequestBody @Valid UpdateTraineeRequestDto traineeRequest) {
        LOGGER.info("Updating a trainee");
        TraineeUpdatedResponseDto responseDto = traineeService.updateTrainee(user.getUsername(), traineeRequest);
        LOGGER.info("A trainee was updated");
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Delete a trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trainee deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: user does not have the required role"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<Void> DeleteTrainee(@PathVariable("username") String username) {
        LOGGER.info("Deleting a trainee");
        traineeService.deleteTrainee(username);
        LOGGER.info("A trainee was deleting");
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    @Operation(summary = "Change the active status of a trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trainee Patched successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: user does not have the required role"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<Void> changeActiveStatus(@AuthenticationPrincipal UserDetails user,
                                              @RequestBody @Valid PatchUserRequestDto requestDto) {
        LOGGER.info("Changing the active status of a trainee");
        traineeService.changeActiveStatus(user.getUsername(), requestDto);
        LOGGER.info("The active status of a trainee was changing");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/trainings")
    @Operation(summary = "Get trainee trainings by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee trainings list was successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: user does not have the required role"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    public ResponseEntity<List<TraineeTrainingsResponseDto>>  getTraineeTrainingsByUsername(
            @PathVariable("username") String username,
                         @ModelAttribute TraineeTrainingsFilterRequestDto filterDto) {
        LOGGER.info("Getting a trainee trainings");
        List<TraineeTrainingsResponseDto> responseDto = traineeService.getTraineeTrainings(username, filterDto);
        LOGGER.info("The trainee trainings list was getting");
        return ResponseEntity.ok(responseDto);
    }


}
