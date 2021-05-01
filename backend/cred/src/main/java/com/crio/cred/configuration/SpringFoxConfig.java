package com.crio.cred.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Class for Swagger configuration.
 *
 * @author harikesh.pallantla
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpringFoxConfig {
    /**
     * The Constant DEFAULT_PRODUCES_CONSUMES.
     */
    private static final Set<String> DEFAULT_PRODUCES_CONSUMES = Set.of(MediaType.APPLICATION_JSON_VALUE);

    /**
     * The constant USER_TAG.
     */
    public static final String USER_TAG = "user";

    /**
     * The constant CARD_TAG.
     */
    public static final String CARD_TAG = "card";

    private final List<Response> globalResponses = Arrays.asList(
            new ResponseBuilder()
                    .code(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED))
                    .description("Unauthorized user.")
                    .build(),
            new ResponseBuilder()
                    .code(String.valueOf(HttpServletResponse.SC_BAD_REQUEST))
                    .description("Validation Error")
                    .build(),
            new ResponseBuilder()
                    .code(String.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR))
                    .description("Internal Error")
                    .build());

    private final HttpAuthenticationScheme authenticationScheme = HttpAuthenticationScheme
            .JWT_BEARER_BUILDER
            .name("JWT")
            .build();

    /**
     * Api.
     *
     * @return the docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30).select()
                .apis(RequestHandlerSelectors.basePackage("com.crio.cred")).build()
                .tags(new Tag(USER_TAG, "User Operations"),
                        new Tag(CARD_TAG, "Card Operations"))
                .apiInfo(apiInfo())
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(authenticationScheme))
                .produces(DEFAULT_PRODUCES_CONSUMES)
                .consumes(DEFAULT_PRODUCES_CONSUMES)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalResponses)
                .globalResponses(HttpMethod.POST, globalResponses)
                .globalResponses(HttpMethod.DELETE, globalResponses)
                .globalResponses(HttpMethod.PATCH, globalResponses)
                .globalResponses(HttpMethod.PUT, globalResponses);
    }

    /**
     * Api info method returns the ApiInfo object with the details.
     *
     * @return the api info
     */
    private ApiInfo apiInfo() {

        Contact contact = new Contact("Harikesh", "https://harikesh409.github.io/",
                "p.harikesh409@gmail.com");

        return new ApiInfo("CRED", "Credit Card Management System.",
                "1.0", "", contact,
                "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }

    /**
     * Security Context.
     *
     * @return SecurityContext
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                // Whitelisting authorization header for login and signup endpoints
                .operationSelector(operationContext -> (
                        !operationContext.requestMappingPattern().startsWith("/login")) &&
                        !operationContext.requestMappingPattern().startsWith("/signup")
                )
                .build();
    }

    /**
     * Default auth list.
     *
     * @return the list
     */
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    /**
     * Security configuration.
     *
     * @return the security configuration
     */
    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder().scopeSeparator(",")
                .additionalQueryStringParams(null)
                .enableCsrfSupport(false)
                .useBasicAuthenticationWithAccessCodeGrant(false).build();
    }
}
