package org.koffa.recipefrontend.chat;

import org.koffa.recipefrontend.gui.get.FullRecipe;
import org.koffa.recipefrontend.pojo.ChatMessage;
import org.koffa.recipefrontend.pojo.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
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

    /**
     * Constructor for ChatWebSocketClient
     * @param url url of the websocket
     * @param fullRecipe instance of FullRecipe
     */

    public ChatWebSocketClient(@Value("${websocket.url}")  String url, FullRecipe fullRecipe) {
        this.url = url;
        this.fullRecipe = fullRecipe;
    }

    /**
     * Connects to the websocket
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void connect() throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        this.stompSession = stompClient.connectAsync(url,sessionHandler).get();
    }

    /**
     * Subscribes to the websocket
     * @param id id of the recipe to subscribe to
     */
    public void subscribe(long id) {
        stompSession.subscribe("/topic/"+id, this);
    }

    /**
     * Sends a message to the websocket
     * @param id id of the recipe to send to
     * @param message message to send
     */
    public void send(long id, Message message) {
        stompSession.send("/app/chat/"+id, message);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return ChatMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        fullRecipe.newMessage(((ChatMessage) payload));
    }
}