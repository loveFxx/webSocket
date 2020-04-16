package mocean.ws.heart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {  
  
	/**
     * 自己定义的拦截器类
     * @return
     */
    @Bean
    HandShakeInterceptor sysUserLoginInterceptor() {
        return new HandShakeInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addWebRequestInterceptor( (WebRequestInterceptor) new SessionAuthHandshakeInterceptor());
//    }

} 
