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
import javafx.stage.Stage;

public class ContactingUs extends Application {

    Menu goBackMenu;
    MenuBar menuBar;
    Label label;
    Text scenetitle;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GrowOrganic About Us");

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

        // Set menu bar for Go Back
        goBackMenu = new Menu();
        InformationDisplayForm.goBackToInformationDisplayForm(goBackMenu, primaryStage, null);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(goBackMenu);
        grid.add(menuBar, 0, 0, 2, 1);
        menuBar.setStyle("-fx-background-color:  #B0C4DE;");
        grid.setAlignment(Pos.TOP_CENTER);

        label = new Label("GrowOrganic Allowing You To Live A Healthier Lifestyle. If you are encountering any issues"
                + " regarding our application, would like to delete your account with us or simply would like to ask us a "
                + "question don't hesitate to contact us."
                + "Email us with any questions or queries to our company email address at GrowOrganicBIS@gmail.com"
                + " We would be happy to answer your questions or to set up a skype meeting with you, We aim to respond within 24hrs."
                + " Thank you for visiting us and we hope you enjoy your time on our application!");

        label.setMaxWidth(650);
        label.setWrapText(true);
        grid.add(label, 0, 7);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("", FontWeight.NORMAL, 20));

        // Set menu bar in here
        //Creates text object that cannot be edited.
        //sSets the text to Welcome, and assigns it to a variable named scenetitle.
        scenetitle = new Text("        Contact Us");
        //uses the setFont() method to set the font family, weight, and size of the scene title variable.

        scenetitle.setFill(Color.GOLD);
        scenetitle.setFont(Font.font(null, FontWeight.BOLD, 65));

        //The grid.add() method adds the scenetitle variable to the layout grid.
        grid.add(scenetitle, 0, 3, 2, 1);

        //Optional to display the gridlines which is useful for debugging
        grid.setGridLinesVisible(false);

        Scene scene = new Scene(grid, 950, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().
                add(ContactingUs.class.getResource("../css/ContactUs.css").toExternalForm());
        primaryStage.show();

    }
}
