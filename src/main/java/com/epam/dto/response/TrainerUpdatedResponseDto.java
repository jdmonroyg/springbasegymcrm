package com.epam.dto.response;

/**
 * @author jdmon on 8/09/2025
 * @project springbasegymcrm
 */
public record TrainerUpdatedResponseDto(
        String username,
        TrainerResponseDto trainerResponseDto
){
}
