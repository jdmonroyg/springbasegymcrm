package com.epam.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 30/07/2025
 * @project springbasegymcrm
 */
class ApplicationConfigTest {

    @Test
    void propertyConfig() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class)){
            Environment environment = context.getEnvironment();
            String file = environment.getProperty("storage.init.file");
            assertEquals("data/init-data.json",file);
        }
    }
}