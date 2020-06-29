package top.dc.security.rest.config.security;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import top.dc.security.rest.constant.AuthConstant;
import top.dc.security.rest.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Permission;
import java.util.List;

/**
 * 认证类
 *
 * @author wenfeng.zhu
 */
@Slf4j
public class AuthFilter extends BasicAuthenticationFilter {

    private final String OPTIONS = "OPTIONS";

    public AuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (OPTIONS.equals(request.getMethod())) {
            response.setStatus(200);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "*");
            return;
        }

        String token = request.getHeader("Authorization");

        try {
            if (!StringUtils.isEmpty(token)) {
                String decode = JwtUtil.decode(AuthConstant.TOKEN_KEY, token);
                if (!StringUtils.isEmpty(decode)) {
                    final String userId = decode;

                    //根据userId 读取 用户信息,权限数据
                    //FIXME 增加缓存
                    List<GrantedAuthority> permissionList = Lists.newArrayList();

                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, "", permissionList));
                }
            }
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        } finally {
            chain.doFilter(request, response);
        }
    }
}
