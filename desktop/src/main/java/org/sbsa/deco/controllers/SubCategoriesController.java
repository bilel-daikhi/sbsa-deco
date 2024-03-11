package org.sbsa.deco.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.sbsa.deco.dto.CategoryDTO;
import com.sbsa.deco.dto.SubCategoryDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.service.CategoryService;
import org.sbsa.deco.service.SubCategoriesService;
import org.sbsa.deco.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.input.KeyCode.ESCAPE;
import static org.sbsa.deco.utils.Resources.dialog;
import static org.sbsa.deco.utils.Resources.shakeAnimation;

@Controller
@Slf4j
public class SubCategoriesController implements Initializable {
    private final BoxBlur blur = new BoxBlur(3, 3, 3);
    @Autowired
    private DialogTransition dialogTransition;
    private ObservableList<SubCategoryDTO> listSubCategories;
    private ObservableList<CategoryDTO> listCategories;
    private ObservableList<SubCategoryDTO> filterSubCategories;
    @FXML
    private StackPane stckSubCategory;
    @FXML
    private AnchorPane rootSubCategory;
    @FXML
    private TableView<SubCategoryDTO> tblSubCategories;
    @FXML
    private TableColumn<SubCategoryDTO, Integer> colId;
    @FXML
    private TableColumn<SubCategoryDTO, String> colName;
    @FXML
    private TableColumn<SubCategoryDTO, String> colCategory;

    @FXML
    private TableColumn<SubCategoryDTO, String> colItemsNo;
    @FXML
    private TableColumn<SubCategoryDTO, String> colCreated;


    @FXML
    private HBox rootSearchSubCategories;
    @FXML
    private AnchorPane rootAddSubCategory;
    @FXML
    private AnchorPane rootDeleteSubCategory;
    @FXML
    private TextField txtSearchWithName;
    @FXML
    private TextField txtSearchWithCategoryName;
    @FXML
    private JFXTextField txtName;


    @FXML
    private JFXButton btnAddSubCategory;
    @FXML
    private JFXButton btnSaveSubCategory;
    @FXML
    private JFXButton btnUpdateSubCategory;
    @FXML
    private JFXButton btnCancelAdd;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnCancelDelete;
    @FXML
    private JFXComboBox<CategoryDTO> cmbCategories;
    @FXML
    private Text titleWindowAddSubCategory;
    @FXML
    private Text titleWindowDeleteSubCategory;
    @FXML
    private Text descriptionWindowDeleteSubCategory;


    private JFXDialog dialogAddSubCategory;
    private JFXDialog dialogDeleteSubCategory;
    @Autowired
    private SubCategoriesService subCategoriesService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        filterSubCategories = FXCollections.observableArrayList();
//        setActionToggleButton();
        initializeComboBox();
        keyDeleteSubCategory();
        keyEscapeWindows();
        loadData();
        setFonts();
        animateNodes();
        validations();
        selectText();
        escapeWindowWithTextFields();
    }

    private void initializeComboBox() {
        loadComboBox();
        AutocompleteComboBox.autoCompleteComboBoxPlus(cmbCategories, (typedText, itemToCompare) -> itemToCompare.toString().toLowerCase().contains(typedText.toLowerCase()) || itemToCompare.toString().equals(typedText));
    }

    private void setFonts() {
        Resources.setFontToJFXButton(btnCancelDelete, 15);
        Resources.setFontToJFXButton(btnUpdateSubCategory, 15);
        Resources.setFontToJFXButton(btnSaveSubCategory, 15);
        Resources.setFontToJFXButton(btnAddSubCategory, 12);
        Resources.setFontToJFXButton(btnCancelAdd, 15);
        Resources.setFontToJFXButton(btnDelete, 15);

        Resources.setFontToText(descriptionWindowDeleteSubCategory, 12);
        Resources.setFontToText(titleWindowDeleteSubCategory, 15);
        Resources.setFontToText(titleWindowAddSubCategory, 20);
    }

    private void animateNodes() {
        Resources.fadeInUpAnimation(rootSearchSubCategories);
        Resources.fadeInUpAnimation(btnAddSubCategory);
        Resources.fadeInUpAnimation(tblSubCategories);
    }

    @FXML
    private void showWindowAddCategory() {
        cmbCategories.setPromptText(PropertiesCache.getInstance().getProperty("selection.category"));
        cmbCategories.setEditable(true);
        cmbCategories.setDisable(false);

        resetValidations();
        enableControlsEdit();
        disableTable();
        rootSubCategory.setEffect(blur);

        btnUpdateSubCategory.setVisible(false);
        btnSaveSubCategory.setVisible(true);
        btnSaveSubCategory.setDisable(false);
        rootAddSubCategory.setVisible(true);

        btnSaveSubCategory.toFront();
        titleWindowAddSubCategory.setText(PropertiesCache.getInstance().getProperty("add.subcategory"));

        dialogAddSubCategory = new JFXDialog();
        dialogAddSubCategory.setTransitionType(dialogTransition.dialogTransition());
        dialogAddSubCategory.setBackground(Background.EMPTY);
        dialogAddSubCategory.setDialogContainer(stckSubCategory);
        dialogAddSubCategory.setContent(rootAddSubCategory);
        Resources.setStyleToAlerts(dialogAddSubCategory);
        dialogAddSubCategory.show();

        dialogAddSubCategory.setOnDialogOpened(ev -> {
            txtName.requestFocus();
        });

        dialogAddSubCategory.setOnDialogClosed(ev -> {
            rootAddSubCategory.setVisible(false);
            tblSubCategories.setDisable(false);
            rootSubCategory.setEffect(null);
            cleanControls();
        });


        cmbCategories.focusedProperty().addListener(ev -> {
            cmbCategories.show();
        });

        Resources.setTextIsEmpty(txtName);


    }

    @FXML
    private void hideWindowAddSubCategory() {
        dialogAddSubCategory.close();
    }

    @FXML
    private void showWindowDeleteSubCategory() {
        if (tblSubCategories.getSelectionModel().getSelectedItems().isEmpty()) {
            Resources.showErrorAlert(stckSubCategory, rootSubCategory, tblSubCategories, PropertiesCache.getInstance().getProperty("selection.tableau"));
        } else {
            rootSubCategory.setEffect(blur);
            disableTable();

            dialogDeleteSubCategory = new JFXDialog();
            dialogDeleteSubCategory.setTransitionType(dialogTransition.dialogTransition());
            dialogDeleteSubCategory.setBackground(Background.EMPTY);
            dialogDeleteSubCategory.setDialogContainer(stckSubCategory);
            dialogDeleteSubCategory.setContent(rootDeleteSubCategory);
            Resources.setStyleToAlerts(dialogDeleteSubCategory);
            rootDeleteSubCategory.setVisible(true);
            dialogDeleteSubCategory.show();

            dialogDeleteSubCategory.setOnDialogClosed(ev -> {
                rootDeleteSubCategory.setVisible(false);
                tblSubCategories.setDisable(false);
                rootSubCategory.setEffect(null);
                cleanControls();
            });
        }
    }

    @FXML
    private void hideWindowDeleteSubCategory() {
        if (dialogDeleteSubCategory != null) {
            dialogDeleteSubCategory.close();
        }
    }

    @FXML
    private void showWindowUpdateSubCategory() {
        if (tblSubCategories.getSelectionModel().getSelectedItems().isEmpty()) {
            Resources.showErrorAlert(stckSubCategory, rootSubCategory, tblSubCategories, PropertiesCache.getInstance().getProperty("selection.tableau"));
        } else {
            showWindowAddCategory();

            titleWindowAddSubCategory.setText(PropertiesCache.getInstance().getProperty("update.subcategory"));

            cmbCategories.setPromptText("");
            cmbCategories.setDisable(true);
            cmbCategories.setEditable(true);

            selectedRecord();
            btnUpdateSubCategory.setVisible(true);
            btnSaveSubCategory.setVisible(false);

        }
    }

    public void itemClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() >= 2)
            this.showWindowDetailsSubCategory();
    }

    @FXML
    private void showWindowDetailsSubCategory() {
        if (tblSubCategories.getSelectionModel().isEmpty()) {
            Resources.showErrorAlert(stckSubCategory, rootSubCategory, tblSubCategories, PropertiesCache.getInstance().getProperty("selection.tableau"));
        } else {
            showWindowAddCategory();

            titleWindowAddSubCategory.setText(PropertiesCache.getInstance().getProperty("detail.subcategory"));
            cmbCategories.setPromptText(PropertiesCache.getInstance().getProperty("select.category"));
            cmbCategories.setEditable(true);

            btnSaveSubCategory.setDisable(true);
            btnUpdateSubCategory.setVisible(false);
            btnSaveSubCategory.toFront();

            selectedRecord();
            disableControlsEdit();
        }
    }

    private void selectedRecord() {
        SubCategoryDTO subCategoryDTO = tblSubCategories.getSelectionModel().getSelectedItem();
        txtName.setText(subCategoryDTO.getName());
        cmbCategories.getSelectionModel().select(subCategoryDTO.getCategory());


    }

    @FXML
    private void loadData() {
        loadTable();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colItemsNo.setCellValueFactory(new PropertyValueFactory<>("itemsCounter"));
        colCreated.setCellValueFactory(new PropertyValueFactory<>("created"));

    }

    private void loadTable() {
        List<SubCategoryDTO> list = new ArrayList<>();
        try {
            list = subCategoriesService.findAll().toList();
            list.stream().forEach(subcategory -> {
                log.info("sub category: " + subcategory.getName());
            });

        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            Resources.showErrorAlert(stckSubCategory, rootSubCategory, tblSubCategories, PropertiesCache.getInstance().getProperty("database.error")
            );
        }
        listSubCategories = FXCollections.observableArrayList(list);
        tblSubCategories.setItems(listSubCategories);
    }

    private void loadComboBox() {
        List<CategoryDTO> list = new ArrayList<>();
        try {
            list = categoryService.findAllCategory().toList();


        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        listCategories = FXCollections.observableArrayList(list);
        cmbCategories.setItems(listCategories);
    }

    @FXML
    private void newSubCategory() {
        if (cmbCategories.getSelectionModel().isEmpty()) {
            shakeAnimation(cmbCategories);
        } else if (Validators.isEmpty(txtName.getText())) {
            shakeAnimation(txtName);
        } else {
            SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
            subCategoryDTO.setName(txtName.getText());
            CategoryDTO selectedCategory = cmbCategories.getSelectionModel().getSelectedItem();
            boolean result = subCategoriesService.saveSubCategory(subCategoryDTO, selectedCategory);
            if (result) {
                loadData();
                cleanControls();
                hideWindowAddSubCategory();
                Resources.showSuccessAlert(stckSubCategory, rootSubCategory, tblSubCategories, PropertiesCache.getInstance().getProperty("enregistrement.succes.message"));
            } else {
                Resources.notification(PropertiesCache.getInstance().getProperty("erreur.fatal"), PropertiesCache.getInstance().getProperty("database.error"), "error.png");
            }
        }
    }

    @FXML
    private void deleteSubCategory() {
        boolean result = subCategoriesService.deleteSubCategory(tblSubCategories, listSubCategories);
        if (result) {
            loadData();
            hideWindowDeleteSubCategory();
            Resources.showSuccessAlert(stckSubCategory, rootSubCategory, tblSubCategories, PropertiesCache.getInstance().getProperty("enregistrement.supprmer.succes"));
        } else {
            Resources.notification(PropertiesCache.getInstance().getProperty("erreur.fatal"), PropertiesCache.getInstance().getProperty("database.error"), "error.png");
        }

    }

    @FXML
    private void updateSubCategory() {
        if (cmbCategories.getSelectionModel().isEmpty()) {
            shakeAnimation(cmbCategories);
        } else if (Validators.isEmpty(txtName.getText())) {
            shakeAnimation(txtName);
        } else {
            CategoryDTO selectedItem = cmbCategories.getSelectionModel().getSelectedItem();
            SubCategoryDTO subCategoryDTO = tblSubCategories.getSelectionModel().getSelectedItem();


            boolean result = subCategoriesService.updateSubCategory(subCategoryDTO, selectedItem);
            if (result) {
                loadData();
                cleanControls();
                hideWindowAddSubCategory();
                Resources.showSuccessAlert(stckSubCategory, rootSubCategory, tblSubCategories, PropertiesCache.getInstance().getProperty("enregistrement.modifie.succes"));
            } else {
                Resources.notification(PropertiesCache.getInstance().getProperty("erreur.fatal"), PropertiesCache.getInstance().getProperty("database.error"), "error.png");
            }

        }
    }

    private void keyEscapeWindows() {
        rootSubCategory.setOnKeyReleased((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == ESCAPE && rootDeleteSubCategory.isVisible()) {
                hideWindowDeleteSubCategory();
            }
            if (keyEvent.getCode() == ESCAPE && rootAddSubCategory.isVisible()) {
                hideWindowAddSubCategory();
            }
            if (dialog != null) {
                if (keyEvent.getCode() == ESCAPE && dialog.isVisible()) {
                    tblSubCategories.setDisable(false);
                    rootSubCategory.setEffect(null);
                    dialog.close();
                }
            }
        });
    }

    private void escapeWindowWithTextFields() {
        txtName.setOnKeyReleased(ev -> {
            if (ev.getCode() == ESCAPE) {
                hideWindowAddSubCategory();
            }
        });


        rootAddSubCategory.setOnKeyReleased(ev -> {
            if (ev.getCode() == ESCAPE) {
                hideWindowAddSubCategory();
            }
        });
    }

    private void keyDeleteSubCategory() {
        rootSubCategory.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                if (tblSubCategories.isDisable()) {
                    System.out.println("Pour supprimer, terminer la sauvegarde de l'enregistrement ou annuler l'operation!");
                } else if (tblSubCategories.getSelectionModel().getSelectedItems().isEmpty()) {
                    Resources.showErrorAlert(stckSubCategory, rootSubCategory, tblSubCategories, PropertiesCache.getInstance().getProperty("selection.tableau"));
                } else {
                    deleteSubCategory();
                }
            }
        });
    }

    private void disableTable() {
        tblSubCategories.setDisable(true);
    }

    private void cleanControls() {
        cmbCategories.getSelectionModel().clearSelection();
        txtName.clear();
    }

    private void disableControlsEdit() {
        txtName.setEditable(false);

    }

    private void enableControlsEdit() {
        txtName.setEditable(true);

    }

    private void validations() {
        Resources.validationOfJFXComboBox(cmbCategories);
        Resources.validationOfJFXTextField(txtName);
    }

    private void resetValidations() {
        cmbCategories.resetValidation();
        txtName.resetValidation();

    }

    private void selectText() {
        Resources.selectTextToTextField(txtSearchWithName);
        Resources.selectTextToJFXTextField(txtName);
    }

    @FXML
    private void filterSubCategories() {
        String filterCustomers = txtSearchWithName.getText();
        if (Validators.isEmpty(filterCustomers)) {
            tblSubCategories.setItems(listSubCategories);
        } else {
            filterSubCategories.clear();
            for (SubCategoryDTO q : listSubCategories) {
                if (q.getName().toLowerCase().contains(filterCustomers.toLowerCase())) {
                    filterSubCategories.add(q);
                }
            }
            tblSubCategories.setItems(filterSubCategories);
        }
    }

    @FXML
    private void filterSubCategoriesByCategory() {
        String filter = txtSearchWithCategoryName.getText();
        if (Validators.isEmpty(filter)) {
            tblSubCategories.setItems(listSubCategories);
        } else {
            filterSubCategories.clear();
            for (SubCategoryDTO q : listSubCategories) {
                if (q.getName().toLowerCase().contains(filter.toLowerCase())) {
                    filterSubCategories.add(q);
                }
            }
            tblSubCategories.setItems(filterSubCategories);
        }
    }


}
