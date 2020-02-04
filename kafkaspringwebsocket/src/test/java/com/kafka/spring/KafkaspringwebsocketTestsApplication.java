package com.kafka.spring;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.kafka.spring.model.Message;
import com.kafka.spring.model.Messages;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KafkaspringwebsocketTestsApplication {

	  @Value("${local.server.port}")
	    private int port;
	    private String URL;
	    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	  //test case for sending the messages to Kafka and browser
	    private static final String Producer_EndPoint = "/app/message";
	    private static final String Consumer_EndPonit = "/topic/message";
	    
	    
	    
	    private static final String Kafka_Producer_EndPoint = "/topic/message";
	    private static final String Kafka_Consumer_EndPonit = "/topic/consumeMessage";
	    
	    
	  //test case for sending the messages from Kafka to browser
	    private static final String Kafka_Producers_EndPoint = "/topic/messages";
	    private static final String Kafka_Consumers_EndPonit = "/topic/pushNotification";
	    private CompletableFuture<Message> completableFuture;
	    
	    private CompletableFuture<Messages>  completableFutureMessages;

	    @Before
	    public void setup() {
	        completableFuture = new CompletableFuture<>();
	        completableFutureMessages = new CompletableFuture<>();
	        URL = "ws://localhost:" + port + "/websocket";
	    }

	   //test case for sending the messages to Kafka to browser
	    @Test
	    public void testMessagePassEndpoint() throws InterruptedException, ExecutionException, TimeoutException {
	        String uuid = UUID.randomUUID().toString();
	       

	        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
	        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

	        StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
	        }).get(1, SECONDS);

	        stompSession.subscribe(Consumer_EndPonit, new CreateMessageStompFrameHandler());
	        System.out.println("<=============================Automatically Seding messages to Kafka and updating browser and Spring boot from Test file================================================>");
	        stompSession.send(Producer_EndPoint, new Message(1,"Praveen","8989daf"));
	        Message messageStateAfterMove = completableFuture.get(5, SECONDS);
	        assertNotNull(messageStateAfterMove);
	    }

	    

		  //test case for sending the message from Kafka to browser
	    @Test
	    public void testMessageFromKafka() throws InterruptedException, ExecutionException, TimeoutException {
	        String uuid = UUID.randomUUID().toString();

	        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
	        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

	        StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
	        }).get(20, SECONDS);

	        stompSession.subscribe(Kafka_Consumer_EndPonit , new CreateMessageStompFrameHandler());
	        stompSession.send(Kafka_Producer_EndPoint, new Message());
	        System.out.println("<=============================kindly Send message({id:1,name:Praveen,date:jkjkj90}) from Kafka within 10 seconds it will update Spring boot and browser================================================>");
	        Message kafkaMessage = completableFuture.get(15, SECONDS);
	        assertNotNull(kafkaMessage);
	    }

	   
	    //test case for sending the messages from Kafka to browser
	    @Test
	    public void testMessagesFromKafka() throws InterruptedException, ExecutionException, TimeoutException {
	        String uuid = UUID.randomUUID().toString();

	        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
	        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

	        StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
	        }).get(20, SECONDS);

	        stompSession.subscribe(Kafka_Consumers_EndPonit , new CreateMessagesStompFrameHandler());
	        stompSession.send(Kafka_Producers_EndPoint, new Messages());
	        System.out.println("<=============================kindly Send Messages from Kafka within 20 seconds it will update Spring boot and browser================================================>");
	        Messages kafkaMessages = completableFutureMessages.get(20, SECONDS);
	        assertNotNull(kafkaMessages);
	    }

	   
	    private List<Transport> createTransportClient() {
	        List<Transport> transports = new ArrayList<>(1);
	        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
	        return transports;
	    }

	    private class CreateMessageStompFrameHandler implements StompFrameHandler {
	        @Override
	        public Type getPayloadType(StompHeaders stompHeaders) {
	            System.out.println(stompHeaders.toString());
	            return Message.class;
	        }

	        @Override
	        public void handleFrame(StompHeaders stompHeaders, Object o) {
	            System.out.println((Message) o);
	            completableFuture.complete((Message) o);
	        }
	    }
	    
	    
	    private class CreateMessagesStompFrameHandler implements StompFrameHandler {
	        @Override
	        public Type getPayloadType(StompHeaders stompHeaders) {
	            System.out.println(stompHeaders.toString());
	            return Messages.class;
	        }

	        @Override
	        public void handleFrame(StompHeaders stompHeaders, Object o) {
	            System.out.println((Messages) o);
	            completableFutureMessages.complete((Messages) o);
	        }
	    }
  
}
