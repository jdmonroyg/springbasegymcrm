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
    TrainerResponseDto selectTrainerByUsername(String token, String username);
    TrainerUpdatedResponseDto updateTrainer(String token, UpdateTrainerRequestDto dto);
    void changeActiveStatus(String token, PatchUserRequestDto requestDto);
    List<TrainerTrainingsResponseDto> getTrainerTrainings(String token, String username,
                                                          TrainerTrainingsFilterRequestDto filterDto);
    List<TrainersResponseDto> getUnassignedTrainersByTraineeUsername(String token, String traineeUsername);
}
