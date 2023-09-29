package org.koffa.recipefrontend;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.koffa.recipefrontend.MainUi.StageReadyEvent;
import org.koffa.recipefrontend.gui.get.GetBox;
import org.koffa.recipefrontend.gui.send.SendBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final SendBox sendBox;
    private final GetBox getBox;
    @Value("${websocket.url}")
    private String url;
    public StageInitializer(SendBox sendBox, GetBox getBox) {
        this.sendBox = sendBox;
        this.getBox = getBox;
    }

    /**
     * This method is called when the application is ready to start
     * @param event the event that is fired when the application is ready to start
     */
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
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
