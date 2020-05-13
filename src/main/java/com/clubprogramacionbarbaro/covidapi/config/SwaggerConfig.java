package com.clubprogramacionbarbaro.covidapi.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${api.version}")
	private String apiVersion;
	
	@Value("${api.title}")
	private String apiTitle;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.clubprogramacionbarbaro.covidapi"))
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(securitySchemes())
				.securityContexts(securityContext())
				.apiInfo(apiInfo())
				.produces(producesAndConsumes())
				.consumes(producesAndConsumes());
				//.globalOperationParameters(getOperationParameters());
	}

	private List<SecurityContext> securityContext() {
		return Arrays.asList(SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/v1.*"))
				.build());
	}
	
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));
	}

	private List<ApiKey> securitySchemes() {
		return Arrays.asList(new ApiKey("Bearer", "Authorization", "header"));
		
	}

	private List<Parameter> getOperationParameters() {
		List<Parameter> params = new ArrayList<>();
		params.add(new ParameterBuilder()
				.name("Authorization")
				.description("Authorization")
				.modelRef(new ModelRef("String"))
				.parameterType("header")
				.required(false)
				.build());
		
		return params;
	}

	private Set<String> producesAndConsumes() {
		return new HashSet<String>(Arrays.asList("application/json"/*, "application/xml"*/));
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title(apiTitle)
				.description("This Api helps the hospitals to always have the necesary material during the pandemy")
				.version(apiVersion)
				.termsOfServiceUrl("Free use")
				.contact(createContact())
				.license("apache 2.0")
				.build();
		
		return apiInfo;
	}

	private Contact createContact() {
		Contact contact = new Contact("César Salazar", "cslz.com", "cssalazar77@gmail.com");
		return contact;
	}
}
