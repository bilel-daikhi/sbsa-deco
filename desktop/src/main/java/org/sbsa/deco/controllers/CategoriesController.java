package org.sbsa.deco.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.sbsa.deco.dto.CategoryDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.sbsa.deco.service.CategoryService;
import org.sbsa.deco.utils.DialogTransition;
import org.sbsa.deco.utils.PropertiesCache;
import org.sbsa.deco.utils.Resources;
import org.sbsa.deco.utils.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.input.KeyCode.ESCAPE;
import static org.sbsa.deco.utils.Resources.dialog;

@Controller
public class CategoriesController implements Initializable {
    private final Logger logger = Logger.getLogger(CategoriesController.class.getName());
    private final BoxBlur blur = new BoxBlur(3, 3, 3);
    @Autowired
    private DialogTransition dialogTransition;
    private ObservableList<CategoryDTO> listCategories;

    private ObservableList<CategoryDTO> filterCategories;

    @FXML
    private StackPane stckCategories;

    @FXML
    private AnchorPane rootCategories;

    @FXML
    private AnchorPane rootDeleteCategory;

    @FXML
    private AnchorPane rootAddCategory;

    @FXML
    private TableView<CategoryDTO> tblCategories;

    @FXML
    private TableColumn<CategoryDTO, Integer> colIdCategory;

    @FXML
    private TableColumn<CategoryDTO, String> colName;

    @FXML
    private TableColumn<CategoryDTO, String> colCreated;


    @FXML
    private TableColumn<CategoryDTO, String> colNumberSubCategory;

    @FXML
    private HBox rootSearchCategories;



    @FXML
    private TextField txtSearchCategory;

    @FXML
    private JFXTextField txtCategoryName;


    @FXML
    private Text titleWindowAddCategory;

    @FXML
    private Text textConfirmation;

    @FXML
    private Text description;

    private JFXDialog dialogAddCategory;

    private JFXDialog dialogDeleteCategory;
    @Autowired
    private CategoryService categoryService;

    @FXML
    private JFXButton btnAddCategory;

    @FXML
    private JFXButton btnSaveCategory;

    @FXML
    private JFXButton btnUpdateCategory;
    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnCancelDelete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        filterCategories = FXCollections.observableArrayList();
        escapeWindowWithTextFields();
        keyDeleteCategory();
        keyEscapeWindows();
        characterLimiter();
        animateNodes();
        validation();
        selectText();
        loadData();
        setFonts();
    }

    private void setFonts() {
        Resources.setFontToJFXButton(btnUpdateCategory, 15);
        Resources.setFontToJFXButton(btnSaveCategory, 15);
        Resources.setFontToJFXButton(btnCancelDelete, 15);
        Resources.setFontToJFXButton(btnAddCategory, 12);
        Resources.setFontToJFXButton(btnCancel, 15);
        Resources.setFontToJFXButton(btnDelete, 15);

        Resources.setFontToText(titleWindowAddCategory, 20);
        Resources.setFontToText(textConfirmation, 15);
        Resources.setFontToText(description, 12);
    }

    private void animateNodes() {
        Resources.fadeInUpAnimation(rootSearchCategories);
        Resources.fadeInUpAnimation(btnAddCategory);
        Resources.fadeInUpAnimation(tblCategories);
    }

    private void selectText() {

        Resources.selectTextToJFXTextField(txtCategoryName);


        Resources.selectTextToTextField(txtSearchCategory);
    }

    private void validation() {
        Resources.validationOfJFXTextField(txtCategoryName);

    }

    private void characterLimiter() {
        Resources.limitTextField(txtCategoryName, 150);

    }


    @FXML
    private void showWindowAddCategory() {
        rootCategories.setEffect(blur);
        enableControlsEdit();
        resetValidation();
        disableTable();

        titleWindowAddCategory.setText(PropertiesCache.getInstance().getProperty("ajouter.category"));
        btnUpdateCategory.setVisible(false);
        btnSaveCategory.setVisible(true);
        btnSaveCategory.setDisable(false);
        rootAddCategory.setVisible(true);

        dialogAddCategory = new JFXDialog();
        dialogAddCategory.setTransitionType(dialogTransition.dialogTransition());
        dialogAddCategory.setDialogContainer(stckCategories);
        dialogAddCategory.setBackground(Background.EMPTY);
        dialogAddCategory.setContent(rootAddCategory);
        Resources.setStyleToAlerts(dialogAddCategory);
        dialogAddCategory.show();

        dialogAddCategory.setOnDialogOpened(ev -> {
            txtCategoryName.requestFocus();
        });

        dialogAddCategory.setOnDialogClosed(ev -> {
            rootAddCategory.setVisible(false);
            tblCategories.setDisable(false);
            rootCategories.setEffect(null);
            cleanControls();
        });
    }

    @FXML
    private void hideWindowAddCategory() {
        dialogAddCategory.close();
    }

    @FXML
    private void showWindowDeleteCategory() {
        if (tblCategories.getSelectionModel().getSelectedItems().isEmpty()) {
            Resources.showErrorAlert(stckCategories, rootCategories, tblCategories, PropertiesCache.getInstance().getProperty("table.select.item"));
        } else {
            rootCategories.setEffect(blur);
            disableTable();

            dialogDeleteCategory = new JFXDialog();
            dialogDeleteCategory.setTransitionType(dialogTransition.dialogTransition());
            dialogDeleteCategory.setDialogContainer(stckCategories);
            dialogDeleteCategory.setBackground(Background.EMPTY);
            dialogDeleteCategory.setContent(rootDeleteCategory);
            Resources.setStyleToAlerts(dialogDeleteCategory);
            rootDeleteCategory.setVisible(true);
            dialogDeleteCategory.show();

            dialogDeleteCategory.setOnDialogClosed(ev -> {
                rootDeleteCategory.setVisible(false);
                tblCategories.setDisable(false);
                rootCategories.setEffect(null);
                cleanControls();
            });
        }
    }

    @FXML
    private void hideWindowDeleteCategory() {
        if (dialogDeleteCategory != null) {
            dialogDeleteCategory.close();
        }
    }

    @FXML
    private void showWindowUptadeCategory() {
        if (tblCategories.getSelectionModel().getSelectedItems().isEmpty()) {
            Resources.showErrorAlert(stckCategories, rootCategories, tblCategories, PropertiesCache.getInstance().getProperty("table.select.item"));
        } else {
            showWindowAddCategory();
            titleWindowAddCategory.setText(PropertiesCache.getInstance().getProperty("update.category"));
            btnSaveCategory.setVisible(false);
            btnUpdateCategory.setVisible(true);
            selectedRecord();
        }
    }

    public void itemClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() >= 2)
            this.showWindowDetailsCategory();
    }

    @FXML
    private void showWindowDetailsCategory() {
        if (tblCategories.getSelectionModel().getSelectedItems().isEmpty()) {
            Resources.showErrorAlert(stckCategories, rootCategories, tblCategories, PropertiesCache.getInstance().getProperty("table.select.item"));
        } else {
            showWindowAddCategory();
            titleWindowAddCategory.setText(PropertiesCache.getInstance().getProperty("details.category"));
            btnUpdateCategory.setVisible(false);
            btnSaveCategory.setDisable(true);
            btnSaveCategory.toFront();
            disableControlsEdit();
            selectedRecord();
        }
    }

    private void selectedRecord() {
        CategoryDTO client = tblCategories.getSelectionModel().getSelectedItem();
        txtCategoryName.setText(client.getName());

    }

    @FXML
    private void loadData() {
        loadTable();
        colIdCategory.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCreated.setCellValueFactory(new PropertyValueFactory<>("created"));
        colNumberSubCategory.setCellValueFactory(new PropertyValueFactory<>("subCategoriesCount"));
    }

    private void loadTable() {
        List<CategoryDTO> list = categoryService.findAllCategory().toList();


        listCategories = FXCollections.observableArrayList(list);
        tblCategories.setItems(listCategories);
    }

    @FXML
    private void newCategory() {
        if (Validators.isEmpty(txtCategoryName.getText())) {
            Resources.shakeAnimation(txtCategoryName);
            Resources.selectTextToJFXTextField(txtCategoryName);
            return;
        }


        CategoryDTO client = new CategoryDTO();
        client.setName(txtCategoryName.getText());

        boolean result = categoryService.saveCategory(client, listCategories);
        if (result) {
            loadData();
            cleanControls();
            hideWindowAddCategory();
            Resources.showSuccessAlert(stckCategories, rootCategories, tblCategories, PropertiesCache.getInstance().getProperty("saved.message.succes"));
        } else {
            Resources.notification(PropertiesCache.getInstance().getProperty("erreur.fatal"), PropertiesCache.getInstance().getProperty("database.error"), "error.png");
        }

    }

    @FXML
    private void deleteCategory() {
        boolean result = categoryService.deleteCategory(tblCategories, listCategories);
        if (result) {
            loadData();
            cleanControls();
            hideWindowDeleteCategory();
            Resources.showSuccessAlert(stckCategories, rootCategories, tblCategories, PropertiesCache.getInstance().getProperty("deletion.message.succes"));
        } else {
            Resources.notification(PropertiesCache.getInstance().getProperty("erreur.fatal"), PropertiesCache.getInstance().getProperty("database.error"), "error.png");
        }
    }

    @FXML
    private void updateCategory() {
        if (Validators.isEmpty(txtCategoryName.getText())) {
            Resources.shakeAnimation(txtCategoryName);
            return;
        }


        CategoryDTO client = tblCategories.getSelectionModel().getSelectedItem();
        client.setId(client.getId());
        client.setName(txtCategoryName.getText());


        boolean result = categoryService.updateCategory(client);
        if (result) {
            loadData();
            cleanControls();
            hideWindowAddCategory();
            Resources.showSuccessAlert(stckCategories, rootCategories, tblCategories, PropertiesCache.getInstance().getProperty("update.message.succes"));
        } else {
            Resources.notification(PropertiesCache.getInstance().getProperty("erreur.fatal"), PropertiesCache.getInstance().getProperty("database.error"), "error.png");
        }

    }

    private void cleanControls() {
        txtCategoryName.clear();

    }

    private void disableControlsEdit() {
        txtCategoryName.setEditable(false);

    }

    private void enableControlsEdit() {
        txtCategoryName.setEditable(true);

    }

    private void resetValidation() {
        txtCategoryName.resetValidation();

    }

    private void disableTable() {
        tblCategories.setDisable(true);
    }


    private void keyEscapeWindows() {
        rootCategories.setOnKeyReleased((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == ESCAPE && rootDeleteCategory.isVisible()) {
                hideWindowDeleteCategory();
            }
            if (keyEvent.getCode() == ESCAPE && rootAddCategory.isVisible()) {
                hideWindowAddCategory();
            }
            if (dialog != null) {
                if (keyEvent.getCode() == ESCAPE && dialog.isVisible()) {
                    tblCategories.setDisable(false);
                    rootCategories.setEffect(null);
                    dialog.close();
                }
            }
        });
    }

    private void escapeWindowWithTextFields() {
        txtCategoryName.setOnKeyReleased(ev -> {
            if (ev.getCode() == ESCAPE) {
                hideWindowAddCategory();
            }
        });

    }

    private void keyDeleteCategory() {
        rootCategories.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                if (tblCategories.isDisable()) {
                    logger.log(Level.INFO, "Pour supprimer, terminer la sauvegarde de l'enregistrement ou annuler l'operation");
                } else if (tblCategories.getSelectionModel().getSelectedItems().isEmpty()) {
                    Resources.showErrorAlert(stckCategories, rootCategories, tblCategories, PropertiesCache.getInstance().getProperty("table.select.item"));
                } else {
                    deleteCategory();
                }
            }
        });
    }
//    @FXML
//    private void filterCategoriesByName() {
//        String filterName = txtSearchName.getText();
//        if (Validators.isEmpty(filterName)) {
//            tblCategories.setItems(listCategories);
//        } else {
//            filterCategories.clear();
//            for (CategoryDTO c : listCategories) {
//                if (c.getName().toLowerCase().contains(filterName.toLowerCase())) {
//                    filterCategories.add(c);
//                }
//            }
//            tblCategories.setItems(filterCategories);
//        }
//    }

    @FXML
    private void filterNameCategory() {
        String filterName = txtSearchCategory.getText();
        if (Validators.isEmpty(filterName)) {
            tblCategories.setItems(listCategories);
        } else {
            filterCategories.clear();
            for (CategoryDTO c : listCategories) {
                if (c.getName().toLowerCase().contains(filterName.toLowerCase())) {
                    filterCategories.add(c);
                }
            }
            tblCategories.setItems(filterCategories);
        }
    }


}
