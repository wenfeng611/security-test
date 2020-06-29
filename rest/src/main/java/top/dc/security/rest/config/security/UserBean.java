package top.dc.security.rest.config.security;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wenfeng.zhu
 */
@Data
@Accessors(chain = true)
public class UserBean implements Serializable {
    private String userId;
    private String name;
    private String username;
}
