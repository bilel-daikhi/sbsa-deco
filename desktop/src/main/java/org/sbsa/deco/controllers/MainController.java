package org.sbsa.deco.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.sbsa.deco.utils.FxmlView;
import org.sbsa.deco.utils.Resources;
import org.sbsa.deco.utils.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MainController implements Initializable {

    @FXML
    public AnchorPane tooltipHome;
    @FXML
    private StackPane stckMain;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnCategories;
    @FXML
    private JFXButton btnSubCategories;

    @FXML
    private JFXButton btnExit;
    @FXML
    private JFXButton btnAbout;
    @FXML
    private JFXButton btnStatistics;


    @FXML
    private JFXButton btnItems;
    @FXML
    private AnchorPane rootContainer;
    @FXML
    private AnchorPane rootSideMenu;
    @FXML
    private AnchorPane tooltipCategories;
    @FXML
    private AnchorPane tooltipSubCategories;


    @FXML
    private AnchorPane tooltipExit;

    @FXML
    private AnchorPane tooltipItems;


    @FXML
    private AnchorPane tooltipAbout;

    @FXML
    private AnchorPane tooltipStatistics;


    @Lazy
    @Autowired
    private StageManager stageManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        homeWindowsInitialize();
        tooltips();
    }

    @FXML
    private void setDisableButtons(MouseEvent event) {
        setDisableButtons(event, btnHome);
        setDisableButtons(event, btnCategories);
        setDisableButtons(event, btnSubCategories);

        setDisableButtons(event, btnItems);

        setDisableButtons(event, btnStatistics);
        setDisableButtons(event, btnAbout);
        setDisableButtons(event, btnExit);
    }

    private void homeWindowsInitialize() {
        btnHome.setDisable(true);
        //  showFXMLWindows("home");

        stageManager.showFXMLWindows(rootContainer, FxmlView.home.name(), FxmlView.home.getTitle());
    }

    @FXML
    private void homeWindows(MouseEvent event) {
        btnHome.setDisable(true);
        stageManager.showFXMLWindows(rootContainer, FxmlView.home.name(), FxmlView.home.getTitle());
        setDisableButtons(event);
    }

    @FXML
    private void categoriesWindows(MouseEvent event) {
        stageManager.showFXMLWindows(rootContainer, FxmlView.categories.name(), FxmlView.categories.getTitle());
        setDisableButtons(event);
    }

    @FXML
    private void subcategoriesWindows(MouseEvent event) {
        stageManager.showFXMLWindows(rootContainer, FxmlView.subCategories.name(), FxmlView.subCategories.getTitle());
        setDisableButtons(event);
    }


    @FXML
    private void statisticsWindows(MouseEvent event) {

        stageManager.showFXMLWindows(rootContainer, FxmlView.statistics.name(), FxmlView.statistics.getTitle());
        setDisableButtons(event);
    }

    @FXML
    private void aboutWindows(MouseEvent event) {

        stageManager.showFXMLWindows(rootContainer, FxmlView.about.name(), FxmlView.about.getTitle());
        setDisableButtons(event);
    }

    @FXML
    private void itemsWindows(MouseEvent event) {

        stageManager.showFXMLWindows(rootContainer, FxmlView.items.name(), FxmlView.items.getTitle());
        setDisableButtons(event);
    }


    private void closeStage() {
        ((Stage) btnHome.getScene().getWindow()).close();
    }

    private void setDisableButtons(MouseEvent event, JFXButton button) {
        button.setDisable(event.getSource() == button);
    }

    private void tooltips() {
        Resources.tooltip(btnHome, tooltipHome);
        Resources.tooltip(btnCategories, tooltipCategories);
        Resources.tooltip(btnSubCategories, tooltipSubCategories);


        Resources.tooltip(btnExit, tooltipExit);
        Resources.tooltip(btnItems, tooltipItems);
        Resources.tooltip(btnStatistics, tooltipStatistics);
        Resources.tooltip(btnAbout, tooltipAbout);

    }


    public JFXButton getBtnStatistics() {
        return btnStatistics;
    }


    public JFXButton getBtnAbout() {
        return btnAbout;
    }
}
