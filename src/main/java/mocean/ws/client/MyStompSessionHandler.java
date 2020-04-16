package mocean.ws.client;

import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;

import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSession.Receiptable;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import mocean.ws.util.AES;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

	private final CountDownLatch latch;

	public MyStompSessionHandler(final CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		/*
		 * Map<String, String> headers = connectedHeaders.toSingleValueMap(); for
		 * (Entry<String, String> kv : headers.entrySet()) {
		 * System.out.println(kv.getKey() + " - " + kv.getValue()); }
		 * System.out.println("connected session : "+session.getSessionId());
		 */
		session.setAutoReceipt(true);
		try {
			String userid = "123";
			String result = "hahahaaaa";
			String base64String = new AES().encryptToBase64String(result, userid);
			System.out.println(userid + "解码前：" + result.length() + " " + result);
			System.out.println(userid + "解码后：" + base64String.length() + " " + base64String);

			session.subscribe("/user/" + userid + "/msg", new StompFrameHandler() {

				@Override
				public Type getPayloadType(StompHeaders headers) {
					System.out.println(headers.getDestination());
					System.out.println(headers.toString());
					return String.class;
				}

				@Override
				public void handleFrame(StompHeaders headers, Object payload) {
					System.out.println("subscribe message : ");
					System.out.println(payload);
				}

			});

			// System.out.println("==================");
			session.send("/user/" + userid + "/msg", result);
		} finally {
			latch.countDown();

		}
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		System.out.println(session.isConnected());
		boolean isConnected = false;
		if (exception instanceof ConnectionLostException || !isConnected) {
			System.out.println("-------------------------");
		}
	}

}
