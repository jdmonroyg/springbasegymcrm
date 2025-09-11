package com.epam.mapper;

import com.epam.dto.request.CreateTrainingRequestDto;
import com.epam.dto.response.TraineeTrainingsResponseDto;
import com.epam.dto.response.TrainerTrainingsResponseDto;
import com.epam.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author jdmon on 9/09/2025
 * @project springbasegymcrm
 */
@Mapper(componentModel = "spring")
public interface TrainingMapper {

    @Mapping(target ="trainerFirstName" , source ="trainer.firstName" )
    TraineeTrainingsResponseDto trainingsToTraineeTrainingsResponseDto(Training training);
    List<TraineeTrainingsResponseDto> trainingsToTraineeTrainingsResponseDto(List<Training> trainings);

    @Mapping(target ="traineeFirstName" , source ="trainee.firstName" )
    TrainerTrainingsResponseDto trainingsToTrainerTrainingsResponseDto(Training trainings);
    List<TrainerTrainingsResponseDto> trainingsToTrainerTrainingsResponseDto(List<Training> trainings);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainee", ignore = true)
    @Mapping(target = "trainer", ignore = true)
    @Mapping(target = "trainingType", ignore = true)
    Training CreateTrainingRequestToTraining(CreateTrainingRequestDto dto);



}
