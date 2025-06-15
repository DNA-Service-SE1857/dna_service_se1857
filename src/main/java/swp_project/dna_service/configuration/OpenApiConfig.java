package swp_project.dna_service.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.ExternalDocumentation;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                    .addSecuritySchemes("bearer-key",
                            new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")
                                    .description("Nhập JWT token của bạn với định dạng: Bearer <token>")))
            .info(new Info()
                    .title("DNA Service API")
                    .description("API Documentation for DNA Service")
                    .version("1.0")
                    .contact(new Contact()
                            .name("DNA Service Team")
                            .email("contact@dnaservice.com"))
                    .license(new License()
                            .name("Apache 2.0")
                            .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
            .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
            .externalDocs(new ExternalDocumentation()
                    .description("DNA Service Wiki Documentation")
                    .url("https://dnaservice.wiki.com"));
}
}