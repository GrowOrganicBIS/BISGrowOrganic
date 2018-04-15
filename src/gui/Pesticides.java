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
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Pesticides extends Application {

    Menu goBackMenu;
    MenuBar menuBar;
    Text scenetitle;
    WebView webview;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GrowOrganic Pesticides");

        //GRID PANEL STARTS
        //Creates GridPane object and assigns it to the variable names grid.
        GridPane grid = new GridPane();
        //Alignment property changes the default position of the grid from the top left of the scene to the center.
        grid.setAlignment(Pos.CENTER);
        //Gap property manages spacing between rows+columns
        grid.setHgap(10);
        grid.setVgap(10);
        //Padding property manages space around edges of grid pane.
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Set menu bar in here
        goBackMenu = new Menu();
        // Controll to go back to the previous stage
        webview = new WebView();
        InformationDisplayForm.goBackToInformationDisplayForm(goBackMenu, primaryStage, webview);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(goBackMenu);
        grid.add(menuBar, 0, 0, 2, 1);
        menuBar.setStyle("-fx-background-color:  #B0C4DE;");
        grid.setAlignment(Pos.TOP_CENTER);

        // Set menu bar in here
        //Creates text object that cannot be edited.
        //sSets the text to Welcome, and assigns it to a variable named scenetitle.
        scenetitle = new Text("         Pesticides");
        //uses the setFont() method to set the font family, weight, and size of the scene title variable.

        scenetitle.setFill(Color.GOLD);
        scenetitle.setFont(Font.font(null, FontWeight.BOLD, 65));

        //The grid.add() method adds the scenetitle variable to the layout grid.
        grid.add(scenetitle, 0, 1, 2, 1);

        // Add Youtube video
        webview.getEngine().load(
                "https://www.youtube.com/embed/aTm7i84mcMI"
        );
        webview.setPrefSize(640, 390);
        // Add Youtube video
        grid.add(webview, 0, 3);

        //Optional to display the gridlines which is useful for debugging
        grid.setGridLinesVisible(false);

        Scene scene = new Scene(grid, 950, 650);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().
                add(Fruits.class.getResource("../css/LoginProtoType.css").toExternalForm());
        primaryStage.show();

    }
}
