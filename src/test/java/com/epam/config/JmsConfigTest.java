package com.epam.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.support.converter.JacksonJsonMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 13/01/2026
 * @project springbasegymcrm
 */

class JmsConfigTest {

    @Test
    void messageConverterShouldBeConfigured() {
        JmsConfig config = new JmsConfig();
        MessageConverter messageConverter = config.messageConverter();

        assertNotNull(messageConverter);
        assertInstanceOf(JacksonJsonMessageConverter.class, messageConverter);
        assertEquals(MessageType.TEXT, ReflectionTestUtils.getField(messageConverter, "targetType"));
        assertEquals("_type", ReflectionTestUtils.getField(messageConverter, "typeIdPropertyName"));
    }

}