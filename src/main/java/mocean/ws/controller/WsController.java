package mocean.ws.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import mocean.ws.service.WebSocketService;
import mocean.ws.util.WsResMessage;

@Controller
public class WsController {

	@Resource
	WebSocketService webSocketService;

	@Autowired
	private SimpMessagingTemplate template;

	@GetMapping("/websocket")
	public String index() {
		return "/hello/welcome";
	}

	/**
	 * 前端调用点播
	 * 订阅stompClient.subscribe('/topic/getResponse'
	 * @param msg
	 * @return
	 */
//	@MessageMapping("/welcome")
	public String greeting(String msg) {
		template.convertAndSend("/topic/getResponse", msg);
		return "welcome " + msg + "!";
	}

	/**
	 * 前端界面调用点对点
	 * stompClient.subscribe('/user/doris/msg'
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/cheat")
	public String interfac( WsResMessage message) throws Exception {
		
		System.out.println("前端界面WsController调用----" + message.toString());
		template.convertAndSendToUser(message.getName(), "/msg", message.getContext());
		return "Welcome, " + message.toString() + "!";
	}

}
