package com.epam.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import static com.epam.util.Constants.INVALID_FIELD;

/**
 * @author jdmon on 7/09/2025
 * @project springbasegymcrm
 */
public record UpdateTraineeRequestDto(

        @NotBlank(message = INVALID_FIELD)
        String username,

        @NotBlank(message = INVALID_FIELD)
        String firstName,

        @NotBlank(message = INVALID_FIELD)
        String lastName,

        LocalDate dateOfBirth,

        String address,

        @NotNull(message = INVALID_FIELD)
        Boolean active
) {
}
