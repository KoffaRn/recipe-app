package org.koffa.recipefrontend;

import org.koffa.recipefrontend.gui.get.FullRecipe;
import org.koffa.recipefrontend.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
@Component
@Scope("prototype")
public class ChatWebSocketClient implements StompFrameHandler {
    private StompSession stompSession;
    private final String url;
    private final FullRecipe fullRecipe;

    public ChatWebSocketClient(@Value("${websocket.url}")  String url, FullRecipe fullRecipe) {
        this.url = url;
        this.fullRecipe = fullRecipe;
    }
    public void connect() throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        this.stompSession = stompClient.connectAsync(url,sessionHandler).get();
    }
    public void subscribe(long id) {
        stompSession.subscribe("/topic/"+id, this);
    }
    public void send(long id, Message message) {
        stompSession.send("/app/chat/"+id, message);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        fullRecipe.newMessage(((Message) payload));
    }
}