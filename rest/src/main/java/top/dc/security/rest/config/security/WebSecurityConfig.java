package top.dc.security.rest.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static top.dc.security.rest.constant.WebConstant.WEB_BASE;

/**
 * @author wenfeng.zhu
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .accessDecisionManager(new RestAccessDecisionManager())

                //swagger 访问权限
                .regexMatchers("/doc.*")
                .permitAll()

                //登录接口权限
                .regexMatchers(WEB_BASE + "/login.*")
                .permitAll()

                //其余接口 访问受限
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new TokenAuthenticationEntryPoint())
                .and()
                .logout().permitAll()
                .and()
                .addFilterBefore(new AuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

}
