package cn.meteoroid.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @author Kelvin Song
 */
@Configuration
public class CorsConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "TRACE", "OPTIONS")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .maxAge(3600)
                .allowCredentials(true);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean<>(new CorsFilter(source));
        //设置最高优先级，避免OPTIONS /oauth/token 401的问题
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        source.registerCorsConfiguration("/**", config);
        return registrationBean;
    }
}
