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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.DataUserAccessFactory;
import utils.EmailValidator;
import utils.JavaFXUtils;

public class RegisterForm extends Application {

    
    Hyperlink goBackToLoginHyperLink = null;
    Button submitButton;
    Label headerLabel, nameLabel, emailLabel, secondNameLabel, passwordLabel, addressLabel;
    TextField nameField, secondNameField, emailField, addressField;
    Text validEmailAddress, scenetitle;
    PasswordField passwordField;

    // From Lecture 8
    DataUserAccess dataUserAccess = null;

    public RegisterForm() {
        this.dataUserAccess = DataUserAccessFactory.getDataUserAccessInstance();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GrowOrganic Registration");

        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // An ActionEvent Going from RegisterForm window to LoginForm/
        // The main challange is to get an instance of stage that is used in LoginFormProtoType
        // so that you could call stage.show() to display the Login Screen
        LoginFormProtoType.goBackToLogInFormProtoType(goBackToLoginHyperLink, primaryStage);

        // An ActionEven Going from RegisterForm window to LoginForm
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 550);

        primaryStage.setScene(scene);

        // Set the scene in primary stage	
        scene.getStylesheets().
                add(RegisterForm.class.getResource("../css/LoginProtoType.css").toExternalForm());
        primaryStage.show();

    }

    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add Header

        // Set menu bar in here
        // Set menu bar in here
        //Creates text object that cannot be edited.
        //sSets the text to Welcome, and assigns it to a variable named scenetitle.
        scenetitle = new Text("Fill In Register Form");
        //uses the setFont() method to set the font family, weight, and size of the scene title variable.
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        scenetitle.setFill(Color.GOLD);
        
        gridPane.add(scenetitle, 0, 0, 2, 1);
        gridPane.setHalignment(scenetitle, HPos.CENTER);
        gridPane.setMargin(scenetitle, new Insets(20, 0, 20, 0));

        // Add Name Label
        nameLabel = new Label("First Name : ");
        gridPane.add(nameLabel, 0, 1);

        // Add Name Text Field
        nameField = new TextField();

        gridPane.add(nameField, 1, 1);

        // Second Name Label
        secondNameLabel = new Label("Second Name : ");
        gridPane.add(secondNameLabel, 0, 2);

        // Add Name Text Field
        secondNameField = new TextField();
        nameField.setPrefHeight(20);
        gridPane.add(secondNameField, 1, 2);

        // Add Email Label
        emailLabel = new Label("Email ID : ");
        gridPane.add(emailLabel, 0, 3);

        // Add Email Text Field
        emailField = new TextField();
        emailField.setPrefHeight(20);
        gridPane.add(emailField, 1, 3);

        //Email Validation
        validEmailAddress = new Text();
        gridPane.add(validEmailAddress, 2, 3);
        emailField.focusedProperty().addListener(new EmailFieldChangeListenerHandler());

        // Add Password Label
        passwordLabel = new Label("Password : ");
        gridPane.add(passwordLabel, 0, 4);

        // Add Password Field
        passwordField = new PasswordField();
        passwordField.setPrefHeight(20);
        gridPane.add(passwordField, 1, 4);

        // Add Address Label
        addressLabel = new Label("Address : ");
        gridPane.add(addressLabel, 0, 5);

        // Add address Text Field
        addressField = new TextField();
        addressField.setPrefHeight(20);
        gridPane.add(addressField, 1, 5);

        // Add Submit Button
        submitButton = new Button("Sign Up");
        submitButton.setPrefHeight(20);
        submitButton.setDefaultButton(true);
        submitButton.setStyle("-fx-font: 12 arial; -fx-base: #00BFFF;");
        submitButton.setPrefWidth(200);
        gridPane.add(submitButton, 1, 6, 1, 1);
        gridPane.setHalignment(submitButton, HPos.CENTER);
        gridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        //Hyper link
        goBackToLoginHyperLink = new Hyperlink("                    Click Here To Go Back To Login");
        goBackToLoginHyperLink.setStyle("-fx-font: 12 arial; -fx-base: #ee2211;");
        gridPane.add(goBackToLoginHyperLink, 1, 6, 9, 9);

        //Action events For Controls on gui
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nameField.getText().isEmpty()) {
                    JavaFXUtils.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter your first name");
                    return;
                }
                if (secondNameField.getText().isEmpty()) {
                    JavaFXUtils.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter your second name");
                    return;
                }
                // Check emailField is filled in valid and no existing email  in the users_db.txt
                if (emailField.getText().isEmpty() || EmailValidator.validate(emailField.getText()) && isUserExist(emailField.getText())) {
                    JavaFXUtils.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter an email address");
                    return;
                }

                if (passwordField.getText().isEmpty()) {
                    JavaFXUtils.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter a password");
                    return;
                }

                if (addressField.getText().isEmpty()) {
                    JavaFXUtils.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter an address");
                    return;
                }

                String firstName = nameField.getText();
                String lastName = secondNameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String address = addressField.getText();

                // Once value has been retrieved, values can be set to user
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(password);
                user.setAddress(address);

                // Once an instance of user has been created, 
                // we can pass user as an argument into create Method in DataAccess
                dataUserAccess.create(user);

                // Code for storing user that is currently registering into databse
                JavaFXUtils.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Registration Successful!", "Welcome " + nameField.getText());
            }
        });
    }

    // if email field has been changes, notify to the user on the screen
    // whether this email is valid/registered or not.
    class EmailFieldChangeListenerHandler implements ChangeListener<Boolean> {

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (!newValue) {
                String emailFromTextField = emailField.getText();
                if (EmailValidator.validate(emailFromTextField)) {
                    if (isUserExist(emailFromTextField)) {
                        validEmailAddress.setText("Email has been registered already");
                        validEmailAddress.setFill(Color.rgb(210, 39, 30));
                        submitButton.setDisable(true);
                    } else {
                        validEmailAddress.setText("Email is Valid");
                        validEmailAddress.setFill(Color.rgb(21, 117, 84));
                        submitButton.setDisable(false);
                    }
                } else {
                    validEmailAddress.setText("Email Is Not Valid");
                    validEmailAddress.setFill(Color.rgb(210, 39, 30));
                    submitButton.setDisable(true);
                }
            }
        }
    }


    /**
     * 
     * @param email a string representation of email
     * @return true if user exists in database, otherwise false
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
