package mocean.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableDiscoveryClient
/**
 * 开启服务后，可以登录http://localhost:8960/websocket 访问到前端welcome.html进行测试
 *  浏览器F12可以看到websocket发送信息
 * @author mh
 * @date 2020-04
 */
public class WebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
	}
}
