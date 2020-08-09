package com.srijan.javafx.paintIndia;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class Director {

    Stage stage;

    HashMap<String, Scene> scenes = new HashMap<>();
    private  static Director instance = null;
    public static Director getInstance(Stage stage){
        if (instance == null) {
            instance = new Director(stage);
        }
        return instance;
    }
    private Director(Stage stage){
        this.stage = stage;
    }

    public void addScene(String key, Scene scene){
        scenes.put(key , scene);
    }

    public void activateScene(String key){
        stage.setScene(scenes.get(key));
    }
}
