package com.epam.client;

import com.epam.dto.request.WorkloadRequestDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
//class WorkloadClientFallbackTest {
//
//    @Test
//    void updateWorkload_shouldNotThrowException() {
//        WorkloadRequestDTO request = new WorkloadRequestDTO(
//                "jdoe",
//                "John",
//                "Doe",
//                true,
//                LocalDate.now(),
//                60,
//                "ADD"
//        );
//
//        WorkloadClientFallback fallback = new WorkloadClientFallback();
//
//        assertDoesNotThrow(() ->
//                fallback.updateWorkload(request, "tx-123")
//        );
//    }
//
//}