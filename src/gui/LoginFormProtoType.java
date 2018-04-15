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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.AudioPlayUtil;
import utils.CheckBoxProperty;
import utils.DataUserAccessFactory;
import utils.DatabaseFactory;
import utils.JavaFXUtils;

public class LoginFormProtoType extends Application {

    // From Lecture 8, get a dataUserAccess handler
    // so that we could check whether an email address that user enters
    // is stored in our dataUserAccess.
    DataUserAccess dataUserAccess = null;
    private static AudioClip audio = null;
    
    // get an instance of DataAccess instance which has a connection to the database
    Connection dataPersistenceLayer = DatabaseFactory.getConnectionInstance();

    Label healthBenefitsLabel, userName, passwordLabel;
    Text scenetitle;
    TextField userTextField;
    PasswordField passwordField;
    Button signUpButton, loginButton;
    HBox hbox;
    Hyperlink forgotPasswordHyperLink;
    CheckBox rememberMeCheckBox;
    Tooltip rememberMeToolTip;
    GridPane grid;

    
    private static LoginFormProtoType loginFormProtoTypeInstance;
    
    private Stage primaryStage;

    // constructor, pass in LoginFormProtoType instance to static
    // variable instance, so that getInstance() method below can be retrieved.
    public LoginFormProtoType() {
        loginFormProtoTypeInstance = this;
        this.dataUserAccess = DataUserAccessFactory.getDataUserAccessInstance();
        this.audio = AudioPlayUtil.getAudioClip();
    }

    // This is used for other classes to get an instance of LoginFormProtoType
    // Once other classes get a handle to InformationDisplayForm,
    // other classes will be able to call method in LoginFormProtoType,
    // thus providing functionality for other classes to go back to 
    // LoginFormProtoType window, e.g. calling goBackToLogInFormProtoType method from RegisterForm
    public static LoginFormProtoType getInstance() {
        return loginFormProtoTypeInstance;
    }

    public void show() {
        this.primaryStage.show();
    }

    public void start(final Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("GrowOrganic Login");

        //Create borderPane
        BorderPane borderPane = new BorderPane();
        //

        //GRID PANEL STARTS
        //Creates GridPane object and assigns it to the variable names grid.
        grid = new GridPane();
        //Alignment property changes the default position of the grid from the top left of the scene to the center.
        grid.setAlignment(Pos.CENTER);
        //Gap property manages spacing between rows+columns
        grid.setHgap(10);
        grid.setVgap(10);
        //Padding property manages space around edges of grid pane.
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Set menu bar in here
        //Creates text object that cannot be edited.
        //sSets the text to Welcome, and assigns it to a variable named scenetitle.
        scenetitle = new Text("GrowOrganic");
        //uses the setFont() method to set the font family, weight, and size of the scene title variable.
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
        scenetitle.setFill(Color.GOLD);

        //The grid.add() method adds the scenetitle variable to the layout grid.
        grid.add(scenetitle, 0, 1, 2, 1);

        //Username Label
        userName = new Label("User Name:");
        grid.add(userName, 0, 2);

        //Password Label
        userTextField = new TextField();
        grid.add(userTextField, 1, 2);

        passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 3);

        passwordField = new PasswordField();
        grid.add(passwordField, 1, 3);

        rememberMeCheckBox = new CheckBox("Remember Me");
        grid.add(rememberMeCheckBox, 1, 6, 1, 1);

        signUpButton = new Button("           Sign Up          ");
        signUpButton.setStyle("-fx-font: 11 arial; -fx-base: #ee2211;");

        // Link LoginForm in RegisterForm
        signUpButton.setOnAction(new SignUpButtonEventHandler());
        // Link LoginForm in RegisterForm

        grid.add(signUpButton, 0, 7, 1, 1);
        GridPane.setMargin(signUpButton, new Insets(20, 0, 20, 0));

        loginButton = new Button("            Login              ");
        hbox = new HBox(10);
        loginButton.setStyle("-fx-font: 11 arial; -fx-base: #00BFFF;");
        hbox.getChildren().add(loginButton);
        hbox.setAlignment(Pos.CENTER);
        grid.add(hbox, 1, 7, 1, 1);

        //Hyper link
        forgotPasswordHyperLink = new Hyperlink("Forgot Password?     ");
        grid.add(forgotPasswordHyperLink, 1, 8, 3, 3);
        // Hyper link action: jump to ForgotPasswordForm window
        forgotPasswordHyperLink.setOnAction(new ForgotPasswordHyperLinkEventHandler());

        //Optional to display the gridlines which is useful for debugging
        grid.setGridLinesVisible(false);
        // longinButton action: jump to Information Display
        loginButton.setOnAction(new LoginButtonEventHandler());

        // Code for Checkbox Remember me
        rememberMeCheckBox.setOnAction(new RememberMeCheckBoxEventHandler());
        // Code for Checkbox Remember me

        // Set Tooltip for rememberMeCheckBox
        rememberMeToolTip = new Tooltip();
        rememberMeToolTip.setText("If you tick it, your user name and password will be remembered the next time when you log in.");
        rememberMeCheckBox.setTooltip(rememberMeToolTip);
        // Set Tooltip for rememberCheckBox

        // Code for when App starts up, it will read checkbox.properties
        // if it is set to true, then populate the last user's login credentials into related textfields
        // if checkbox is checked
        if (CheckBoxProperty.isCheckBoxChecked()) {
            try {
                // create sql statement
                Statement sql = this.dataPersistenceLayer.createStatement();
                // select the rememberuser details from databse
                ResultSet records = sql.executeQuery("SELECT * from REMEMBERUSER");
                // populate data into related textField
                while (records.next()) {
                    userTextField.setText(records.getString("EMAIL"));
                    passwordField.setText(records.getString("PASSWORD"));
                    rememberMeCheckBox.setSelected(true);
                }
            } catch (SQLException e) {
                System.out.println("Error reading REMEMBERUSER database:  " + e.getMessage());
            }
        }

        //BorderPane
        borderPane.setCenter(grid);//
        Scene scene = new Scene(borderPane, 700, 500);//
        primaryStage.setScene(scene);
        primaryStage.show();

        //Initialize the stylesheets Variable
        scene.getStylesheets().add(LoginFormProtoType.class.getResource("../css/LoginProtoType.css").toExternalForm());

        primaryStage.show();
    }

    // Handles Remember me check box event actions
    class RememberMeCheckBoxEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            // if rememberMeCheckBox is selected
            if (rememberMeCheckBox.isSelected()) {
                // get user and password from textfield
                String user = userTextField.getText();
                String password = passwordField.getText();
                // if user and password contains nothing with rememberMeCheckBox selected
                // we still set to false
                if (user.trim().equals("") || password.trim().equals("")) {
                    CheckBoxProperty.updateCheckBoxProperty("false");
                } else {
                    // else update user and password into properties
                    CheckBoxProperty.updateCheckBoxProperty("true");
                    updateRememberUserDatabase(user, password);
                }
            } else {
                // else handles if rememberMeCheckBox is not selected
                CheckBoxProperty.updateCheckBoxProperty("false");
            }
        }
    }

    // handles forgot password hyperlink actions
    class ForgotPasswordHyperLinkEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            // Go to Register Form
            showForgetPasswordScreen();
            primaryStage.hide();
        }

    }

    // handles login actions
    class LoginButtonEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            // get user and password data from related TextField
            String user = userTextField.getText();
            String password = passwordField.getText();

            //  if user and password is stored in the databse
            if (isLoginValid(user, password)) {
                // close the current window
                primaryStage.close();
                // update properties file for case 2
                if (rememberMeCheckBox.isSelected()) {
                    updateRememberUserDatabase(user, password);
                }
                // update properties file for case 2
                // show login screen
                showLoginScreen();
            } else if (user.equals("admin") && (password.equals("admin"))) {
                // else if handles a spefical case for administrator
                primaryStage.close();
                if (rememberMeCheckBox.isSelected()) {
                    updateRememberUserDatabase(user, password);
                }
                showDatabaseScreen();
                audio.stop();
            } else {
                // Show alert for incorrect username/password
                JavaFXUtils.showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Form ERROR!", "Incorrect username/password");
            }
        }
    }

    // hanldes sign up actions
    class SignUpButtonEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            // Go to Register Form
            showSignUpScreen();
            primaryStage.hide();
        }
    }

    // handles hyperlink go back to LoginFormProtoType actions
    public static <T extends ButtonBase> void goBackToLogInFormProtoType(T hyperlink, Stage stageInstance) {
        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stageInstance.hide();
                try {
                    LoginFormProtoType.getInstance().show();
                } catch (Exception e) {
                    System.out.println("Can't Go back to LoginFormProtoType, Reason: " + e.getMessage());
                }
            }
        });
    }

    //handles label go back to LoginFormProtoType actions
    public static <T extends Labeled> void goBackToLogInFormProtoType(T label, Stage stageInstance) {
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stageInstance.hide();
                try {
                    LoginFormProtoType.getInstance().show();
                    // when going back to LoginFormProtoType, play music again
                    audio.play();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Can't Logout, Reason: " + e.getMessage());
                }
            }
        });
    }

    // generic method to open different window, e.g. InformationDisplayForm
    // ForgetPasswordForm, RegisterForm
    public <T extends Application> void showScreen(Class<T> clazz) {
        try {
            final Stage stage = new Stage();
            T instance = clazz.newInstance();
            instance.start(stage);
        } catch (Exception ex) {
            System.out.println("Show" + clazz.getSimpleName() + " Screen Failed: error: " + ex.toString());
        }
    }

    public void showDatabaseScreen() {
        showScreen(DatabaseScreen.class);
    }
    
    // show InformationDisplayForm window
    public void showLoginScreen() {
        showScreen(InformationDisplayForm.class);
    }

    //show ForgetPasswordForm window
    public void showForgetPasswordScreen() {
        showScreen(ForgetPasswordForm.class);
    }

    // show RegisterForm window
    public void showSignUpScreen() {
        showScreen(RegisterForm.class);
    }

    /**
     *
     * @param email a string representation of email
     * @param password a string representation of password
     * @return true if login is valid otherwise false
     */
    private boolean isLoginValid(String email, String password) {
        ArrayList<User> users = dataUserAccess.retrieve();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email) && users.get(i).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param userEmail a string representation of userEmail
     * @param password a string representation of password Update user/password
     * pair into REMEMBERUSER database
     */
    private void updateRememberUserDatabase(String userEmail, String password) {
        try {
            // Write into REMEMBERUSER database, it has following attributes
            // ID, EMAIL, PASSWORD
            // Database has been initilised an empty row for use before starting GrowOragnic Application, 
            // so in here there is no need to take care of performing inserting operations
            // Only Update operations is performed
            String createUserSql = "Update REMEMBERUSER Set"
                    + " EMAIL='" + userEmail
                    + "', PASSWORD='" + password
                    + "' WHERE ID=1";
            Statement statement = dataPersistenceLayer.createStatement();
            statement.executeUpdate(createUserSql);

        } catch (SQLException e) {
            System.out.println("Write to REMEMBERUSER database failed: " + e.getMessage());
        }
    }

}
