package com.epam.mapper;

import com.epam.dto.response.TrainingTypeResponseDto;
import com.epam.model.TrainingType;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
@Mapper(componentModel = "spring")
public interface TrainingTypeMapper {

    TrainingTypeResponseDto trainingTypeToTrainingTypeResponse(TrainingType type);
    List<TrainingTypeResponseDto> trainingTypesToTrainingTypesResponse(List<TrainingType> types);
}
