package mocean.ws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import mocean.ws.util.Constant;


@Service
public class WebSocketService {
	
	@Autowired
    private SimpMessagingTemplate template;

    /**
     * 广播
     * 发给所有在线用户
     * /topic/getResponse
     * @param msg
     */
    public void sendMsg(String msg) {
        template.convertAndSend(Constant.PRODUCERPATH, msg);
    }

    /**
     * 发送给指定用户
     * @param users
     * @param msg  /msg
     */
    public void send2Users(List<String> users, String msg) {
        users.forEach(userName -> {
            template.convertAndSendToUser(userName, Constant.P2PPUSHPATH, msg);
        });
    }

}
