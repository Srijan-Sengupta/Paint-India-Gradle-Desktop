package com.srijan.javafx.paintIndia;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum SceneRepository {

    MAIN_SCENE(Main.class.getResource("/PaintIndia.fxml")),
    ABOUT_SCENE(Main.class.getResource("/About.fxml"));
    private Scene scene;

    SceneRepository(URL url){
        try {
            this.scene = new Scene(FXMLLoader.load(url));
        }catch (Exception e){
            Logger.getLogger("Javafx").log(Level.SEVERE, e.getMessage() + url);
            e.printStackTrace();
        }

    }

    public Scene getScene() {
        return scene;
    }
}

