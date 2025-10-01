package com.epam.service;

import com.epam.dto.request.*;
import com.epam.dto.response.*;

import java.util.List;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
public interface TrainerService {
    CreateUserResponseDto createTrainer(CreateTrainerRequestDto trainerRequest);
    TrainerResponseDto selectTrainerByUsername(String username);
    TrainerUpdatedResponseDto updateTrainer(String username, UpdateTrainerRequestDto dto);
    void changeActiveStatus(String username, PatchUserRequestDto requestDto);
    List<TrainerTrainingsResponseDto> getTrainerTrainings(String username, TrainerTrainingsFilterRequestDto filterDto);
    List<TrainersResponseDto> getUnassignedTrainersByTraineeUsername(String traineeUsername);
}
