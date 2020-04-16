package mocean.ws.heart;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 配置WebSocket
 * @author WeiguoLiu
 * @data 2018年1月11日
 */
@Component
@EnableWebSocket
public class WebSocketConfigs implements WebSocketConfigurer{
	
	@Bean
    public ServerEndpointExporter serverEndpointExporter(ApplicationContext context) {
        return new ServerEndpointExporter();
    }

    @Resource
    MyWebSocketHandler handler;
 
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	
    	//配置对指定的url进行拦截
        registry.addHandler(handler, "ws://127.0.0.1:8960/endpointWisely").addInterceptors(new HandShakeInterceptor());
        System.out.println("-------------------");
 
        //允许客户端使用SokcetJs
//        registry.addHandler(handler, "/endpointWisely").addInterceptors(new HandShakeInterceptor()).withSockJS();
    }
   }