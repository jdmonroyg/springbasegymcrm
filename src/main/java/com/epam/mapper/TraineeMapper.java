package com.epam.mapper;

import com.epam.dto.request.CreateTraineeRequestDto;
import com.epam.dto.response.TraineeResponseDto;
import com.epam.dto.response.TraineeUpdatedResponseDto;
import com.epam.model.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
@Mapper(componentModel = "spring")
public interface TraineeMapper {

    TraineeResponseDto traineeToTraineeResponseDto(Trainee trainee);

    @Mapping(target = "traineeResponseDto", source = ".")
    TraineeUpdatedResponseDto traineeToTraineeUpdResponseDto(Trainee trainee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "trainers", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "role", ignore = true)
    Trainee createTraineeRequestDtoToTrainee(CreateTraineeRequestDto dto);
}
