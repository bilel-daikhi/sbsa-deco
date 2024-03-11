package org.sbsa.deco.controllers;


import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.sbsa.deco.utils.PropertiesCache;
import org.sbsa.deco.utils.Resources;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class AboutController implements Initializable {


    private static final String FACEBOOK = "https://www.facebook.com/bilel.daikhi.3";
    private static final String VERSION = "Version " + PropertiesCache.getInstance().getProperty("app.version");
    private static final String WEBSITE = "https://sbsa.business.site";
    private static final String GMAIL = "mailto:" + PropertiesCache.getInstance().getProperty("sbsa.email.address");


    @FXML
    private ImageView sbsabiglogo;

    @FXML
    private Text developer;
    @FXML
    private Text telephone;
    @FXML
    private Text version;
    @FXML
    private ImageView sbsalogo;


    @FXML
    private MaterialDesignIconView facebook;
    @FXML
    private MaterialDesignIconView website;
    @FXML
    private MaterialDesignIconView google;


    @FXML
    private ImageView idreicon;

    @FXML
    private Separator separator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        version.setText(VERSION);
        setAnimations();
        setURL();
    }

    private void setURL() {

        Resources.goToUrl(FACEBOOK, facebook);
        Resources.goToUrl(GMAIL, google);
        Resources.goToUrl(WEBSITE, website);

    }

    private void setAnimations() {
        transition(sbsabiglogo, 0);
        transition(idreicon, 1);
        transition(version, 2);
        transition(developer, 3);
        transition(telephone, 4);
        transition(sbsalogo, 5);
        transition(separator, 6);
        transition(facebook, 7);
        transition(google, 8);
        transition(website, 9);


    }

    private void transition(Node node, int duration) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), node);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), node);
        fadeTransition.setFromValue(2);
        fadeTransition.setToValue(0.5);

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(duration));
        pauseTransition.setOnFinished(ev -> {
            PauseTransition pauseTransition2 = new PauseTransition();
            pauseTransition2.setDuration(Duration.seconds(0.1));
            pauseTransition2.setOnFinished(ev2 -> {
                node.setVisible(true);
            });

            pauseTransition2.play();
            Resources.fadeInUpAnimation(node);
            fadeTransition.play();
        });

        pauseTransition.play();

        node.setOnMouseEntered(ev -> {
            fadeTransition.setToValue(1);
            fadeTransition.playFromStart();

            scaleTransition.setRate(1.0);
            scaleTransition.play();
        });

        node.setOnMouseExited(ev -> {
            fadeTransition.setDuration(Duration.millis(100));
            fadeTransition.setToValue(0.5);
            fadeTransition.playFromStart();

            scaleTransition.setRate(-1.0);
            scaleTransition.play();
        });
    }
}
