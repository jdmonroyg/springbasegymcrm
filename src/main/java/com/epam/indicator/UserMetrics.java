package com.epam.indicator;

import com.epam.repository.UserRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

/**
 * @author jdmon on 21/09/2025
 * @project springbasegymcrm
 */
@Component
public class UserMetrics {

    public UserMetrics(MeterRegistry registry, UserRepository repository){
        Gauge.builder("users_total", repository, UserRepository::count )
                .description("Total number of users in the database")
                .register(registry);
    }
}
