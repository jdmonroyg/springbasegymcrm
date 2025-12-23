package com.epam.client;

import com.epam.dto.request.WorkloadRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author jdmon on 21/12/2025
 * @project springbasegymcrm
 */
@Component
public class WorkloadClientFallback implements WorkloadClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkloadClientFallback.class);

    @Override
    public void updateWorkload(WorkloadRequestDTO request, String transactionId) {
        LOGGER.error("TX: {}, FALLBACK TRIGGERED. Workload Service is unavailable. Failed to send action: {}",
                transactionId, request.actionType());
    }
}
