/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 * DatabaseScreen contains a TableView, this is derived from javafx
 * TableViewSample.java
 * https://docs.oracle.com/javafx/2/ui_controls/TableViewSample.java.html
 *
 * @author Timothy
 */
import businessLogic.User;
import dataAccess.DataUserAccess;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.DataUserAccessFactory;
import utils.JavaFXUtils;
import utils.EmailValidator;

public class DatabaseScreen extends Application {

    Menu goBackMenu;
    MenuBar menuBar;
    Text scenetitle;

    Label passwordLeftLabel, firstNameLeftLabel, lastNameLabel, addressLabel, emailAddressLabel, goBackLabel;
    TextField passwordLeftTextField, firstNameTextField, lastNameTextField, addressTextField, emailAddressTextField;
    Button buttonInsert, buttonRetrieve, buttonUpdate, buttonDelete;
    TableView<User> table;
    TableColumn passwordCol, firstNameCol, lastNameCol, addressCol, emailCol;
    BorderPane boarderPane;

    DataUserAccess dataUserAccess = null;

    public DatabaseScreen() {
        // get an instance of DataAccess instance which has a connection to the database
        this.dataUserAccess = DataUserAccessFactory.getDataUserAccessInstance();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GrowOrganic Database");
        boarderPane = new BorderPane();
        boarderPane.setPadding(new Insets(10));

        // Set menu bar in here
        goBackMenu = new Menu();
        goBackLabel = new Label("Go Back");
        goBackMenu.setGraphic(goBackLabel);
        // Controll to go back to the previous stage
        LoginFormProtoType.goBackToLogInFormProtoType(goBackLabel, primaryStage);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(goBackMenu);
        menuBar.setStyle("-fx-background-color:  #B0C4DE;");

        // Set menu bar in here
        //Creates text object that cannot be edited.
        //sSets the text to Welcome, and assigns it to a variable named scenetitle.
        scenetitle = new Text("Database CRUD Operations");
        //uses the setFont() method to set the font family, weight, and size of the scene title variable.

        scenetitle.setFill(Color.GOLD);
        scenetitle.setFont(Font.font(null, FontWeight.BOLD, 65));

        // Table View
        table = new TableView<User>();

        passwordCol = new TableColumn("Password");
        passwordCol.setMinWidth(100);
        passwordCol.setCellValueFactory(new PropertyValueFactory<User, String>("password"));

        firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));

        lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));

        addressCol = new TableColumn("Address");
        addressCol.setMinWidth(100);
        addressCol.setCellValueFactory(new PropertyValueFactory<User, String>("address"));

        emailCol = new TableColumn("Email");
        emailCol.setMinWidth(100);
        emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        //table.getStylesheets().add("table.css");
        table.getStylesheets().add(LoginFormProtoType.class.getResource("../css/table.css").toExternalForm());
        table.getColumns().addAll(emailCol, passwordCol, firstNameCol, lastNameCol, addressCol);
        // Table View

        // Create a VBox for left side navigation
        GridPane leftGridPane = new GridPane();
        leftGridPane.setHgap(10);
        leftGridPane.setVgap(10);
        leftGridPane.setPadding(new Insets(10, 10, 10, 10));
        leftGridPane.setAlignment(Pos.CENTER);

        emailAddressLabel = new Label("Email Address:");
        emailAddressTextField = new TextField();
        leftGridPane.add(emailAddressLabel, 0, 0);
        leftGridPane.add(emailAddressTextField, 1, 0);

        passwordLeftLabel = new Label("Password:");
        passwordLeftTextField = new TextField();
        leftGridPane.add(passwordLeftLabel, 0, 1);
        leftGridPane.add(passwordLeftTextField, 1, 1);

        firstNameLeftLabel = new Label("First Name:");
        firstNameTextField = new TextField();
        leftGridPane.add(firstNameLeftLabel, 0, 2);
        leftGridPane.add(firstNameTextField, 1, 2);

        lastNameLabel = new Label("Last Name:");
        lastNameTextField = new TextField();
        leftGridPane.add(lastNameLabel, 0, 3);
        leftGridPane.add(lastNameTextField, 1, 3);

        addressLabel = new Label("Address:");
        addressTextField = new TextField();
        leftGridPane.add(addressLabel, 0, 4);
        leftGridPane.add(addressTextField, 1, 4);

        // Create button with image
        buttonInsert = createCRUDButton("../images/Add.png", "Insert");
        // Create buttonInsert/Create Action
        buttonInsert.setOnAction(new DatabaseScreen.CreateButtonEventHandler());

        // Create Retrieve button with image
        buttonRetrieve = createCRUDButton("../images/retrieveButton.jpg", "Retrieve");
        // Create buttonRetrieve Action
        buttonRetrieve.setOnAction(new DatabaseScreen.RetrieveButtonEventHandler());

        // Create Update button with image
        buttonUpdate = createCRUDButton("../images/update.png", "Update");
        // Create buttonUpdate Action
        buttonUpdate.setOnAction(new DatabaseScreen.UpdateButtonEventHandler());

        // Create Delete button with image
        buttonDelete = createCRUDButton("../images/delete.png", "Delete");
        // Create Update button action
        buttonDelete.setOnAction(new DatabaseScreen.DeleteButtonEventHandler());

        // TableView action: populate selected information into CRUD Field
        // i.e. when user selects a row from the tableview
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                User user = table.getSelectionModel().getSelectedItem();
                if (user != null) {
                    passwordLeftTextField.setText(user.getPassword());
                    firstNameTextField.setText(user.getFirstName());
                    lastNameTextField.setText(user.getLastName());
                    addressTextField.setText(user.getAddress());
                    emailAddressTextField.setText(user.getEmail());
                }
            }
        });
        // TableView action: populate selected information into CRUD Field

        HBox hbox = new HBox();
        hbox.setSpacing(25.0);
        hbox.getChildren().addAll(buttonInsert, buttonRetrieve, buttonUpdate, buttonDelete);
        leftGridPane.add(hbox, 0, 5, 2, 2);

        boarderPane.setTop(menuBar);
        boarderPane.setCenter(table);
        boarderPane.setLeft(leftGridPane);
        // Create a VBox for left side navigation

        //Adding Css file for Dashboard GUI
        boarderPane.getStylesheets().
                add(DatabaseScreen.class.getResource("../css/DatabaseScreen.css").toExternalForm());

        //Optional to display the gridlines which is useful for debugging
        Scene scene = new Scene(boarderPane, 950, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().
                add(Fruits.class.getResource("../css/DatabaseScreen.css").toExternalForm());
        primaryStage.show();

    }

    private Button createCRUDButton(String imageRelativeLocation, String buttonName) {
        Image image = new Image(DatabaseScreen.class.getResourceAsStream(imageRelativeLocation), 20, 20, false, false);
        Button button = new Button(buttonName, new ImageView(image));
        button.setStyle("-fx-font: 11 arial; -fx-base:  #A9A9A9;");
        return button;
    }

    private void retrieveTable() {
        final ObservableList<User> data = FXCollections.observableArrayList(dataUserAccess.retrieve());
        table.setItems(data);
        table.setEditable(true);
    }

    // This method is used to create/insert/update button actions
    // If user does not enter all the information in the textfield, the emptyField will be filled with text "TobeFilled"
    private User getUserFromTextField() {
        // Get value from TextField
        String password = passwordLeftTextField.getText().equals("") ? "TobeFilled" : passwordLeftTextField.getText();
        String firstName = firstNameTextField.getText().equals("") ? "TobeFilled" : firstNameTextField.getText();
        String lastName = lastNameTextField.getText().equals("") ? "TobeFilled" : lastNameTextField.getText();
        String address = addressTextField.getText().equals("") ? "TobeFilled" : addressTextField.getText();
        String emailAddress = emailAddressTextField.getText().equals("") ? "TobeFilled" : emailAddressTextField.getText();

        // Once value has been retrieved, values can be set to user
        User user = new User();
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setEmail(emailAddress);
        return user;
    }

    class RetrieveButtonEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            retrieveTable();
        }
    }

    class CreateButtonEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            User user = getUserFromTextField();
            boolean isEmailValid = EmailValidator.validate(user.getEmail());

            if (isEmailValid) {
                if (dataUserAccess.create(user)) {
                    retrieveTable();
                } else {
                    JavaFXUtils.showAlert(Alert.AlertType.ERROR, boarderPane.getScene().getWindow(), "Create Operation Failed", "email exists already");
                }
            } else {
                JavaFXUtils.showAlert(Alert.AlertType.ERROR, boarderPane.getScene().getWindow(), "Create Operation Failed", "email is not valid");
            }
        }

    }

    class UpdateButtonEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            User user = getUserFromTextField();
            dataUserAccess.update(user);
            retrieveTable();
        }
    }

    class DeleteButtonEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            // Get value from TextField
            String email = emailAddressTextField.getText();

            // Once value has been retrieved, values can be set to user
            // Once an instance of user has been created, 
            // we can pass user as an argument into create Method in DataAccess
            dataUserAccess.delete(email);
            // Call dataAccess.retrieve() to update tableview
            retrieveTable();
        }
    }
}
