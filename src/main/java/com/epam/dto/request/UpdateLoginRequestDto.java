package com.epam.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.epam.util.Constants.*;

/**
 * @author jdmon on 6/09/2025
 * @project springbasegymcrm
 */
public record UpdateLoginRequestDto(
//        @NotBlank(message = INVALID_FIELD)
//        String username,
        @NotBlank (message = INVALID_FIELD)
        String currentPassword,
        @NotBlank (message = INVALID_FIELD)
        @Pattern(regexp = REGEX_PASSWORD, message = INVALID_PASSWORD )
        String newPassword
) {
}
