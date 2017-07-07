package xyz.youjieray.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * created by IntelliJ IDEA
 *
 * @author leihz
 * @date 2017/7/6 13:19
 */
@Configuration
public class WebViewConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath*:static/**");
        super.addResourceHandlers(registry);
    }
}
