package com.epam.client;

import com.epam.dto.request.WorkloadRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author jdmon on 21/12/2025
 * @project springbasegymcrm
 */
@FeignClient(name = "workloads-service", fallback = WorkloadClientFallback.class)
public interface WorkloadClient {

    @PostMapping("/api/v1/workloads")
    void updateWorkload(@RequestBody WorkloadRequestDTO request,
                        @RequestHeader("X-Transaction-ID") String transactionId);
}
