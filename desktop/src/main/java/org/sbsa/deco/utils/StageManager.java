package org.sbsa.deco.utils;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.controllers.MainController;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

import static org.slf4j.LoggerFactory.getLogger;

@Slf4j
public class StageManager {

    private static final Logger LOG = getLogger(StageManager.class);

    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;


    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;


        this.primaryStage = stage;
    }

    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());

        show(viewRootNodeHierarchy, view.getTitle());
    }

    public void addNewScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        showExtraScene(viewRootNodeHierarchy, view.getTitle());
        //  return viewRootNodeHierarchy;
    }

    public void showFXMLWindows(AnchorPane rootContainer, String FXMLName, String title) {
        rootContainer.getChildren().clear();
        try {
            //FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/views/" + FXMLName + ".fxml"));
            //Locale locale = new Locale("fr", "FR");
            // fXMLLoader.setResources(ResourceBundle.getBundle("Bundle", locale, new CustomResourceBundleControl("UTF-8")));
            // Parent root = fXMLLoader.load();
            log.info("load FXMLName: " + FXMLName);
            Parent root = springFXMLLoader.load("/views/" + FXMLName + ".fxml");
            //  Parent root = FXMLLoader.load(getClass().getResource("/views/" + FXMLName + ".fxml"));
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            rootContainer.getChildren().setAll(root);
            primaryStage.setTitle(title);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void newMethode(Image image, String title) {
        Stage stage = new Stage();
        double widthImage = image.getWidth();
        double heightImage = image.getHeight();

        final ImageView imageView = new ImageView(image);
        double widthImageView = imageView.getFitWidth();
        double heightImageView = imageView.getFitHeight();

        if (widthImage > 1000 || heightImage > 600) {
            imageView.setFitHeight(heightImage / 2);
            imageView.setFitWidth(widthImage / 2);

            final BorderPane borderPane = new BorderPane();
            borderPane.setPrefSize(widthImageView, heightImageView);
            borderPane.setCenter(imageView);

            final ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setContent(borderPane);
            scrollPane.setStyle("-fx-background-color: white");
            scrollPane.getStylesheets().add(Resources.LIGHT_THEME);
            scrollPane.getStyleClass().add("scroll-bar");

            stage.setScene(new Scene(scrollPane, 1000, 600));
        } else {
            imageView.setFitHeight(heightImage);
            imageView.setFitWidth(widthImage);

            final BorderPane borderPane = new BorderPane();
            borderPane.setCenter(imageView);
            borderPane.setStyle("-fx-background-color: white");
            borderPane.setPrefSize(widthImage, heightImage);

            stage.setScene(new Scene(borderPane, widthImage, heightImage));
        }
        stage.setTitle(title);
        stage.getIcons().add(new Image(Resources.SOURCE_PACKAGES + "/images/sbsa-icon.png"));
        stage.show();

    }

    public Parent addChild(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        //showExtraScene(viewRootNodeHierarchy, view.getTitle());
        return viewRootNodeHierarchy;
    }

    private void showExtraScene(final Parent rootnode, String title) {
        Scene scene = new Scene(rootnode);
        Stage stage = new Stage();
        //scene.getStylesheets().add("/styles/Styles.css");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/small-logo.png")));
        stage.centerOnScreen();
        //stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.toFront();
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        try {
            stage.show();
        } catch (Exception exception) {
            logAndExit("Unable to show scene for title" + title, exception);
        }
    }

    private void show(final Parent rootnode, String title) {
        Scene scene = prepareScene(rootnode);
        //scene.getStylesheets().add("/styles/Styles.css");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/small-logo.png")));
        primaryStage.centerOnScreen();
        primaryStage.setResizable(true);
        //  primaryStage.initStyle(StageStyle.DECORATED);
//        if(title.equals(FxmlView.logging.getTitle()))
//            primaryStage.setResizable(false);


        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit("Unable to show scene for title" + title, exception);
        }
    }

    private Scene prepareScene(Parent rootnode) {
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;

        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);

            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }


    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
