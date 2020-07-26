package com.dairyfarm;

//import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;

//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
public class DairyfarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(DairyfarmApplication.class, args);
	}
	
//	@Bean
//	public Docket swaggerConfiguration() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.dairyfarm"))
//				.paths(PathSelectors.any())
//				.build()
//				.apiInfo(apiInfoDetails());
//	}
//	
//	public ApiInfo apiInfoDetails() {
//		return new ApiInfo(
//				"Dairyfarm API Information",
//				"API Documentation",
//				"1.0", 
//				"Open API", 
//				new springfox.documentation.service.Contact("Shubhankar Dutta Banik", "www.genericurl.com", "sduttabanik113@gmail.com"),
//				"Apache 2.0", 
//				"http://www.apache.org/licenses/LICENSE-2.0", 
//				Collections.emptyList());
//	}

}
