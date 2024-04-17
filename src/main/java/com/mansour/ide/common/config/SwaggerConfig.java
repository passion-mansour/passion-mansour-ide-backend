package com.mansour.ide.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi  openAPI() {

        return GroupedOpenApi.builder()
            .group("mansour")
            .pathsToMatch("/public/**")
            .build();
    }

}
