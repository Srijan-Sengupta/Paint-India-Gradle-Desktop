package com.srijan.javafx.paintIndia;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class About {
    @FXML
    public TextArea msg;

    @FXML
    public void initialize(){
        msg.setText("Welcome to Paint-India application.\n " +
                "This is a application created using javafx by Srijan Sengupta.");
    }
}