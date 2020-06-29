package top.dc.security.rest.config.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.swagger2.web.Swagger2Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wenfeng.zhu
 */
@ApiIgnore
@RestController
public class SwaggerDocController {

    @Autowired
    private Swagger2Controller swagger2Controller;

    @RequestMapping(value = "/doc/v2/api-docs", method = RequestMethod.GET, produces = {"application/json", "application/hal+json"})
    public ResponseEntity<Json> getDocumentation(
            @RequestParam(value = "group", required = false) String swaggerGroup,
            HttpServletRequest servletRequest) {
        return swagger2Controller.getDocumentation(swaggerGroup, servletRequest);
    }
}
