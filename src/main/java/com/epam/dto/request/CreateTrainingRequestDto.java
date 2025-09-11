package com.epam.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

import static com.epam.util.Constants.*;

/**
 * @author jdmon on 9/09/2025
 * @project springbasegymcrm
 */
public record CreateTrainingRequestDto(
        @NotBlank(message = INVALID_FIELD)
        String traineeUsername,

        @NotBlank(message = INVALID_FIELD)
        String trainerUsername,

        @NotBlank(message = INVALID_FIELD)
        String trainingName,

        @NotNull(message = INVALID_FIELD)
        @Future(message = INVALID_FUTURE_DATE)
        LocalDate trainingDate,

        @Min(value = 20, message =INVALID_MIN_VALUE)
        @Max(value = 120, message = INVALID_MAX_VALUE )
        int durationInMinutes
) {
}
