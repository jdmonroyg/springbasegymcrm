package com.epam.indicator;

import com.epam.repository.TrainingRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

/**
 * @author jdmon on 21/09/2025
 * @project springbasegymcrm
 */
@Component
public class TrainingMetrics {

    public TrainingMetrics(MeterRegistry registry, TrainingRepository repository){
        Gauge.builder("trainings_total", repository, TrainingRepository::count )
                .description("Total number of trainings in the database")
                .register(registry);
    }
}
