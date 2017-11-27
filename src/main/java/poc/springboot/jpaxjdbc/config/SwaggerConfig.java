package poc.springboot.jpaxjdbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * The Swagger config.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * API Configuration and Information
     *
     * @return the {@link Docket}
     */
    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("POC-JPA-X-JDBC")
            .apiInfo(metadata())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(regex("/api/*.*"))
            .build()
            .pathMapping("/")
            .apiInfo(metadata());
    }

    /**
     * UI Configuration
     *
     * @return the {@link UiConfiguration}
     */
    @Bean
    public UiConfiguration uiConfig() {
        return UiConfiguration.DEFAULT;
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
            .title("POC - Show difference between JPA and JDBC")
            .description("API to handle with PersonJpa Entity")
            .version("1.0")
            .contact("renanpelicari@gmail.com")
            .build();
    }
}
