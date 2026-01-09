package com.epam.indicator;

import com.epam.repository.UserRepository;
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
class UserMetricsTest {

    @Test
    void shouldRegisterGaugeAndReturnRepositoryCount() {
        // Arrange
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        UserRepository repository = mock(UserRepository.class);
        when(repository.count()).thenReturn(100L);

        // Act
        new UserMetrics(registry, repository);

        // Assert
        Gauge gauge = registry.find("users_total").gauge();
        assertEquals(100.0, gauge.value());
    }

}