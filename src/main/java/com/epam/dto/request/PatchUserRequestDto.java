package com.epam.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import static com.epam.util.Constants.INVALID_FIELD;

/**
 * @author jdmon on 7/09/2025
 * @project springbasegymcrm
 */
public record PatchUserRequestDto(

        @NotBlank(message = INVALID_FIELD)
        String username,

        @NotNull(message = INVALID_FIELD)
        Boolean active
) {
}
