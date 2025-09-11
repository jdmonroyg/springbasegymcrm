package com.epam.service;

import com.epam.dto.response.TrainingsTypeResponseDto;
import com.epam.model.TrainingType;

import java.util.List;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
public interface TrainingTypeService {

    TrainingType selectTrainingTypeById(Long id);

    List<TrainingsTypeResponseDto> getAllTrainingTypes(String token);


}
