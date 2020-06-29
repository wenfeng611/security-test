package top.dc.security.rest.config.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * @author wenfeng.zhu
 */
@Controller
@ApiIgnore
@RequestMapping("/doc/swagger-resources")
public class SwaggerApiResourceController extends ApiResourceController {
    public SwaggerApiResourceController(SwaggerResourcesProvider swaggerResources) {
        super(swaggerResources);
    }
}