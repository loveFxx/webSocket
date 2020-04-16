package mocean.ws.controller;

import java.nio.ByteBuffer;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.PingMessage;

import mocean.ws.heart.MyWebSocketHandler;
import mocean.ws.util.AES;
import mocean.ws.util.Constant;

@RestController
public class LinkServer {

	@Resource
	LinkServer linkServer;

	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * @MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
	 * /topic/getResponse点播如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。'/user/'+
	 * userId+'/msg' 订阅stompClient.subscribe('/user/doris/msg')
	 * 发送template.convertAndSendToUser(doris, "/msg", msg);
	 * 
	 * @param userid
	 * @param message
	 * @return 点对点
	 * @throws Exception
	 */
	@RequestMapping("/cheat")
	public String ws(@RequestParam(value = "userid") String userid, @RequestParam(value = "message") String message)
			{
		String msg = new AES().decryptFromBase64String(message, userid);
		System.out.println(userid + ":编码前=" + message + " 解码后=" + msg);
		try {
			System.out.println("444444444");
		    template.convertAndSendToUser(userid, "/msg", msg);
		}catch (MessagingException e) {
			System.out.println("666666666");
			e.printStackTrace();
		}
		return "Welcome, " + msg + "!";

	}

	/**
	 * 外部访问接口需要先编码message=new AES().encryptToBase64String(result, userid);
	 *  这里是供外部接口调用
	 * @param userid
	 * @param message
	 */
	@RequestMapping(value = "nocheat")
	public String wsnocheat(@RequestParam(value = "userid") String userid,
			@RequestParam(value = "message") String message) {
		message = message.replace(" ", "+");
		
		try {
			 System.out.println("3333333333"+linkServer.ws(userid, message));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * @MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。 topic/getResponse如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
	 *                                                                      welcome
	 * @param message
	 * @return 广播
	 * @throws Exception
	 */
	@MessageMapping(Constant.FORETOSERVERPATH)
	// @SendTo(Constant.PRODUCERPATH)
	public String say(String message) throws Exception {
		template.convertAndSend(Constant.PRODUCERPATH, message);
		return "Welcome, " + message + "!";
	}
}
