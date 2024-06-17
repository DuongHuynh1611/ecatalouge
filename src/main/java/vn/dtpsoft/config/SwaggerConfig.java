package vn.dtpsoft.config;

import springfox.documentation.service.Contact;
import vn.dtpsoft.model.dto.SwaggerPageable;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Profile({"!prod"})
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Autowired
    private TypeResolver typeResolver;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket apiMas() {
        HashSet<String> consumesAndProduces = new HashSet<String>(Arrays.asList("application/json"));
        List<ResponseMessage> responseMessages = Arrays.asList(
//            new ResponseMessageBuilder().code(401).message("Unauthorized").build(),
//            new ResponseMessageBuilder().code(403).message("Forbidden").build(),
//            new ResponseMessageBuilder().code(404).message("Not Found").build(),
            //new ResponseMessageBuilder().code(401).message("Method Not Allow").responseModel(errorModel).build(),
//            new ResponseMessageBuilder().code(405).message("Method Not Allow").build(),
//            new ResponseMessageBuilder().code(500).message("Server Internal Error").build()
        );

        //Class[] clazz = {Permission.class, Role.class, User.class, TokenManagement.class};

        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("Api Documentation")
            .forCodeGeneration(true)
            //.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(ResponseEntity.class)
            //        ,typeResolver.resolve(WildcardType.class)))
            //.ignoredParameterTypes(clazz)
            .consumes(consumesAndProduces)
            .produces(consumesAndProduces)
            .select()
            .apis(RequestHandlerSelectors.basePackage("vn.dtpsoft.modules"))

            //.paths(regex("/a.*"))
            .build().apiInfo(metadata())
            .directModelSubstitute(Pageable.class, SwaggerPageable.class)
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET, responseMessages)
            .globalResponseMessage(RequestMethod.POST, responseMessages)
            .globalResponseMessage(RequestMethod.DELETE, responseMessages)
            .securitySchemes(Arrays.asList(apiKey()))
            .enableUrlTemplating(false)
            ;


    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", AUTHORIZATION_HEADER, "header");
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
            .title("Spring boot Api template")
            .description("This is template api from Spring boot framework 2.7.0")
            .version("1.0")
            .contact(new Contact("Thi Phung", "https://www.dtpdoft.vn","thipv@dtp-education.com"))
            .license("License to DTP @2023")
            .licenseUrl("https://www.dtpsoft.vn")
            .build();
    }
}
