package com.epam.mapper;

import com.epam.dto.request.CreateTrainerRequestDto;
import com.epam.dto.response.TrainerResponseDto;
import com.epam.dto.response.TrainerUpdatedResponseDto;
import com.epam.dto.response.TrainersResponseDto;
import com.epam.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
@Mapper(componentModel = "spring")
public interface TrainerMapper {

    TrainerResponseDto trainerToTrainerResponseDto(Trainer trainerSelected);

    @Mapping(target = "trainerResponseDto", source = ".")
    TrainerUpdatedResponseDto trainerToTrainerUpdResponseDto(Trainer trainerToUpdated);

    List<TrainersResponseDto> trainersToTrainersResponseDto(List<Trainer> trainers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "trainees", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "specialization.id", source = "specializationId")
    @Mapping(target = "role", ignore = true)
    Trainer createTrainerRequestDtoToTrainer(CreateTrainerRequestDto dto);

}
