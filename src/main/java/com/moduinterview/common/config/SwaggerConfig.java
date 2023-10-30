package com.moduinterview.common.config;

import com.fasterxml.classmate.TypeResolver;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

  private static final String REFERENCE = "apiKey";

  @Bean
  public Docket api(TypeResolver typeResolver) {
    return new Docket(DocumentationType.OAS_30)

//        .additionalModels(typeResolver.resolve(AccessTokenResponseDto.class))
//        .additionalModels(typeResolver.resolve(ErrorResponse.class))
        .useDefaultResponseMessages(false)

        .select()
        .apis(RequestHandlerSelectors.basePackage("com.moduinterview.common.controller")
            .or(RequestHandlerSelectors.basePackage("com.moduinterview.interview.controller"))
                .or(RequestHandlerSelectors.basePackage("com.moduinterview.user.controller")))
        .paths(PathSelectors.any())
        .build()

        .apiInfo(apiInfo())
        .securityContexts(Collections.singletonList(securityContext())) // Security 관련 설정
        .securitySchemes(Collections.singletonList(bearerAuthSecurityScheme())); //jwt 읽기 위한 설정
  }

  private SecurityContext securityContext() {
    return SecurityContext
        .builder()
        .securityReferences(defaultAuth())
        .operationSelector(operationContext -> true)
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
    return Collections.singletonList(new SecurityReference(REFERENCE, authorizationScopes));
  }

  private HttpAuthenticationScheme bearerAuthSecurityScheme() {
    return HttpAuthenticationScheme.JWT_BEARER_BUILDER
        .name(REFERENCE).build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Moiuinterview Backend API")
        .description("Backend API 리스트입니다.")
        .version("1.0")
        .build();
  }

}
