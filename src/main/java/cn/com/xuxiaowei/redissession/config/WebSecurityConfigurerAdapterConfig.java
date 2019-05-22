package cn.com.xuxiaowei.redissession.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 禁用 Spring Security 安全控制 配置
 * <p>
 * 1、继承 WebSecurityConfigurerAdapter
 * 2、激活 @Configuration
 * 3、重写 configure(HttpSecurity http)，并保留空
 *
 * @author xuxiaowei
 */
@Configuration
public class WebSecurityConfigurerAdapterConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 保留空
        // <code>super.configure(http);</code>

    }

}
