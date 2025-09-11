package com.epam.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.epam.util.Constants.*;

public record CreateTrainerRequestDto(

        @NotBlank(message = INVALID_FIELD)
        String firstName,

        @NotBlank(message = INVALID_FIELD)
        String lastName,

        @NotBlank(message = INVALID_FIELD)
        @Email(message = INVALID_EMAIL)
        String email,

        @NotNull (message = INVALID_FIELD)
        @Min(value = 1, message = INVALID_ID_VALUE)
        Long specializationId ){
}