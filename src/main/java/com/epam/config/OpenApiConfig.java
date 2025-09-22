package com.epam.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author jdmon on 6/09/2025
 * @project springbasegymcrm
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        final String securitySchemeName = "Authorization";
        return new OpenAPI()
                .info(new Info()
                        .title("GymCRM API")
                        .version("1.0")
                        .description("Documentation")
                        .contact(new Contact()
                                .name("Jesus Monroy")
                                .email("jesusmonroy@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer").bearerFormat("UUID")));
    }
}
