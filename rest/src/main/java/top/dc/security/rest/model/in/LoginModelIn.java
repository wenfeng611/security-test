package top.dc.security.rest.model.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wenfeng.zhu
 */
@ApiModel("登录Model")
@Data
public class LoginModelIn implements Serializable {
    @ApiModelProperty(value = "用户名", position = 0)
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", position = 1)
    @NotNull(message = "密码不能为空")
    private String password;
}
