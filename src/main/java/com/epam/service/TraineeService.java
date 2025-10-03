package com.epam.service;

import com.epam.dto.request.CreateTraineeRequestDto;
import com.epam.dto.request.PatchUserRequestDto;
import com.epam.dto.request.TraineeTrainingsFilterRequestDto;
import com.epam.dto.request.UpdateTraineeRequestDto;
import com.epam.dto.response.CreateUserResponseDto;
import com.epam.dto.response.TraineeResponseDto;
import com.epam.dto.response.TraineeUpdatedResponseDto;
import com.epam.dto.response.TraineeTrainingsResponseDto;

import java.util.List;

/**
 * @author jdmon on 15/08/2025
 * @project springbasegymcrm
 */
public interface TraineeService {

    CreateUserResponseDto createTrainee(CreateTraineeRequestDto traineeRequest);
    TraineeResponseDto selectTraineeByUsername(String username);
    void deleteTrainee(String username);
    TraineeUpdatedResponseDto updateTrainee(String username, UpdateTraineeRequestDto traineeRequest);
    void changeActiveStatus(String username, PatchUserRequestDto requestDto);
    List<TraineeTrainingsResponseDto> getTraineeTrainings(String username, TraineeTrainingsFilterRequestDto filterDto);
}
