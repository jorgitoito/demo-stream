package com.jmr.stream.demostream.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * http://localhost:35006/demo/swagger-ui.html
 * this above url is going to redirect to :
 * http://localhost:35006/demo/swagger-ui/index.html?configUrl=/demo/v3/api-docs/swagger-config
 * <p>
 * if 'No API definition provided' message, add to explore box: /demo/v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${spring.application.description}") String appDescription,
            @Value("${spring.application.version}") String appVersion,
            @Value("${server.servlet.context-path}") String contextPath
    ) {

        final var info = new Info()
                .version(appVersion)
                .description(appDescription)
                .title("Demo - REST API")
                .license(
                        new License().name("Proprietary: Copyright © Company").url("https://www.company.es")
                );

        final var contact = new Contact()
                .name("Demo Swagger API Team")
                .email("Demo@demo.com")
                .url("http://swagger.io");
        info.setContact(contact);

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url(contextPath))
                .externalDocs(new ExternalDocumentation()
                        .description("Demo Documentation")
                        .url("https://demo/docs/"));
    }

}
