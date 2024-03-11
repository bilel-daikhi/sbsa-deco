package org.sbsa.deco;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.utils.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@SpringBootApplication
public class SbsaDecoMain extends Application implements Initializable {

    public static Boolean isSplashLoaded = false;
    protected ConfigurableApplicationContext springContext;
    protected StageManager stageManager;
    Locale locale = new Locale("fr", "FR");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/views/splash.fxml"));
        fXMLLoader.setResources(ResourceBundle.getBundle("Bundle", locale, new CustomResourceBundleControl("UTF-8")));
        final Parent root = fXMLLoader.load();
      /*  try {
            root = fXMLLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Stage stageSplash = new Stage();
        Scene scene = new Scene(root);
        stageSplash.initStyle(StageStyle.UNDECORATED);
        stageSplash.setResizable(false);
        stageSplash.getIcons().add(new Image(getClass().getResourceAsStream("/images/small-logo.png")));
        stageSplash.centerOnScreen();
        stageSplash.setScene(scene);
        stageSplash.setTitle(PropertiesCache.getInstance().getProperty("app.name"));

        stageSplash.show();

        final ApplicationContextLoader applicationContextLoader = new ApplicationContextLoader(primaryStage);
        applicationContextLoader.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                springContext.publishEvent(new StageReadyEvent(primaryStage));

                this.hide(stageSplash, root);
                stageManager = springContext.getBean(StageManager.class, primaryStage);

                displayInitialScene();
            }
        });
        new Thread(applicationContextLoader).start();
    }

    protected void displayInitialScene() {
        stageManager.switchScene(FxmlView.main);
    /*   if (userService.adminCompteExist()) {
           log.info("show login");
            stageManager.switchScene(FxmlView.login);
        }
        else{
            log.info("show inscriptionEtAuthentification");
           stageManager.switchScene(FxmlView.inscriptionEtAuthentification);

        }*/

    }


    public void hide(Stage stage, Parent parent) {
        stage.toFront();
        FadeTransition fadeSplash = new FadeTransition(Duration.seconds(0.3), parent);
        fadeSplash.setFromValue(1.0);
        fadeSplash.setToValue(0.0);
        fadeSplash.setOnFinished(actionEvent -> stage.hide());
        fadeSplash.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    class ApplicationContextLoader extends Task<Void> {

        private final Stage primaryStage;

        ApplicationContextLoader(Stage primaryStage) {
            this.primaryStage = primaryStage;
        }

        @Override
        protected Void call() {
            ApplicationContextInitializer<GenericApplicationContext> initializer =
                    context -> {
                        context.registerBean(Application.class, () -> SbsaDecoMain.this);
                        context.registerBean(Stage.class, () -> primaryStage);
                        context.registerBean(Parameters.class,
                                SbsaDecoMain.this::getParameters); // for demonstration, not really needed
                    };
            SbsaDecoMain.this.springContext = new SpringApplicationBuilder()
                    .sources(SbsaDecoMain.class)
                    .initializers(initializer)
                    .run(getParameters().getRaw().toArray(new String[0]));

            return null;
        }
    }
}

