package com.epam.indicator;

import com.epam.repository.TrainingRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class TrainingMetricsTest {

    @Test
    void shouldRegisterGaugeAndReturnRepositoryCount() {

        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        TrainingRepository repository = mock(TrainingRepository.class);
        when(repository.count()).thenReturn(42L);

        new TrainingMetrics(registry, repository);

        Gauge gauge = registry.find("trainings_total").gauge();
        assertEquals(42.0, gauge.value());
    }

}