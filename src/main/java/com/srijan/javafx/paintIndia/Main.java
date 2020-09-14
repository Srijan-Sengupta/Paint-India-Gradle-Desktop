package com.srijan.javafx.paintIndia;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static Stage stage ;

    // Entry point of the Application:
    @Override
    public void start(Stage primaryStage) {
        //com.sun.glass.ui.Application.GetApplication().setName("app test");
        Director director = Director.getInstance(primaryStage);
        director.addScene("main", SceneRepository.MAIN_SCENE.getScene());
        primaryStage.show();
        primaryStage.setTitle("Paint-India");
        director.activateScene("main");
        primaryStage.centerOnScreen();
        Main.stage = primaryStage;
        Main.getStage().setOnCloseRequest(event -> {

            PaintIndiaController controller = PaintIndiaController.getInstance();
            ButtonType save = new ButtonType("Save");
            ButtonType closeWithoutSaving = new ButtonType("Close without saving");
            Alert alert = new Alert(Alert.AlertType.WARNING,"Do you really want exit? The file is not saved",closeWithoutSaving,save);
            alert.showAndWait();
            ButtonType buttonType = alert.getResult();
            if (buttonType.equals(save)){
                controller.onSave(null);
            }else if (buttonType.equals(closeWithoutSaving)){
                controller.onForceClose(null);
            }
        });
        primaryStage.getIcons().add(new Image("/Paint-India.png"));
    }

    public static Stage getStage() {
        return stage;
    }
}
