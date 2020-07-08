package com.nxsol;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * To use {@link SwaggerConfigV2} annotate your {@link SpringBootApplication} class
 * (or any of your {@link Configuration} class) as
 * <pre> {@code
 * @Import(SwaggerConfigV2.class)
 * </pre>
 * 
 */
@EnableSwagger2
@Configuration
public class SwaggerConfigV2 {

	/**
	 * To override {@link Docket} bean, define a docket bean and annotate it with {@link Primary}
	 * 
	 */
	@ConditionalOnMissingBean
	@Bean
	public Docket docket() {
		List<ResponseMessage> list = new ArrayList<>();
		list.add(new ResponseMessageBuilder().code(500).message("Internal Server Error").build());
		list.add(new ResponseMessageBuilder().code(403).message("Forbidden").build());

		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				/*.globalResponseMessage(RequestMethod.GET, list)
				.globalOperationParameters(Arrays.asList(new ParameterBuilder()
			            .name("auth_token")
			            .description("Authorization Token")
			            .modelRef(new ModelRef("string"))
			            .parameterType("header")
			            .required(false)
			            .build()))*/
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.nxsol"))
				.build()
				.apiInfo(apiInfo());
	}

	/**
	 * To override {@link ApiInfo} for some services, define a bean and annotate it with {@link Primary}
	 * 
	 */
	@ConditionalOnMissingBean
	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API Documents")
				.description("Documents with Swagger 2")
				.termsOfServiceUrl("http://www.nxsol.com/")
				.contact(new Contact("", "", "minesh.b.java@gmail.com"))
				.license("nxsol.com")
				.licenseUrl("http://www.nxsol.com/")
				.version("1.0")
				.build();
	}
	
	@Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
            .displayRequestDuration(true)
            .validatorUrl("")
            .build();
    }
}
