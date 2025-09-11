package com.epam.dto.response;

import java.time.LocalDate;

/**
 * @author jdmon on 10/09/2025
 * @project springbasegymcrm
 */
public record TrainerTrainingsResponseDto (
        String trainingName,
        LocalDate trainingDate,
        TrainingTypeResponseDto trainingType,
        int durationInMinutes,
        String traineeFirstName
) {
}
