package com.epam.indicator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.health.contributor.Health;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class DatabaseHealthIndicatorTest {

    @Test
    void shouldReturnUpWhenConnectionIsValid() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isValid(2000)).thenReturn(true);

        DatabaseHealthIndicator indicator = new DatabaseHealthIndicator(dataSource);

        Health health = indicator.health();

        assertEquals("UP", health.getStatus().getCode());
        assertEquals("Available", health.getDetails().get("database"));
    }

    @Test
    void shouldReturnDownWhenConnectionIsNotValid() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isValid(2000)).thenReturn(false);

        DatabaseHealthIndicator indicator = new DatabaseHealthIndicator(dataSource);

        Health health = indicator.health();

        assertEquals("DOWN", health.getStatus().getCode());
        assertEquals("Not reachable", health.getDetails().get("database"));
    }

    @Test
    void shouldReturnDownWhenSQLExceptionOccurs() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenThrow(new SQLException("DB error"));

        DatabaseHealthIndicator indicator = new DatabaseHealthIndicator(dataSource);

        Health health = indicator.health();

        assertEquals("DOWN", health.getStatus().getCode());
        assertEquals("DB error", health.getDetails().get("error"));
    }


}