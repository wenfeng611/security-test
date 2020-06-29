package top.dc.security.rest.config.security;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wenfeng.zhu
 */
@Slf4j
@Component
public class RestAccessDecisionManager implements AccessDecisionManager {

    /**
     * java 配置 PermitAll
     */
    private String permitAll = "permitAll";

    /**
     * 最基本的用户的权限
     */
    private String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * spring security 默认 principle
     */
    private String anonymousUser = "anonymousUser";

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        String userId = String.valueOf(authentication.getPrincipal());
        if (null == userId || anonymousUser.equals(userId)) {
            userId = "";
        }
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        for (ConfigAttribute configAttribute : collection) {
            if (permitAll.equals(configAttribute.toString())) {
                log.info("userId[{}] url [{}] result [allow] reason [permitAll]", userId, requestUrl);
                return;
            }
        }

        boolean allow = true;
        String reason = "default allow";

        //读取用户权限
        List<String> authorities = authentication.getAuthorities().stream().map(x -> x.toString()).collect(Collectors.toList());
        if (!authorities.contains(ROLE_ANONYMOUS)) {
            authorities.add(ROLE_ANONYMOUS);
        }

        //TODO 读取此地址所需要的权限
        List<String> permissions = Lists.newArrayList();
        permissions.add("ROLE_DATA");

        for (String permission : permissions) {
            if (!authorities.contains(permission)) {
                reason = " need perimission [" + permission + "] ";
                allow = false;
                break;
            }
        }

        log.info("userId [{}] url [{}] user permissions [{}] url permissions [{}] result [{}] reason [{}]",
                userId, requestUrl, Joiner.on(",").join(authorities), Joiner.on(",").join(permissions),
                allow ? "allow" : "reject", reason);
        if (!allow) {
            throw new AccessDeniedException(reason);
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
