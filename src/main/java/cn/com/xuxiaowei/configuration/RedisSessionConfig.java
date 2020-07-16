package cn.com.xuxiaowei.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 开启 Redis Session 缓存
 *
 * @author xuxiaowei
 */
@EnableRedisHttpSession
public class RedisSessionConfig {

    /**
     * 在主域中储存Cookie，子域中共享Cookie
     */
    @Bean
    CookieSerializer cookieSerializer() {

        // 默认 Cookie 序列化
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();

        // Cookie 名字，默认为 SESSION
        // 可使用下列方式自定义
        // <code>defaultCookieSerializer.setCookieName();</code>

        // 域，这允许跨子域共享cookie，默认设置是使用当前域。
        // 测试环境，使用 127.0.0.1
        defaultCookieSerializer.setDomainName("127.0.0.1");

        // Cookie的路径。
        defaultCookieSerializer.setCookiePath("/");

        // 设置 浏览器 Cookie（Session） 有效时间（秒）
        // 默认有效时间：浏览器关闭时过期
        // <code>defaultCookieSerializer.setCookieMaxAge();</code>

        return defaultCookieSerializer;
    }

}
