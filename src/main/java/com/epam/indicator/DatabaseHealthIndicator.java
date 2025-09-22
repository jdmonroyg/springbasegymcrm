package com.epam.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author jdmon on 21/09/2025
 * @project springbasegymcrm
 */
@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()){
            if (connection.isValid(2000)) {
                return Health.up()
                        .withDetail("database", "Available")
                        .build();
            } else return Health.down()
                    .withDetail("database", "Not reachable")
                    .build();

        } catch (SQLException exception){
            return Health.down()
                    .withDetail("error", exception.getMessage())
                    .build();
        }
    }
}
