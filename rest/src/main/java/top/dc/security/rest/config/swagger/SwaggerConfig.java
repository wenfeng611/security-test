package top.dc.security.rest.config.swagger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author wenfeng.zhu
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .securitySchemes(apiKeys())
                .securityContexts(newArrayList(securityContext()))
                .select()
                .apis(packagesToScan())
                .paths(PathSelectors.any())
                .build();
    }

    private List<ResponseMessage> responseMessages() {
        List<ResponseMessage> list = new ArrayList<>();
        list.add(new ResponseMessageBuilder()
                .code(500)
                .message("服务器异常")
                .build());
        list.add(new ResponseMessageBuilder()
                .code(200)
                .message("OK")
                .build());
        list.add(new ResponseMessageBuilder()
                .code(400)
                .message("参数错误")
                .build());
        list.add(new ResponseMessageBuilder()
                .code(401)
                .message("未登录")
                .build());
        list.add(new ResponseMessageBuilder()
                .code(403)
                .message("禁止访问")
                .build());
        return list;
    }

    private com.google.common.base.Predicate<RequestHandler> packagesToScan() {
        return Predicates.or(
                RequestHandlerSelectors.basePackage("top.dc.security")
        );
    }

    private List<ApiKey> apiKeys() {
        List<ApiKey> list = new ArrayList<>();
        list.add(new ApiKey("token", "Authorization", "header"));
        return list;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("token", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SecurityAPI")
                .description("")
                .termsOfServiceUrl("https://xxx.xx")
                .version("1.0")
                .build();
    }
}
