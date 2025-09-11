package com.epam.mapper;

import com.epam.dto.response.TrainingsTypeResponseDto;
import com.epam.model.TrainingType;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
@Mapper(componentModel = "spring")
public interface TrainingTypeMapper {

    TrainingsTypeResponseDto trainingTypeToTrainingTypeResponse(TrainingType type);
    List<TrainingsTypeResponseDto> trainingTypesToTrainingTypesResponse(List<TrainingType> types);
}
