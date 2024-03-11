package org.sbsa.deco.controllers;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.sbsa.deco.service.CategoryService;
import org.sbsa.deco.service.ItemService;
import org.sbsa.deco.utils.PropertiesCache;
import org.sbsa.deco.utils.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class StatisticsController implements Initializable {

    private final DatePicker datepicker = new DatePicker();
    private final JFXPopup popup = new JFXPopup();
    private final ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootStatistics;
    @FXML
    private JFXDatePicker dpSelectedDate;
    @FXML
    private AnchorPane rootDate;
    @FXML
    private PieChart pieChart;
    @FXML
    private Text title;
    @FXML
    private HBox hbox;
    @FXML
    private HBox hboxImage;
    @FXML
    private ImageView emptyImage;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService productService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setFont();
        setGraphics();
//        setDatePicker();
        setPopup();
        setNodeStartupConfiguration();
        setAnimations();
    }

    private void setNodeStartupConfiguration() {
//        popupContent.getStyleClass().addAll("date-picker");
        hboxImage.setVisible(true);
        hbox.setVisible(false);
        pieChart.setLegendVisible(false);
    }

    private void setAnimations() {
        Resources.fadeInUpAnimation(hboxImage);
        Resources.fadeInUpAnimation(title);
    }
//
//    private void setDatePicker() {
//        popupContent.setVisible(false);
//        rootDate.getChildren().add(popupContent);
//    }

    private void setPopup() {
        popup.setPopupContent(rootDate);

        pieChart.setOnMouseClicked(ev -> {
            if (ev.getButton() == MouseButton.SECONDARY) {
//                popupContent.setVisible(true);
                popup.show(pieChart);
                popup.setAnchorX(ev.getScreenX());
                popup.setAnchorY(ev.getScreenY());
            }
        });

        hboxImage.setOnMouseClicked(ev -> {
            if (ev.getButton() == MouseButton.SECONDARY) {
//                popupContent.setVisible(true);
                popup.show(hboxImage);
                popup.setAnchorX(ev.getScreenX());
                popup.setAnchorY(ev.getScreenY());
            }
        });

        emptyImage.setOnMousePressed(ev -> {
//                popupContent.setVisible(true);
            popup.show(emptyImage);
            popup.setAnchorX(ev.getScreenX());
            popup.setAnchorY(ev.getScreenY());

        });

        rootStatistics.setOnMouseClicked(ev -> {
            if (ev.getButton() == MouseButton.SECONDARY) {
//                popupContent.setVisible(true);
                popup.show(rootStatistics);
                popup.setAnchorX(ev.getScreenX());
                popup.setAnchorY(ev.getScreenY());
            }
        });
        dpSelectedDate.setOnMouseEntered(ev -> {

//                popupContent.setVisible(true);
            popup.show(rootStatistics);
            popup.setAnchorX(ev.getScreenX());
            popup.setAnchorY(ev.getScreenY());

        });
    }

    private void setFont() {
        Resources.setFontToText(title, 35);
    }

    @FXML
    private void setGraphics() {
        datepicker.setOnAction(ev -> {
            dpSelectedDate.setValue(datepicker.getValue());
            popup.hide();

            pieChart.getData().clear();

            java.sql.Date date = java.sql.Date.valueOf(datepicker.getValue());

            long count = 10;//customerService.getCustomers(date);
            long count2 = 20;//devisService.getDevisByDate(date);
            long count3 = 30;//productService.getProducts(date);

            if (count == 0 && count2 == 0 && count3 == 0) {
                hboxImage.setVisible(true);
                hbox.setVisible(false);
                new FadeIn(hboxImage).play();
            } else {
                hboxImage.setVisible(false);
                hbox.setVisible(true);

                PieChart.Data one = new PieChart.Data(PropertiesCache.getInstance().getProperty("text.total.client") + count, count);
                data.add(one);
                pieChart.setData(data);
                Resources.hoverAnimation(one.getNode(), 50, 1.1);

                PieChart.Data two = new PieChart.Data(PropertiesCache.getInstance().getProperty("text.total.rents") + count2, count2);
                data.add(two);
                pieChart.setData(data);
                Resources.hoverAnimation(two.getNode(), 50, 1.1);

                PieChart.Data Three = new PieChart.Data(PropertiesCache.getInstance().getProperty("text.total.car") + count3, count3);
                data.add(Three);
                pieChart.setData(data);
                Resources.hoverAnimation(Three.getNode(), 50, 1.1);
            }
            pieChart.setData(data);
        });
    }


}
