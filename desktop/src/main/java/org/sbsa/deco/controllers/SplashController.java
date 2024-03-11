package org.sbsa.deco.controllers;


import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.sbsa.deco.SbsaDecoMain;
import org.sbsa.deco.utils.CallBackSplashScreen;
import org.sbsa.deco.utils.PropertiesCache;
import org.sbsa.deco.utils.StageManager;
import org.sbsa.deco.utils.Transition;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class SplashController implements CallBackSplashScreen {
    final Locale locale = new Locale("fr", "FR");
    public Circle c1;
    public Circle c2;
    public Circle c3;
    public AnchorPane apeSplash;
    public Label idVersionLbl;
    int rotate = 0;
    CallBackSplashScreen callBackSplashScreen;
    FadeTransition fadeOut = null;
    FadeTransition fadeIn = null;
    // @Lazy
    //@Autowired
    private StageManager stageManager;

    public void initialize() {
        idVersionLbl.setText("Version " + PropertiesCache.getInstance().getProperty("app.version"));
        callBackSplashScreen = this;
        if (!SbsaDecoMain.isSplashLoaded) {
            loadSplashScreen();
        }

        /*FadeTransition fadeIn = new FadeTransition(Duration.millis(2000));
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
*/
        setRotate(c1, true, 360, 10);
        setRotate(c2, true, 180, 18);
        setRotate(c3, true, 145, 24);
    }


    public void setRotate(Circle c, boolean reverse, int angle, int duration) {

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), c);

        rotateTransition.setAutoReverse(reverse);
        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(3);
        rotateTransition.setCycleCount(18);
        rotateTransition.play();

    }

    @FXML
    void imageOnMouseEntered(MouseEvent event) {
        Transition.imageOnMouseEntered(event);
    }

    @FXML
    void imageOnMouseExited(MouseEvent event) {
        Transition.OnMOuseExited(event);
    }

    private void loadSplashScreen() {

        SbsaDecoMain.isSplashLoaded = true;

          /*  AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/splash.fxml"));
            root.getChildren().setAll(pane);
*/
        fadeIn = new FadeTransition(Duration.seconds(3), apeSplash);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        fadeOut = new FadeTransition(Duration.seconds(1), apeSplash);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        fadeIn.play();

        fadeIn.setOnFinished((e) -> {

            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
//                    new InitializeDatabase();
                    callBackSplashScreen.callback();

                }
            });
            t.start();

        });

        fadeOut.setOnFinished((e) -> {
            // stageManager.switchScene(FxmlView.login);
            //loginWindow();
           /* if (userService.checkIfUserExists() == false) {
                startWindow();
            } else {
                stageManager.switchScene(FxmlView.login);
                //loginWindow();
            }*/
            // Stage stage = (Stage) apeSplash.getScene().getWindow();
            //stage.close();
        });


    }


    @Override
    public void callback() {
        if (fadeOut != null) {
            fadeOut.play();
        }
    }


   /*  public Stage loadDashbord() throws IOException {
      //  stageManager.switchScene(FxmlView.dashBoard);
        /* URL resource = this.getClass().getResource("/fxml/dashBoard.fxml");
         Parent root = FXMLLoader.load(resource);
         Scene scene = new Scene(root);
         Stage primaryStage = (Stage)(this.apeSplash.getScene().getWindow());
         primaryStage.setScene(scene);
         primaryStage.centerOnScreen();

         return stageManager.getPrimaryStage();

     }*/


    private void loginWindow() {


        Stage stage = new Stage();
        try {

            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/views/login.fxml"));

            fXMLLoader.setResources(ResourceBundle.getBundle("Bundle", locale));
            Parent root = fXMLLoader.load();

            //Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setTitle(PropertiesCache.getInstance().getProperty("app.name"));
            stage.getIcons().add(new Image("/images/sbsa-icon.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SbsaDecoMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startWindow() {
        Stage stage = new Stage();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/views/start.fxml"));

            fXMLLoader.setResources(ResourceBundle.getBundle("Bundle", locale));
            Parent root = fXMLLoader.load();

            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setTitle(PropertiesCache.getInstance().getProperty("app.name"));
            stage.getIcons().add(new Image("/images/sbsa-icon.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SbsaDecoMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}

