package top.dc.security.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dc.security.rest.constant.AuthConstant;
import top.dc.security.rest.constant.WebConstant;
import top.dc.security.rest.model.in.LoginModelIn;
import top.dc.security.rest.model.out.WebResult;
import top.dc.security.rest.util.JwtUtil;

/**
 * @author wenfeng.zhu
 */
@Api(description = "登录部分")
@RestController
@RequestMapping(WebConstant.WEB_BASE + "/login")
public class LoginController {
    @ApiOperation("统一登录接口")
    @PostMapping("/common")
    public WebResult login(@RequestBody @Validated LoginModelIn model) {
        //FIXME 完善登录验证逻辑
        final String token = JwtUtil.encode(AuthConstant.TOKEN_KEY, "userIdHere", 1 * 60 * 60);
        return WebResult.ok("login success", token);
    }
}
