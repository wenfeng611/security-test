package top.dc.security.rest.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dc.security.rest.constant.WebConstant;
import top.dc.security.rest.model.out.WebResult;

/**
 * @author wenfeng.zhu
 */
@Api(description = "数据接口")
@RestController
@RequestMapping(WebConstant.WEB_BASE + "/data")
public class DataController {
    @GetMapping("/access")
    public WebResult getData() {
        return WebResult.ok("this is data", "haha");
    }
}
