package mocean.ws.client;

//import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import org.springframework.messaging.converter.StringMessageConverter;
//import org.springframework.messaging.simp.stomp.StompFrameHandler;
//import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

/**
 * java模拟客户端
 * 
 * @author Administrator
 *
 */

public class StompClientTest {
	public static void main(String[] args) throws Exception {

		List<Transport> transports = new ArrayList<>(1);
		WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
		webSocketContainer.setDefaultMaxTextMessageBufferSize(50 * 1024 * 1024);// 50M
		webSocketContainer.setDefaultMaxBinaryMessageBufferSize(50 * 1024 * 1024);
		transports.add(new WebSocketTransport(new StandardWebSocketClient(webSocketContainer)));

		WebSocketClient webSocketClient = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
		stompClient.setInboundMessageSizeLimit(50 * 1024 * 1024);
		stompClient.setMessageConverter(new StringMessageConverter());
		stompClient.setTaskScheduler(new DefaultManagedTaskScheduler()); // for heartbeats

		String url = "ws://localhost:8960/endpointWisely";
		CountDownLatch latch = new CountDownLatch(1);
		StompSessionHandler sessionHandler = new MyStompSessionHandler(latch);
		stompClient.connect(url, sessionHandler);
//		ListenableFuture<StompSession>  rc = 
//		StompSession session= rc.get(0, null);
//		session.
		try {
			latch.await();
		} catch (Exception e) {

		}

		/*
		 * ListenableFuture<StompSession> ret =stompClient.connect(url, sessionHandler);
		 * ret.addCallback(new ListenableFutureCallback<StompSession>() {
		 * 
		 * @Override public void onSuccess(StompSession session) {
		 * 
		 * session.setAutoReceipt(true); System.out.println("hahah");
		 * session.subscribe("/user/123/msg", new StompFrameHandler() {
		 * 
		 * @Override public void handleFrame(StompHeaders headers, Object payload) {
		 * System.out.println("ListenableFutureCallback subscribe message : ");
		 * System.out.println(payload); }
		 * 
		 * @Override public Type getPayloadType(StompHeaders headers) { return
		 * String.class; }
		 * 
		 * }); // session.send("/user/123/msg", "456"); }
		 * 
		 * @Override public void onFailure(Throwable ex) { ex.printStackTrace(); } });
		 */

		// Thread.sleep(600000);
	}
}
