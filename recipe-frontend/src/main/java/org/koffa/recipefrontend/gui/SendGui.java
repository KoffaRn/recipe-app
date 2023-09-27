package org.koffa.recipefrontend.gui;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
public class SendGui  {
    private final LoggerBox loggerBox;
    public SendGui(LoggerBox loggerBox){
        this.loggerBox = loggerBox;
    }


}
