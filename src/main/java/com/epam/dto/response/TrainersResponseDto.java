package com.epam.dto.response;

/**
 * @author jdmon on 7/09/2025
 * @project springbasegymcrm
 */
public record TrainersResponseDto (
        String username,
        String firstName,
        String lastName,
        String email,
        TrainingTypeResponseDto specialization
) {
}
