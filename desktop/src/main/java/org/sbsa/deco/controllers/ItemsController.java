package org.sbsa.deco.controllers;

import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOutUp;
import com.jfoenix.controls.*;
import com.sbsa.deco.dto.ItemDTO;
import com.sbsa.deco.dto.SubCategoryDTO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.controllers.tv.ItemTV;
import org.sbsa.deco.service.CategoryService;
import org.sbsa.deco.service.ItemService;
import org.sbsa.deco.service.SubCategoriesService;
import org.sbsa.deco.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ESCAPE;
import static org.sbsa.deco.utils.Resources.dialog;
import static org.sbsa.deco.utils.Resources.shakeAnimation;

@Controller
@Slf4j
public class ItemsController implements Initializable {
    private final long LIMIT = 16777215;
    private final BoxBlur blur = new BoxBlur(3, 3, 3);
    @Autowired
    private DialogTransition dialogTransition;
    private StageManager stageManager;
    @Autowired
    private ItemService itemService;
    @Autowired
    private SubCategoriesService subCategoriesService;
    @Autowired
    private CategoryService categoryService;

    private ObservableList<ItemTV> listItems;
    private ObservableList<ItemTV> filterItems;
    private ObservableList<SubCategoryDTO> categorybservableList;

    private ObservableList<SubCategoryDTO> ownersObservableList;
    @FXML
    private BorderPane imageContainer;
    @FXML
    private StackPane stckItems;
    @FXML
    private AnchorPane rootItems;
    @FXML
    private AnchorPane rootDeleteItems;
    @FXML
    private HBox hBoxSearch;
    @FXML
    private TextField txtSearchItem;
    @FXML
    private TextField txtSearchCategory;
    @FXML
    private JFXButton btnNewItem;

    @FXML
    private TableView<ItemTV> tblItems;
    @FXML
    private TableColumn<ItemTV, Integer> colId;
    @FXML
    private TableColumn<ItemTV, String> colName;
    @FXML
    private TableColumn<ItemTV, Double> colPrice;
    @FXML
    private TableColumn<ItemTV, String> colCreated;
    @FXML
    private TableColumn<ItemTV, String> colCategory;

    @FXML
    private TableColumn<ItemTV, JFXButton> colDiscount;

    @FXML
    private AnchorPane rootAddItem;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtPrice;
    @FXML
    private JFXTextField txtCreated;
    @FXML
    private Text textAddItem;
    @FXML
    private Text titleWindowDeleteItem;
    @FXML
    private Text descriptionWindowDeleteItem;
    @FXML
    private Text textPurchase;
    @FXML
    private Text textPorcentage;

    @FXML
    private JFXTextArea txtDescriptionItem;
    @FXML
    private JFXButton btnUpdateItem;
    @FXML
    private JFXButton btnSaveItem;
    @FXML
    private JFXButton btnCancelDelete;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnCancelAdd;
    @FXML
    private JFXComboBox<SubCategoryDTO> cmbCategory;

    @FXML
    private MenuItem menuEdit;
    @FXML
    private MenuItem menuDelete;
    @FXML
    private ImageView imageItem;
    @FXML
    private Pane paneContainer;
    @FXML
    private MaterialDesignIconView icon;
    private JFXDialog dialogAddItem;
    private JFXDialog dialogDeleteItem;
    private File imageFile;

    public ItemsController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadData();
        setFonts();
        animateNodes();
        validateUser();
        selectText();
        setValidations();
        characterLimiter();
        keyEscapeWindows();
        escapeWindowWithTextFields();
        imageContainer.setPadding(new Insets(5));
        filterItems = FXCollections.observableArrayList();
        imageItem.setFitHeight(imageContainer.getPrefHeight() - 10);
        imageItem.setFitWidth(imageContainer.getPrefWidth() - 10);
    }

    private void setFonts() {
        Resources.setFontToJFXButton(btnCancelAdd, 15);
        Resources.setFontToJFXButton(btnUpdateItem, 15);
        Resources.setFontToJFXButton(btnCancelDelete, 15);
        Resources.setFontToJFXButton(btnSaveItem, 15);

        Resources.setFontToJFXButton(btnNewItem, 12);
        Resources.setFontToJFXButton(btnDelete, 15);

        Resources.setFontToText(descriptionWindowDeleteItem, 12);
        Resources.setFontToText(titleWindowDeleteItem, 15);
        Resources.setFontToText(textAddItem, 20);
        Resources.setFontToText(textPorcentage, 13);
        Resources.setFontToText(textPurchase, 13);
    }

    private void animateNodes() {
        Resources.fadeInUpAnimation(btnNewItem);
        Resources.fadeInUpAnimation(tblItems);
        Resources.fadeInUpAnimation(hBoxSearch);
    }

    private void setValidations() {
        emptyText();

        Resources.validationOfJFXTextArea(txtDescriptionItem);

//        Resources.doubleNumbersValidationTextField(cmbOwner);


//        Resources.validationOfJFXTextField(cmbOwner);
        Resources.validationOfJFXTextField(txtPrice);
//        Resources.validationOfJFXTextField(cmbCategory);

        Resources.validationOfJFXTextField(txtName);
    }

    private void selectText() {
        Resources.selectTextToJFXTextArea(txtDescriptionItem);
//        Resources.selectTextToJFXTextField(cmbOwner);
        Resources.selectTextToJFXTextField(txtPrice);
//        Resources.selectTextToJFXTextField(cmbCategory);
        Resources.selectTextToJFXTextField(txtName);
    }

    @FXML
    private void showWindowAddItem() {
        resetValidation();
        enableControlsEdit();
        rootItems.setEffect(blur);
        disableTable();

        textAddItem.setText(PropertiesCache.getInstance().getProperty("add.item"));
        imageContainer.toFront();
        icon.setVisible(false);
        rootAddItem.setVisible(true);
        btnSaveItem.setVisible(true);

        btnSaveItem.setDisable(false);
        btnUpdateItem.setVisible(false);
        btnSaveItem.toFront();

        dialogAddItem = new JFXDialog();
        dialogAddItem.setTransitionType(dialogTransition.dialogTransition());
        dialogAddItem.setBackground(Background.EMPTY);
        dialogAddItem.setDialogContainer(stckItems);
        dialogAddItem.setContent(rootAddItem);
        Resources.setStyleToAlerts(dialogAddItem);
        dialogAddItem.show();

        dialogAddItem.setOnDialogOpened(ev -> {
            txtName.requestFocus();
        });

        dialogAddItem.setOnDialogClosed(ev -> {
            tblItems.setDisable(false);
            rootItems.setEffect(null);
            rootAddItem.setVisible(false);
            cleanControls();

          /*  if (stage != null) {
                stage.hide();
            }*/
        });
    }

    @FXML
    private void hideWindowAddItem() {
        dialogAddItem.close();
    }

    @FXML
    private void showWindowDeleteItem() {
        if (tblItems.getSelectionModel().getSelectedItems().isEmpty()) {
            Resources.showErrorAlert(stckItems, rootItems, tblItems, PropertiesCache.getInstance().getProperty("selection.tableau"));
        } else {
            rootItems.setEffect(blur);
            disableTable();
            dialogDeleteItem = new JFXDialog();
            dialogDeleteItem.setTransitionType(dialogTransition.dialogTransition());
            dialogDeleteItem.setBackground(Background.EMPTY);
            dialogDeleteItem.setDialogContainer(stckItems);
            dialogDeleteItem.setContent(rootDeleteItems);
            Resources.setStyleToAlerts(dialogDeleteItem);
            rootDeleteItems.setVisible(true);
            dialogDeleteItem.show();

            dialogDeleteItem.setOnDialogClosed(ev -> {
                tblItems.setDisable(false);
                rootItems.setEffect(null);
                rootDeleteItems.setVisible(false);
                cleanControls();
            });
        }
    }

    @FXML
    private void hideWindowDeleteItem() {
        if (dialogDeleteItem != null) {
            dialogDeleteItem.close();
        }
    }

    @FXML
    private void showWindowUptadeItem() {
        if (tblItems.getSelectionModel().getSelectedItems().isEmpty()) {
            Resources.showErrorAlert(stckItems, rootItems, tblItems, PropertiesCache.getInstance().getProperty("selection.tableau"));
        } else {
            showWindowAddItem();
            textAddItem.setText(PropertiesCache.getInstance().getProperty("update.item"));
            btnUpdateItem.setVisible(true);

            btnSaveItem.setVisible(false);
            btnUpdateItem.toFront();

            selectedRecord();
        }
    }

    @FXML
    private void showWindowDetailsItem() {
        if (tblItems.getSelectionModel().getSelectedItems().isEmpty()) {
            Resources.showErrorAlert(stckItems, rootItems, tblItems, PropertiesCache.getInstance().getProperty("selection.tableau"));
        } else {
            showWindowAddItem();
            textAddItem.setText(PropertiesCache.getInstance().getProperty("details.item"));
            paneContainer.toFront();
            icon.setVisible(true);
            btnUpdateItem.setVisible(false);
            btnSaveItem.setDisable(true);

            btnSaveItem.toFront();
            disableControlsEdit();
            selectedRecord();
        }
    }

    @FXML
    private void loadData() {
        loadTable();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCreated.setCellValueFactory(new PropertyValueFactory<>("created"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("subCategory"));

        colDiscount.setCellValueFactory(new JFXButtonExistsCellValueFactory());



        loadSubCategories();
    }

    private void loadTable() {
        List<ItemTV> list = new ArrayList<>();
        try {
            list = itemService.findAll().toList();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            Resources.showErrorAlert(stckItems, rootItems, tblItems, PropertiesCache.getInstance().getProperty("database.error")
            );
        }
        listItems = FXCollections.observableArrayList(list);
        log.info("listItems: "+ listItems);
        tblItems.setItems(listItems);

    }

    private void loadSubCategories() {
        List<SubCategoryDTO> vehicleCategoryDTOS = subCategoriesService.findAll().toList();

        categorybservableList = FXCollections.observableArrayList(vehicleCategoryDTOS);
        cmbCategory.setItems(categorybservableList);
    }



    private void selectedRecord() {
        ItemTV itemTV = tblItems.getSelectionModel().getSelectedItem();
        log.info("selected item id: "+itemTV.getId());
        ItemDTO itemDTO=itemService.findItemById(itemTV.getId());
        txtName.setText(itemDTO.getName());
        txtPrice.setText(String.valueOf(itemDTO.getPrice()));
        txtDescriptionItem.setText(itemDTO.getDescription());
        txtCreated.setText(itemDTO.getCreated());
//        cmbCategory.getSelectionModel().clearSelection();
        cmbCategory.getSelectionModel().select(itemDTO.getSubCategoryDTO());
        imageItem.setImage(getImage(itemTV.getId()));
        expandImage(itemTV.getId());
    }

    @FXML
    private void newItem() {
        if (Validators.isEmpty(txtName.getText())) {
            shakeAnimation(txtName);
        } else if (itemService.findItemsByName(txtName.getText()) != null) {
            Resources.notification(PropertiesCache.getInstance().getProperty("erreur"), PropertiesCache.getInstance().getProperty("codebare.exist"), "error.png");
        } else if (Validators.isEmpty(txtPrice.getText())) {
            shakeAnimation(txtPrice);
        } else if (Validators.isEmpty(txtCreated.getText())) {
            shakeAnimation(txtCreated);
        } else if (cmbCategory.getSelectionModel().isEmpty()) {
            shakeAnimation(cmbCategory);
        } else if (Validators.isEmpty(txtDescriptionItem.getText())) {
            shakeAnimation(txtDescriptionItem);
        } else if (imageFile != null && imageFile.length() > LIMIT) {
            shakeAnimation(imageContainer);
            Resources.notification(PropertiesCache.getInstance().getProperty("erreur"), PropertiesCache.getInstance().getProperty("picture.too.large"), "error.png");
        } else {
            SubCategoryDTO subCategoryDTO = cmbCategory.getSelectionModel().getSelectedItem();
            ItemTV itemDTO = ItemTV.builder()
                    .name(txtName.getText()).subCategoryId(subCategoryDTO.getId()).description(txtDescriptionItem.getText())
                    .build();


            boolean result = itemService.saveItem(itemDTO, listItems);
            if (result) {
                loadData();
                cleanControls();
                hideWindowAddItem();
                Resources.showSuccessAlert(stckItems, rootItems, tblItems, PropertiesCache.getInstance().getProperty("saved.message.succes"));
            } else {
                Resources.notification(PropertiesCache.getInstance().getProperty("erreur.fatal"), PropertiesCache.getInstance().getProperty("database.error"), "error.png");
            }
        }
    }

    private Image getImage(long id) {

        Image image= itemService.getImageItem(id);
        if(image==null)
            return new Image(Resources.NO_IMAGE_AVAILABLE, 200, 200, true, true);
   return image;
     }

    @FXML
    private void updateProduct() {
        long id = tblItems.getSelectionModel().getSelectedItem().getId();

        if (Validators.isEmpty(txtName.getText())) {
            shakeAnimation(txtName);
        } else if (itemService.findItemById(id) != null) {
            Resources.notification(PropertiesCache.getInstance().getProperty("erreur"), PropertiesCache.getInstance().getProperty("codebare.exist"), "error.png");
        } else if (Validators.isEmpty(txtPrice.getText())) {
            shakeAnimation(txtPrice);
        }  else if (cmbCategory.getSelectionModel().isEmpty()) {
            shakeAnimation(cmbCategory);
        } else if (Validators.isEmpty(txtDescriptionItem.getText())) {
            shakeAnimation(txtDescriptionItem);
        } else if (imageFile != null && imageFile.length() > LIMIT) {
            shakeAnimation(imageContainer);
            Resources.notification(PropertiesCache.getInstance().getProperty("erreur"), PropertiesCache.getInstance().getProperty("picture.is.too.large"), "error.png");
        } else {
            ItemTV itemDTO = tblItems.getSelectionModel().getSelectedItem();
            itemDTO.setId(itemDTO.getId());
            itemDTO.setName(txtName.getText());
            long subCategoryId = cmbCategory.getSelectionModel().getSelectedItem().getId();
            itemDTO.setSubCategoryId(subCategoryId);
            itemDTO.setDescription(txtDescriptionItem.getText());

            if (imageFile != null) {
                boolean result = itemService.updateItem(itemDTO);
                if (result) {
                    hideWindowAddItem();
                    loadData();
                    cleanControls();
                    Resources.showSuccessAlert(stckItems, rootItems, tblItems, PropertiesCache.getInstance().getProperty("enregistrement.modifie.succes"));
                } else {
                    Resources.notification(PropertiesCache.getInstance().getProperty("erreur.fatal"), PropertiesCache.getInstance().getProperty("database.error"), "error.png");
                }
            } else {
                boolean result = itemService.updateItem(itemDTO);
                if (result) {
                    hideWindowAddItem();
                    loadData();
                    cleanControls();
                    Resources.showSuccessAlert(stckItems, rootItems, tblItems, PropertiesCache.getInstance().getProperty("enregistrement.modifie.succes"));
                } else {

                    Resources.notification(PropertiesCache.getInstance().getProperty("erreur.fatal"), PropertiesCache.getInstance().getProperty("database.error"), "error.png");
                }
            }
        }
    }

    @FXML
    private void deleteProducts() {
        boolean result = itemService.deleteItem(tblItems, listItems);
        if (result) {
            loadData();
            cleanControls();
            hideWindowDeleteItem();
            Resources.showSuccessAlert(stckItems, rootItems, tblItems, PropertiesCache.getInstance().getProperty("enregistrement.modifie.succes"));
        } else {
            Resources.notification(PropertiesCache.getInstance().getProperty("erreur.fatal"), PropertiesCache.getInstance().getProperty("database.error"), "error.png");
        }
    }

    private void cleanControls() {

        txtName.clear();

        cmbCategory.getSelectionModel().clearSelection();
        txtPrice.clear();
        txtCreated.clear();
        txtDescriptionItem.clear();
        imageItem.setImage(new Image(Resources.NO_IMAGE_AVAILABLE));
        imageFile = null;
    }

    private void disableControlsEdit() {
        txtName.setEditable(false);
        txtDescriptionItem.setEditable(false);
        txtPrice.setEditable(false);
        txtCreated.setEditable(false);
        cmbCategory.setEditable(false);
    }

    private void enableControlsEdit() {
        txtName.setEditable(true);
        txtPrice.setEditable(true);
        txtCreated.setEditable(true);
        txtDescriptionItem.setEditable(true);
        cmbCategory.setEditable(true);
    }

    private void disableTable() {
        tblItems.setDisable(true);
    }

    private void emptyText() {
        txtDescriptionItem.clear();
        Resources.setTextIsEmpty(txtName);
        Resources.setTextIsEmpty(txtPrice);
        cmbCategory.getSelectionModel().clearSelection();
    }

    private void resetValidation() {
        txtName.resetValidation();
        txtPrice.resetValidation();
        txtCreated.resetValidation();
        cmbCategory.resetValidation();
        txtDescriptionItem.resetValidation();
    }

    private void validateUser() {
//        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        boolean isAdmin= currentUser.getRoles().stream().anyMatch(role -> role.getName().equals(UserType.ADMIN.name()));
        if (true) {
            colDiscount.setVisible(true);
            colCategory.setVisible(true);
            btnNewItem.setDisable(false);
            txtCreated.setVisible(true);
            cmbCategory.setVisible(true);
            textPurchase.setVisible(false);
            textPorcentage.setVisible(false);
            setEnableMenuItem();
        } else {
            setDisableMenuItem();
            colDiscount.setVisible(false);
            colCategory.setVisible(false);
            btnNewItem.setDisable(true);
            txtCreated.setVisible(false);
            textPurchase.setVisible(true);
            textPorcentage.setVisible(true);
            cmbCategory.setVisible(false);
        }
        keyDeleteItem();
    }

    private void setDisableMenuItem() {
        menuEdit.setDisable(true);
        menuDelete.setDisable(true);
    }

    private void setEnableMenuItem() {
        menuEdit.setDisable(false);
        menuDelete.setDisable(false);
    }

    @FXML
    private void onlyTextFieldName() {
        Resources.validationOnlyNumbers(txtName);
    }

    @FXML
    private void onlyTextFielSearchName() {
        Resources.validationOnlyNumbers(txtSearchCategory);
    }

    @FXML
    private void onlyTextFieldPorcentage() {
        Resources.validationOfJFXComboBox(cmbCategory);
    }

    @FXML
    private void validateAndRefreshSubCategories() {
        Resources.validationOfJFXComboBox(cmbCategory);
    }

    private void characterLimiter() {
        Resources.limitTextField(txtName, 20);
//        Resources.limitTextField(cmbCategory, 3);
    }

    private void keyEscapeWindows() {
        rootItems.setOnKeyReleased((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == ESCAPE && rootAddItem.isVisible()) {
                hideWindowAddItem();
            }
            if (keyEvent.getCode() == ESCAPE && rootDeleteItems.isVisible()) {
                hideWindowDeleteItem();
            }
            if (dialog != null) {
                if (keyEvent.getCode() == ESCAPE && dialog.isVisible()) {
                    tblItems.setDisable(false);
                    rootItems.setEffect(null);
                    dialog.close();
                }
            }
        });

    }

    private void escapeWindowWithTextFields() {
        txtName.setOnKeyReleased(ev -> {
            if (ev.getCode() == ESCAPE) {
                hideWindowAddItem();
                tblItems.setDisable(false);
            }
        });

        txtPrice.setOnKeyReleased(ev -> {
            if (ev.getCode() == ESCAPE) {
                hideWindowAddItem();
                tblItems.setDisable(false);
            }
        });

        txtCreated.setOnKeyReleased(ev -> {
            if (ev.getCode() == ESCAPE) {
                hideWindowAddItem();
                tblItems.setDisable(false);
            }
        });


        txtDescriptionItem.setOnKeyReleased(ev -> {
            if (ev.getCode() == ESCAPE) {
                hideWindowAddItem();
                tblItems.setDisable(false);
            }
        });

        cmbCategory.setOnKeyReleased(ev -> {
            if (ev.getCode() == ESCAPE) {
                hideWindowAddItem();
                tblItems.setDisable(false);
            }
        });


    }

    private void keyDeleteItem() {
     if (true) {
            rootItems.setOnKeyPressed((KeyEvent keyEvent) -> {
                if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                    if (tblItems.isDisable()) {
                        System.out.println("To delete, finish saving the registry or cancel the operation");
                    } else if (tblItems.getSelectionModel().getSelectedItems().isEmpty()) {
                        Resources.showErrorAlert(stckItems, rootItems, tblItems, PropertiesCache.getInstance().getProperty("selection.tableau"));
                    } else {
                        deleteProducts();
                    }
                }
            });
        }
    }

    @FXML
    private void filterCategory() {
        String filterName = txtSearchCategory.getText();
        if (Validators.isEmpty(filterName)) {
            tblItems.setItems(listItems);
        } else {
            filterItems.clear();
            for (ItemTV p : listItems) {
                if (p.getName().toLowerCase().contains(filterName.toLowerCase())) {
                    filterItems.add(p);
                }
            }
            tblItems.setItems(filterItems);
        }


    }

    @FXML
    private void filterName() {

        String filterName = txtSearchItem.getText();
        if (Validators.isEmpty(filterName)) {
            tblItems.setItems(listItems);
        } else {
            filterItems.clear();
            for (ItemTV p : listItems) {
                if (p.getName().toLowerCase().contains(filterName.toLowerCase())) {
                    filterItems.add(p);
                }
            }
            tblItems.setItems(filterItems);
        }
    }





    private void expandImage(long id) {
        new FadeOutUp(icon).play();
        paneContainer.hoverProperty().addListener((o, oldValue, newValue) -> {
            if (newValue) {
                new FadeInUp(icon).play();
            } else {
                new FadeOutUp(icon).play();
            }
        });

        icon.setOnMouseClicked(ev -> {
            final Image image = itemService.getImageItem(id);
            //stageManager.newMethode(  image,title);
            Stage stage = new Stage();//stageManager.getPrimaryStage();
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
            stage.setTitle("View Image");
            stage.getIcons().add(new Image("images/sbsa-icon.png"));//Resources.SOURCE_PACKAGES +""+
            stage.show();
//            stageManager.addNewScene(FxmlView.customerDetails);
        });
    }

    public void itemClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() >= 2)
            this.showWindowDetailsItem();
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
}
