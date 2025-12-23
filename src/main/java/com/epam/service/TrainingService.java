package com.epam.service;

import com.epam.dto.request.CreateTrainingRequestDto;

/**
 * @author jdmon on 17/08/2025
 * @project springbasegymcrm
 */
public interface TrainingService {

    void createTraining(CreateTrainingRequestDto requestDto);
    void deleteTraining(Long id);
}
