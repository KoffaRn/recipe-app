package org.koffa.recipefrontend;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.koffa.recipefrontend.MainUi.StageReadyEvent;
import org.koffa.recipefrontend.gui.LoggerBox;
import org.koffa.recipefrontend.gui.SendBox;
import org.koffa.recipefrontend.gui.SendGui;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private SendBox sendBox;
    public StageInitializer(SendBox sendBox) {
        this.sendBox = sendBox;
    }
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        VBox parent = new VBox();
        parent.getChildren().add(sendBox);
        stage.setScene(new Scene(parent, 800, 600));
        stage.show();
    }
}
