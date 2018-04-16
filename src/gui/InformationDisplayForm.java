/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import utils.AudioPlayUtil;
import javafx.scene.web.WebView;

/**
 *
 * @author Timothy
 */
public class InformationDisplayForm extends Application {

    Menu getStartedMenu, growGuideMenu, healthBenefitsMenu, aboutUsMenu, contactUsMenu, logoutMenu;
    Label contactUsLabel, logoutLabel, aboutUsLabel;
    MenuBar menuBar;
    MenuItem indoorGrowingMenuItem, outdoorGrowingMenuItem, gardenEssentialsMenuItem,
            vegetablesMenuItem, fruitsMenuItem, herbMenuItem, whyGoOrganicMenuItem,
            HealthBenefitsOrganicMenuItem, ObesityMenuItem, PesticidesMenuItem, EnvironmentMenuItem;
    BorderPane boarderPane;

    AudioClip audio = null;

    public static InformationDisplayForm informationDisplayFormInstance;
    private Stage stage;

    // constructor, pass in informationDisplayForm instance to static
    // variable instance, so that getInstance() method below can be retrieved.
    public InformationDisplayForm() {
        informationDisplayFormInstance = this;
        audio = AudioPlayUtil.getAudioClip();
    }

    // This is used for other classes to get an instance of InformationDisplayForm
    // Once other classes get a handle to InformationDisplayForm,
    // other classes will be able to call method in InformationDisplayForm,
    // thus providing functionality for other classes to go back to 
    // InformationDisplayForm window, e.g. calling goBackToInformationDisplayForm method
    public static InformationDisplayForm getInstance() {
        return informationDisplayFormInstance;
    }

    public void show() {
        this.stage.show();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        boarderPane = new BorderPane();
        boarderPane.setPadding(new Insets(10));

        getStartedMenu = new Menu("Get Started");
        growGuideMenu = new Menu("Grow Guide");
        healthBenefitsMenu = new Menu("Health Benefits");
        aboutUsMenu = new Menu(); // keep clear: aboutUs//
        contactUsMenu = new Menu();// keep clear: Contact Us

        //Abous linking
        aboutUsLabel = new Label("About Us");
        final Stage aboutUsStage = new Stage();
        // aboutUsLabel.setOnMouseClicked(new AboutUsEventHandler(aboutUsStage));
        // Create a Generic MenuHanlder to handle about us rather than creating
        // dedicated AboutUsEventHandler. the above is the previous not generic
        // approach, contactUsLabel uses the generic menu handler as well.
        aboutUsLabel.setOnMouseClicked(new MenuHandler(GrowOrganicAbout.class, aboutUsStage, stage));
        aboutUsMenu.setGraphic(aboutUsLabel);

        //Contactus linking
        contactUsLabel = new Label("Contact Us");
        final Stage contactUsStage = new Stage();
        //ContactUsLabel.setOnMouseClicked(new MenuHandler(contactUsStage));
        contactUsLabel.setOnMouseClicked(new MenuHandler(ContactingUs.class, contactUsStage, stage));
        contactUsMenu.setGraphic(contactUsLabel);

        // Code for Logout MenuItem
        logoutLabel = new Label("Logout");
        logoutMenu = new Menu();
        logoutMenu.setGraphic(logoutLabel);
        LoginFormProtoType.goBackToLogInFormProtoType(logoutLabel, stage);
        // Code for Logout MenuItem

        //Menubar Dropdown Contents
        indoorGrowingMenuItem = new MenuItem("Indoor Growing");
        outdoorGrowingMenuItem = new MenuItem("Outdoor Growing");
        gardenEssentialsMenuItem = new MenuItem("Gardening Essentials");
        getStartedMenu.getItems().addAll(indoorGrowingMenuItem, outdoorGrowingMenuItem, gardenEssentialsMenuItem);

        vegetablesMenuItem = new MenuItem("Vegetables");
        fruitsMenuItem = new MenuItem("Fruits");
        herbMenuItem = new MenuItem("Herbs");
        growGuideMenu.getItems().addAll(vegetablesMenuItem, fruitsMenuItem, herbMenuItem);

        whyGoOrganicMenuItem = new MenuItem("Why Go Organic?");
        HealthBenefitsOrganicMenuItem = new MenuItem("Health Benefits");
        ObesityMenuItem = new MenuItem("Obesity In Ireland");
        PesticidesMenuItem = new MenuItem("Pesticides");
        EnvironmentMenuItem = new MenuItem("Environment");
        healthBenefitsMenu.getItems().addAll(whyGoOrganicMenuItem, HealthBenefitsOrganicMenuItem, ObesityMenuItem, PesticidesMenuItem, EnvironmentMenuItem);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(getStartedMenu, growGuideMenu, healthBenefitsMenu, aboutUsMenu, contactUsMenu, logoutMenu);
        menuBar.setStyle("-fx-background-color:   #B0C4DE;");
        
        //menuBar DropDown Contents and its related actions
        // Use Generic to minimise duplicated of code.
        clickMenuItem(whyGoOrganicMenuItem, WhyGoOrganic.class, stage);
        clickMenuItem(HealthBenefitsOrganicMenuItem, HealthBenefitsOrganic.class, stage);
        clickMenuItem(ObesityMenuItem, Obesity.class, stage);
        clickMenuItem(PesticidesMenuItem, Pesticides.class, stage);
        clickMenuItem(EnvironmentMenuItem, Environment.class, stage);
        clickMenuItem(vegetablesMenuItem, Vegetables.class, stage);
        clickMenuItem(fruitsMenuItem, Fruits.class, stage);
        clickMenuItem(herbMenuItem, Herbs.class, stage);
        clickMenuItem(indoorGrowingMenuItem, IndoorGrowing.class, stage);
        clickMenuItem(outdoorGrowingMenuItem, OutdoorGrowing.class, stage);
        clickMenuItem(gardenEssentialsMenuItem, GardeningEssentials.class, stage);

        // Welcome Image
        Image welcomeImage = new Image(InformationDisplayForm.class.getResourceAsStream("../images/welcome.jpg"), 640, 360, false, false);
        ImageView imageView = new ImageView();
        imageView.setImage(welcomeImage);
        imageView.setPreserveRatio(true);

        boarderPane.setTop(menuBar);
        boarderPane.setCenter(imageView);

        //Adding Css file for Dashboard GUI
        boarderPane.getStylesheets().
                add(InformationDisplayForm.class.getResource("../css/LoginProtoType.css").toExternalForm());

        Scene scene = new Scene(boarderPane, 840, 555);
        stage.setScene(scene);
        stage.show();

        audio.stop();
    }

    /**
     *
     * @param goBackMenuInstance a Menu instance
     * @param currentStage the stage the current window when event has been
     * triggered by goBackMenuInstance
     */
    public static void goBackToInformationDisplayForm(Menu goBackMenuInstance, Stage currentStage, WebView webView) {
        // Hack: Menu has a constructor Menu(String text, Node graphic). 
        // Set the String to empty-string and the Node to Label. Then add a MouseListener to the Label.
        // From https://stackoverflow.com/questions/48017645/event-handler-in-javafx-for-menu?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        Label goBackLabelInstance = new Label("Go Back");
        goBackMenuInstance.setGraphic(goBackLabelInstance);
        goBackLabelInstance.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // fix: stop webEngine(e.g. youtube webview) from playing even after closing stage
                // some windows have webView, some does not, where null can be passed
                if (webView != null) {
                    webView.getEngine().load("");
                }
                currentStage.hide();
                try {
                    InformationDisplayForm.getInstance().show();
                } catch (Exception e) {
                    System.out.println("Can't Go Back, Reason: " + e.getMessage());
                }
            }
        });
    }

    /**
     * This will close the current window and initialise, open a new window
     * based on what user clicks on MenuItems
     *
     * @param <T>
     * @param menuItem
     * @param T
     * @param currentWindowStage refers to InformationDisplayForm window
     */
    private <T extends Application> void clickMenuItem(MenuItem menuItem, Class<T> T, Stage currentWindowStage) {
        menuItem.setOnAction(e -> {
            currentWindowStage.close();
            openInNewWindow(T);
        });
    }

    /**
     * This will initialise and open a new window when user presses MenuItems
     *
     * @param <T> a new window T which extends Application, for example,
     * Contactingus.java, Fruits.java
     * @param T a generic new window type which extends Application
     */
    private <T extends Application> void openInNewWindow(Class<T> T) {
        final Stage stage = new Stage();
        T newWindow;
        try {
            newWindow = T.newInstance();
            newWindow.start(stage);
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("Creating an instance of type " + T.getSimpleName() + "Failed: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Show " + T.getSimpleName() + " Screen Failed: error: " + ex.toString());
        }
    }

}
