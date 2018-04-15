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
import businessLogic.User;
import dataAccess.DataUserAccess;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.DataUserAccessFactory;
import utils.EmailValidator;
import utils.JavaFXUtils;
import utils.SendEmail;

public class ForgetPasswordForm extends Application {

    // This is gmail account and password information for this app
    // Which is used to send forget password information to users' email
    private static final String SENDER_GMAIL_ACCOUNT = "GrowOrganicbis@gmail.com";
    private static final String SENDER_GMAIL_PASSWORD = "Bis20188";

    // From Lecture 8, get a dataUserAccess handler
    // so that we could check whether an email address that user enters
    // is stored in our dataUserAccess.
    DataUserAccess dataUserAccess = null;

    Label userName;
    Text scenetitle, validEmailAddress;
    Button sendPasswordToYourEmailButton;
    TextField emailField;
    Hyperlink goBackToLoginHyperLink;
    HBox hbox;

    boolean isValidEmailAddress = false;

    public ForgetPasswordForm() {
        // get an instance of DataAccess instance which has a connection to the database
        this.dataUserAccess = DataUserAccessFactory.getDataUserAccessInstance();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GrowOrganic Forgot Password");

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

        //Creates text object that cannot be edited.
        //Sets the text to Welcome, and assigns it to a variable named scenetitle.
        scenetitle = new Text("Forgot Password?");
        //uses the setFont() method to set the font family, weight, and size of the scene title variable.
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        scenetitle.setFill(Color.GOLD);

        //The grid.add() method adds the scenetitle variable to the layout grid.
        grid.add(scenetitle, 0, 1, 2, 1);

        //Your Email Address Label
        userName = new Label("Your Email Address:");
        grid.add(userName, 0, 2);

        //Your Email Address TextField
        emailField = new TextField();
        grid.add(emailField, 1, 2);

        // Validate Email Text
        validEmailAddress = new Text();
        grid.add(validEmailAddress, 2, 2);

        sendPasswordToYourEmailButton = new Button("Send Password to Your Email");
        hbox = new HBox(10);
        sendPasswordToYourEmailButton.setStyle("-fx-font: 11 arial; -fx-base: #00BFFF;");
        sendPasswordToYourEmailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // use the value that is retrieved from emailTextField EventAction
                // use boolean isValidEmailAddress to control whether to send email or not
                if (isValidEmailAddress) {
                    // Send Forget password details to user email
                    String emailSubject = "Grow Oraganic Forgot Password";
                    String emailBody = "Your email is: " + emailField.getText() + " and your password is: " + findPasswordFromEmail(emailField.getText());
                    String[] emailReceipent = new String[]{emailField.getText()};
                    SendEmail.sendFromGMail(SENDER_GMAIL_ACCOUNT, SENDER_GMAIL_PASSWORD, emailReceipent, emailSubject, emailBody);
                    // Send Forget password details to user email
                    // Alert User email has been sent successfully
                    JavaFXUtils.showAlert(Alert.AlertType.CONFIRMATION, grid.getScene().getWindow(), "Password has been sent!", "Please check your email inbox");
                }
            }
        });

        hbox.getChildren().add(sendPasswordToYourEmailButton);
        hbox.setAlignment(Pos.CENTER);
        grid.add(hbox, 1, 3, 1, 1);

        // Valid Email Address is in the database
        // when the emailField has been changed, it will trigger following code
        emailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    // get email from TextField
                    String emailFromTextField = emailField.getText();
                    // If email format is the correct format
                    if (EmailValidator.validate(emailFromTextField)) {
                        // if email is in our database
                        if (isUserExist(emailFromTextField)) {
                            validEmailAddress.setText("Email is registered and password can be sent to email");
                            validEmailAddress.setFill(Color.rgb(21, 117, 84));
                            sendPasswordToYourEmailButton.setDisable(false);
                            // email is valid and can be sent to user's email, set to true
                            isValidEmailAddress = true;
                        } else {
                            validEmailAddress.setText("Email is not registered in our database");
                            validEmailAddress.setFill(Color.rgb(210, 39, 30));
                            sendPasswordToYourEmailButton.setDisable(true);
                            isValidEmailAddress = false;
                        }
                    } else {
                        // if email is not in the correct format
                        validEmailAddress.setText("Email is not in the correct format");
                        validEmailAddress.setFill(Color.rgb(210, 39, 30));
                        sendPasswordToYourEmailButton.setDisable(true);
                        isValidEmailAddress = false;
                    }
                }
            }
        });
        // Send Password to your email

        // Hyper link to go back to login
        goBackToLoginHyperLink = new Hyperlink("           Go Back to Login     ");
        grid.add(goBackToLoginHyperLink, 1, 4, 1, 1);
        LoginFormProtoType.goBackToLogInFormProtoType(goBackToLoginHyperLink, primaryStage);
        // Hyper link to go back to login

        //Optional to display the gridlines which is useful for debugging
        grid.setGridLinesVisible(false);

        Scene scene = new Scene(grid, 800, 450);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().
                add(ForgetPasswordForm.class.getResource("../css/LoginProtoType.css").toExternalForm());
        primaryStage.show();

    }

    /**
     * 
     * @param email a String representation of email address
     * @return a string representation of password relating to email if it is 
     * found, otherwise, "Password Not Found" is returned
     */
    public String findPasswordFromEmail(String email) {
        ArrayList<User> users = dataUserAccess.retrieve();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                return users.get(i).getPassword();
            }
        }
        return "Password Not Found";
    }

    /**
     * 
     * @param email a String representation of email address
     * @return true if email exists in database, otherwise, false 
     * will be returned.
     */
    public boolean isUserExist(String email) {
        ArrayList<User> users = dataUserAccess.retrieve();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
