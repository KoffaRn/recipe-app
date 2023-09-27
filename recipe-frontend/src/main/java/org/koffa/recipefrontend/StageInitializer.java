package org.koffa.recipefrontend;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.koffa.recipefrontend.MainUi.StageReadyEvent;
import org.koffa.recipefrontend.gui.get.GetBox;
import org.koffa.recipefrontend.gui.send.SendBox;
import org.koffa.recipefrontend.gui.send.TagBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private SendBox sendBox;
    private GetBox getBox;
    @Value("${websocket.url}")
    private String url;
    public StageInitializer(SendBox sendBox, GetBox getBox) {
        this.sendBox = sendBox;
        this.getBox = getBox;
    }
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        /* ChatWebSocketClient chatWebSocketClient = new ChatWebSocketClient(url);
        Message message = new Message();
        message.setSender("koffa");
        message.setMessage("message");
        try {
            chatWebSocketClient.connect();
            chatWebSocketClient.subscribe(1);
            chatWebSocketClient.send(1, message);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        */
        Stage stage = event.getStage();
        TabPane parent = new TabPane();
        Tab sendTab = new Tab("Send");
        sendTab.setClosable(false);
        sendTab.setContent(sendBox);
        Tab getTab = new Tab("Get");
        getTab.setClosable(false);
        getTab.setContent(getBox);
        parent.getTabs().addAll(sendTab,getTab);
        stage.setScene(new Scene(parent, 800, 600));
        stage.show();
    }
}
