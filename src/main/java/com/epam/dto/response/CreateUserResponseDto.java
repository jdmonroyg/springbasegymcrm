package com.epam.dto.response;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
public record CreateUserResponseDto(
        String username,
        String password
) {
}
