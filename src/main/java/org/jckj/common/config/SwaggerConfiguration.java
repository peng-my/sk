package org.jckj.common.config;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.jckj.core.launch.constant.AppConstant;
import org.jckj.core.swagger.SwaggerProperties;
import org.jckj.core.swagger.SwaggerUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Configuration
@EnableSwagger2
@AllArgsConstructor
//@Profile({"dev", "test"})
public class SwaggerConfiguration {

	private final SwaggerProperties swaggerProperties;

	@Bean
	public Docket authDocket() {
		return docket("授权模块", Collections.singletonList(AppConstant.BASE_PACKAGES + ".modules.auth"));
	}

	@Bean
	public Docket sysDocket() {
		return docket("系统模块",
			Arrays.asList(AppConstant.BASE_PACKAGES + ".modules.system", AppConstant.BASE_PACKAGES + ".modules.resource"));
	}

	@Bean
	public Docket flowDocket() {
		return docket("业务模块", Collections.singletonList(AppConstant.BASE_PACKAGES + ".modules.business"));
	}

	private Docket docket(String groupName, List<String> basePackages) {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName(groupName)
			.apiInfo(apiInfo())
			.select()
			.apis(SwaggerUtil.basePackages(basePackages))
			.paths(PathSelectors.any())
			.build().securitySchemes(Lists.<SecurityScheme>newArrayList(SwaggerUtil.clientInfo(), SwaggerUtil.bladeAuth(), SwaggerUtil.bladeTenant()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title(swaggerProperties.getTitle())
			.description(swaggerProperties.getDescription())
			.license(swaggerProperties.getLicense())
			.licenseUrl(swaggerProperties.getLicenseUrl())
			.termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
			.contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
			.version(swaggerProperties.getVersion())
			.build();
	}

}
