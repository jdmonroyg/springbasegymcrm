package com.epam.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class TransactionLoggingFilterTest {

    @Test
    void shouldAddTransactionIdHeaderAndContinueChain() throws Exception {
        // Arrange
        TransactionLoggingFilter filter = new TransactionLoggingFilter();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/api/test");
        when(response.getStatus()).thenReturn(200);

        assertDoesNotThrow(() -> filter.doFilter(request, response, chain));

        verify(response, times(1))
                .addHeader(eq("X-Transaction-ID"), anyString());
        verify(chain, times(1))
                .doFilter(request, response);
    }

}