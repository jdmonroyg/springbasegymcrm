package com.epam.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

/**
 * @author jdmon on 5/09/2025
 * @project springbasegymcrm
 */
public class TransactionLoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLoggingFilter.class);
    private static final String TRANSACTION_ID_KEY = "transactionId";
    private static final String TRANSACTION_ID_HEADER = "X-Transaction-ID";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String transactionId = UUID.randomUUID().toString();

        MDC.put(TRANSACTION_ID_KEY, transactionId);

        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            LOGGER.info("Request received: {} {}",
                    request.getMethod(),
                    request.getRequestURI()//,request.getRemoteAddress()
            );
            response.addHeader(TRANSACTION_ID_HEADER, transactionId);

            filterChain.doFilter(request, response);

            LOGGER.info("Request completed with status: {}", response.getStatus());
        } finally {
            MDC.remove(TRANSACTION_ID_KEY);
        }
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("TransactionLoggingFilter initialized");
    }

    @Override
    public void destroy() {
        LOGGER.info("TransactionLoggingFilter destroyed");
    }
}
