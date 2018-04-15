/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This handles cases whereas a menu does not have menu item
 * see stackoverflow:
 * https://stackoverflow.com/questions/10315774/javafx-2-0-activating-a-menu-like-a-menuitem
 *
 * @author Timothy
 * @param <T>
 */
public class MenuHandler<T extends Application> implements EventHandler<MouseEvent> {

    private final Class<T> newWindow;
    private final Stage stage;
    private final Stage currentStage;

    public MenuHandler(Class<T> newWindow, Stage stage, Stage currentStage) {
        this.newWindow = newWindow;
        this.stage = stage;
        this.currentStage = currentStage;
    }

    @Override
    public void handle(MouseEvent event) {
        // Make sure window(e.g. about us) won't pop up multiple times if user presses (e.g. About Us) multiple times
        T t;
        if (!stage.isShowing()) {
            try {
                t = newWindow.newInstance();
                currentStage.close();
                t.start(stage);
            } catch (Exception ex) {
                System.out.println("Show " + newWindow.getSimpleName() + " Screen Failed: error: " + ex.toString());
            }
        }
    }

}
