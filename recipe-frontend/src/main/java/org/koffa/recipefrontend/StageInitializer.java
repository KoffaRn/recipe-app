package org.koffa.recipefrontend;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.koffa.recipefrontend.MainUi.StageReadyEvent;
import org.koffa.recipefrontend.gui.LoggerBox;
import org.koffa.recipefrontend.gui.SendBox;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private SendBox sendBox;
    public StageInitializer(SendBox sendBox) {
        this.sendBox = sendBox;
    }
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connectAsync("ws://localhost:8080/ws", sessionHandler);
        Stage stage = event.getStage();
        VBox parent = new VBox();
        parent.getChildren().add(sendBox);
        stage.setScene(new Scene(parent, 800, 600));
        stage.show();
    }
}
