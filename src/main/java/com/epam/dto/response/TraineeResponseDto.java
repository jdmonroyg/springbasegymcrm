package com.epam.dto.response;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author jdmon on 7/09/2025
 * @project springbasegymcrm
 */
public record TraineeResponseDto(
        String firstName,
        String lastName,
        String email,
        LocalDate dateOfBirth,
        String address,
        Boolean active,
        Set<TrainersResponseDto> trainers
) {
}
