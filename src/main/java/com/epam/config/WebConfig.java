package com.epam.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jdmon on 2/09/2025
 * @project springbasegymcrm
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.epam.controller","org.springdoc"})
public class WebConfig implements WebMvcConfigurer {

}
