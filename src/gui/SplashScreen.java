/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Timothy
 */
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.media.AudioClip;
import utils.AudioPlayUtil;

/**
 * Example of displaying a splash page for a standalone JavaFX application
 * Derived from github with modification:
 * https://gist.github.com/jewelsea/1588531
 */
public class SplashScreen extends Application {

    public static final String APPLICATION_ICON = "http://cdn1.iconfinder.com/data/icons/Copenhagen/PNG/32/people.png";
    public static final String SPLASH_IMAGE = "http://www.organicseedlings.co.nz/wp-content/uploads/2016/10/Growing-banner.jpg";

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private Stage mainStage;
    private static final int SPLASH_WIDTH = 676;
    private static final int SPLASH_HEIGHT = 227;

    private static AudioClip audio = AudioPlayUtil.getAudioClip();
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void init() {
        ImageView splash = new ImageView(new Image(SPLASH_IMAGE, 676, 207, false, false));
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        progressText = new Label("Grow Organic App");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle(
                "-fx-padding: 5; "
                + "-fx-background-color: cornsilk; "
                + "-fx-border-width:5; "
                + "-fx-border-color: "
                + "linear-gradient("
                + "to bottom, "
                + "chocolate, "
                + "derive(chocolate, 50%)"
                + ");"
        );
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception {
        final Task<List<String>> friendTask = new Task<List<String>>() {
            @Override
            protected List<String> call() throws InterruptedException {
                List<String> loadedInfo = new ArrayList<>();
                List<String> infoTobeLoaded = Arrays.asList("Initilising Grow Organic App", "GrowOrganic Team: Lead Developer: Timothy O'Keeffe", "Gary Murphy", "Denis Keane", "Keven O'Shea", "Ruari Moynihan");
                
                // Starting music when app starts
                audio.play();
                
                updateMessage("Loading Resources . . .");
                for (int i = 0; i < infoTobeLoaded.size(); i++) {
                    // Control infoTobeLoaded
                    Thread.sleep(1000);
                    updateProgress(i + 1, infoTobeLoaded.size());
                    String nextInfo = infoTobeLoaded.get(i);
                    loadedInfo.add(nextInfo);
                    updateMessage("Made by: " + nextInfo);
                }
                Thread.sleep(400);
                updateMessage("Initilisation Complete");

                return loadedInfo;
            }
        };

        showSplash(initStage, friendTask, () -> showMainStage());
        new Thread(friendTask).start();
    }

    private void showMainStage() {
        mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setTitle("Grow Organic App");
        mainStage.getIcons().add(new Image(APPLICATION_ICON));
        try {
            // Direct to LoginFormProtoType
            new LoginFormProtoType().getInstance().start(mainStage);
        } catch (Exception e) {
            System.out.println("Failed Starting LoginForm: " + e.toString());
        }
    }

    private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            }
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }

    public interface InitCompletionHandler {

        void complete();
    }
}
