package com.epam.dto.request;

import jakarta.validation.constraints.NotBlank;

import static com.epam.util.Constants.INVALID_FIELD;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
public record LoginRequestDto (
        @NotBlank(message = INVALID_FIELD)
        String username,
        @NotBlank(message = INVALID_FIELD)
        String password ) {
}
