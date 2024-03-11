package org.sbsa.deco.controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.sbsa.deco.controllers.tv.ItemTV;
import org.sbsa.deco.service.CategoryService;
import org.sbsa.deco.service.ItemService;
import org.sbsa.deco.service.SubCategoriesService;
import org.sbsa.deco.utils.PropertiesCache;
import org.sbsa.deco.utils.Resources;
import org.sbsa.deco.utils.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class HomeController implements Initializable {

    private ObservableList<ItemTV> listItems;

    private ObservableList<ItemTV> filterItems;

    @FXML
    private StackPane stckHome;

    @FXML
    private AnchorPane rootSearchMain;

    @FXML
    private AnchorPane rootHome;

    @FXML
    private TextField txtSearchRecentCustomer;

    @FXML
    private AnchorPane rootWelcome;

    @FXML
    private Text textDescriptionWelcome;

    @FXML
    private Text textWelcome;

    @FXML
    private Text textItems;

    @FXML
    private Text txtDiscounts;

    @FXML
    private Text textTotalSubCategories;

    @FXML
    private Text textTotaLCategories;

    @FXML
    private Label labelTotalItems;

    @FXML
    private Label labelTotalDiscounts;

    @FXML
    private Label labelTotalCategories;

    @FXML
    private Label labelTotalSubCategories;

    @FXML
    private TableView<ItemTV> tblItems;

    @FXML
    private AnchorPane rootTotalCustomers;

    @FXML
    private AnchorPane rootTotalDiscounts;

    @FXML
    private AnchorPane rootAllCategories;

    @FXML
    private AnchorPane rootTotalSubCategories;

    @FXML
    private TableColumn<ItemTV, Integer> colId;

    @FXML
    private TableColumn<ItemTV, String> colName;

    @FXML
    private TableColumn<ItemTV, String> colPrice;

    @FXML
    private TableColumn<ItemTV, String> colCreated;

    @FXML
    private TableColumn<ItemTV, String> colDiscountAmount;
    @FXML
    private TableColumn<ItemTV, String> colCategory;

    @FXML
    private TableColumn<ItemTV, JFXButton> colDiscount;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoriesService subCategoriesService;
    @Autowired
    private ItemService itemService;


    @Lazy
    @Autowired
    private StageManager stageManager;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        animationsNodes();
        setWelcomeText();

        itemsCounter();
        categoriesCounter();
        subCategoriesCounter();
        discountsCounter();

        loadData();
        setFonts();
        selectText();
        filterItems = FXCollections.observableArrayList();
    }

    private void selectText() {
        Resources.selectTextToTextField(txtSearchRecentCustomer);
    }

    private void setFonts() {
        Resources.setFontToText(textWelcome, 25);
        Resources.setFontToText(textDescriptionWelcome, 15);
        Resources.setFontToText(textItems, 15);
        Resources.setFontToText(txtDiscounts, 15);
        Resources.setFontToText(textTotalSubCategories, 15);
        Resources.setFontToText(textTotaLCategories, 15);
    }

    private void animationsNodes() {
        Resources.fadeInUpAnimation(rootSearchMain);
        Resources.fadeInUpAnimation(rootWelcome);
        Resources.fadeInUpAnimation(tblItems);
        Resources.fadeInUpAnimation(rootTotalCustomers);
        Resources.fadeInUpAnimation(rootTotalDiscounts);
        Resources.fadeInUpAnimation(rootAllCategories);
        Resources.fadeInUpAnimation(rootTotalSubCategories);
    }

    private void itemsCounter() {

        long total = itemService.count();

        labelTotalItems.setText(String.valueOf(total));

    }

    private void subCategoriesCounter() {

        long total = subCategoriesService.count();

        labelTotalSubCategories.setText(String.valueOf(total));


    }

    private void categoriesCounter() {

        long total = categoryService.count();

        labelTotalCategories.setText(String.valueOf(total));


    }

    private void discountsCounter() {

        long discountCounter = itemService.getAllDiscountItems(true).size();
        labelTotalDiscounts.setText(String.valueOf(discountCounter));


    }

    private void setWelcomeText() {
        try {

            textWelcome.setText(PropertiesCache.getInstance().getProperty("bienvenu") + " !");
            textDescriptionWelcome.setText(PropertiesCache.getInstance().getProperty("message.suggestion.item"));

        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setText(String name, int total) {
        textWelcome.setText(" Felicitations " + name + ", " + total + " de nouveaux devis ont ete enregistres!");
        textDescriptionWelcome.setText(PropertiesCache.getInstance().getProperty("bien"));
    }

    private void loadData() {
        loadAllDiscounts();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCreated.setCellValueFactory(new PropertyValueFactory<>("created"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDiscountAmount.setCellValueFactory(new PropertyValueFactory<>("discountAmount"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDiscount.setCellValueFactory(new JFXButtonExistsCellValueFactory());
    }

    private void loadAllDiscounts() {
        List<ItemTV> discountItems = itemService.getAllDiscountItems(true);
        listItems = FXCollections.observableArrayList(discountItems);
        tblItems.setItems(listItems);


    }

    @FXML
    private void filterCategory() {
        String filter = txtSearchRecentCustomer.getText();
        if (filter.isEmpty()) {
            tblItems.setItems(listItems);
        } else {
            filterItems.clear();
            for (ItemTV q : listItems) {
                if (q.getSubCategory().toLowerCase().contains(filter.toLowerCase())) {
                    filterItems.add(q);
                }
            }
            tblItems.setItems(filterItems);
        }
    }

    private class JFXButtonExistsCellValueFactory implements Callback<TableColumn.CellDataFeatures<ItemTV, JFXButton>, ObservableValue<JFXButton>> {

        @Override
        public ObservableValue<JFXButton> call(TableColumn.CellDataFeatures<ItemTV, JFXButton> param) {
            ItemTV item = param.getValue();

            FontAwesomeIconView icon = new FontAwesomeIconView();
            icon.setFill(Color.WHITE);

            JFXButton button = new JFXButton();
            button.setGraphic(icon);
            button.setText(item.isDiscount() ? "YES" : "NO");
            button.getStylesheets().add(Resources.LIGHT_THEME);
            button.setPrefWidth(colDiscount.getWidth() / 0.5);

            if (item.isDiscount()) {
                icon.setGlyphName(String.valueOf(FontAwesomeIcon.CHECK));
                button.getStyleClass().addAll("cell-button-exists", "table-row-cell");
            } else {
                icon.setGlyphName(String.valueOf(FontAwesomeIcon.CLOSE));
                button.getStyleClass().addAll("cell-button-not-exists", "table-row-cell");
            }
            return new SimpleObjectProperty<>(button);
        }
    }
// 
}
