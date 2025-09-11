package com.epam.dto.response;

import java.util.Set;

/**
 * @author jdmon on 7/09/2025
 * @project springbasegymcrm
 */
public record TrainerResponseDto(
        String firstName,
        String lastName,
        String email,
        TrainingTypeResponseDto specialization,
        Boolean active,
        Set<TraineesResponseDto> trainees
) {
}
