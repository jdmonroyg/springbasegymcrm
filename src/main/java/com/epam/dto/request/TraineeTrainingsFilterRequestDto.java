package com.epam.dto.request;

import jakarta.validation.constraints.Min;

import java.time.LocalDate;

import static com.epam.util.Constants.INVALID_ID_VALUE;

/**
 * @author jdmon on 10/09/2025
 * @project springbasegymcrm
 */
public record TraineeTrainingsFilterRequestDto(

        LocalDate periodFrom,

        LocalDate periodTo,

        String trainerName,

        @Min(value = 1, message = INVALID_ID_VALUE)
        Long trainingTypeId

) {
}
