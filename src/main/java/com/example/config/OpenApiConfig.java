package com.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI (Swagger) documentation.
 * Uses constants for metadata to improve readability and maintainability.
 */
@Configuration
public class OpenApiConfig {

    private static final String API_TITLE = "Learning Platform API";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "REST API для учебной платформы на Spring Boot с Hibernate/JPA. Предоставляет доступ к курсам, пользователям и регистрации.";

    private static final String CONTACT_NAME = "Learning Platform";
    private static final String CONTACT_EMAIL = "support@example.com";

    private static final String LICENSE_NAME = "Apache 2.0";
    private static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0.html";

    /**
     * Creates and configures the custom OpenAPI bean using predefined constants.
     * @return The configured OpenAPI object.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact()
                .name(CONTACT_NAME)
                .email(CONTACT_EMAIL);

        License license = new License()
                .name(LICENSE_NAME)
                .url(LICENSE_URL);

        Info info = new Info()
                .title(API_TITLE)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info);
    }
}