package com.epam.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.epam.util.Constants.INVALID_FIELD;
import static com.epam.util.Constants.INVALID_ID_VALUE;

/**
 * @author jdmon on 8/09/2025
 * @project springbasegymcrm
 */
public record UpdateTrainerRequestDto(
        @NotBlank(message = INVALID_FIELD)
        String username,

        @NotBlank(message = INVALID_FIELD)
        String firstName,

        @NotBlank(message = INVALID_FIELD)
        String lastName,

        @NotNull(message = INVALID_FIELD)
        @Min(value = 1, message = INVALID_ID_VALUE)
        Long specializationId,

        @NotNull(message = INVALID_FIELD)
        Boolean active
) {
}
