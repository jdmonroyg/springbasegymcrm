package com.epam.dto.request;

import java.time.LocalDate;


/**
 * @author jdmon on 10/09/2025
 * @project springbasegymcrm
 */
public record TrainerTrainingsFilterRequestDto(
        LocalDate periodFrom,

        LocalDate periodTo,

        String traineeName
) {
}
