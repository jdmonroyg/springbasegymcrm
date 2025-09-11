package com.epam.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

import static com.epam.util.Constants.INVALID_EMAIL;
import static com.epam.util.Constants.INVALID_FIELD;

public record CreateTraineeRequestDto (

        @NotBlank(message = INVALID_FIELD)
        String firstName,

        @NotBlank(message = INVALID_FIELD)
        String lastName,

        @NotBlank(message = INVALID_FIELD)
        @Email(message = INVALID_EMAIL)
        String email,

        LocalDate dateOfBirth,

        String address ){
}