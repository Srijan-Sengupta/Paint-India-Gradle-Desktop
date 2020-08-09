package com.srijan.javafx.paintIndia;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PaintIndiaController implements Runnable{

    public static boolean savedBefore = false;
    private File savedFile;
    public FileChooser fileChooser = new FileChooser();
    @FXML
    public Button undo;

    @FXML
    public Button clear;
    //Drawing pane:
    @FXML
    private Pane pane;
    //toggleGroup:
    @FXML
    private ToggleGroup toggleGroup;
    //Colour picker
    @FXML
    private ColorPicker colorPicker;
    // Radio Buttons:
    @FXML
    private RadioButton smallest;
    @FXML
    private RadioButton small;
    @FXML
    private RadioButton medium;
    @FXML
    private RadioButton large;
    @FXML
    private RadioButton largest;

    private double x;
    private double y;

    //Lazy initialization:
    public static PaintIndiaController instance;

    public static PaintIndiaController getInstance() {
        if (instance == null) {
            instance = new PaintIndiaController ();
        }
        return instance;
    }

    public PaintIndiaController(){
        instance = this;
    }
    //Drawing method:
    @FXML
    public void draw(MouseEvent event) {
        this.x = event.getX();
        this.y = event.getY();
        new Thread(this).start();
    }

    @FXML
    public void clear(ActionEvent event) {
        pane.getChildren().clear();
    }

    @FXML
    public void undo(ActionEvent event) {
        if (pane.getChildren().size() >= 1)
            pane.getChildren().remove(pane.getChildren().size() - 1);
    }
    public void onSave(ActionEvent event) {
        if (!savedBefore) {
            fileChooser.setTitle("Save your drawing");
            savedFile = fileChooser.showSaveDialog(new Stage());
        }
            if (savedFile != null){
                WritableImage writableImage = pane.snapshot(new SnapshotParameters(), new WritableImage((int)Math.ceil(pane.getWidth()),(int)Math.ceil(pane.getHeight())));
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", savedFile);
                    savedBefore = true;
                    Toast.makeText(Main.getStage(),"Saved successfully",Toast.LONG,Toast.SHORT,Toast.LONG);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


    }

    public void onSaveAs(ActionEvent event) {
            fileChooser.setTitle("Save your drawing");
            savedFile = fileChooser.showSaveDialog(new Stage());
        if (savedFile != null){
            WritableImage writableImage = pane.snapshot(new SnapshotParameters(), new WritableImage((int)Math.ceil(pane.getWidth()),(int)Math.ceil(pane.getHeight())));
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", savedFile);
                savedBefore = true;
                Toast.makeText(Main.getStage(),"Saved successfully",Toast.LONG,Toast.SHORT,Toast.LONG);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onOpen(ActionEvent event) {
        savedFile = fileChooser.showOpenDialog(new Stage());
        if (savedFile != null) {
            //Logger.getLogger("0").log(Level.INFO,savedfile.getAbsolutePath() + "\t" + savedfile.getPath());
            Image image;
            image = new Image("File:"+ savedFile.getPath());
            BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, null);
            pane.setBackground(new Background(backgroundImage));
            savedBefore = true;
            pane.setMinSize(image.getWidth(),image.getHeight());
        }
    }

    public void onClose(ActionEvent event) {
        if (savedBefore) {
            Platform.exit();
            System.exit(0);
        }
        else{
            alert();
        }
    }

    private void alert() {
        ButtonType save = new ButtonType("Save");
        ButtonType closeWithoutSaving = new ButtonType("Close without saving");
        Alert alert = new Alert(Alert.AlertType.WARNING,"Do you really want exit? The file is not saved",closeWithoutSaving,save,ButtonType.CANCEL);
        alert.showAndWait();
        ButtonType buttonType = alert.getResult();
        if (buttonType.equals(save)){
            onSave(null);
        }else if (buttonType.equals(closeWithoutSaving)){
            onForceClose(null);
        }
    }

    @Override
    public void run() {
        Circle circle = new Circle(
                this.getX(),
                this.getY(),
                ((penSize) toggleGroup.getSelectedToggle().getUserData()).getRadius(),
                colorPicker.getValue()
        );

        Platform.runLater(() -> pane.getChildren().add(circle));
    }

    private double getY() {
        return y;
    }

    private double getX() {
        return x;
    }

    public void onForceClose(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void about(ActionEvent event) {
        Stage stage = new Stage();
        stage.initOwner(Main.getStage());
        stage.setResizable(false);
        stage.setScene(SceneRepository.ABOUT_SCENE.getScene());
        stage.show();
    }

    private enum penSize {
        SMALLEST(10),
        SMALL(15),
        MEDIUM(20),
        LARGE(25),
        LARGEST(30);

        private final int radius;

        penSize(int radius) {
            this.radius = radius;
        }

        public int getRadius() {
            return radius;
        }
    }

    @FXML
    public void initialize() {
        medium.setSelected(true);

        smallest.setUserData(penSize.SMALLEST);
        small.setUserData(penSize.SMALL);
        medium.setUserData(penSize.MEDIUM);
        large.setUserData(penSize.LARGE);
        largest.setUserData(penSize.LARGEST);

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName("untitled.png");
        colorPicker.setValue(Color.BLACK);
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
    }
}
