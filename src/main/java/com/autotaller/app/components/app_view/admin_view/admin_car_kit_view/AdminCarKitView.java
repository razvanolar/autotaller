package com.autotaller.app.components.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.components.app_view.admin_view.AdminToolbarPane;
import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.*;

/**
 * Created by razvanolar on 29.04.2017
 */
public class AdminCarKitView extends AdminToolbarPane implements AdminCarKitController.IAdminCarKitView {

  private TableView<CarKitModel> carKitTable;
  private ComboBox<CarKitCategoryModel> carKitCategoriesCombo;
  private TextField carKitNameField;

  public AdminCarKitView() {
    super("Ansamble", "Adauga Ansamblu", "Filtrare");
    init();
  }

  private void init() {
    carKitTable = NodeProvider.createCarKitModelTable();
    content.getItems().add(carKitTable);

    initFilterPane();
  }

  private void initFilterPane() {
    carKitCategoriesCombo = NodeProvider.createCarKitCategoriesCombo(FIELD_WIDTH);
    carKitNameField = NodeProvider.createTextField(FIELD_WIDTH);

    filterPane.add(NodeProvider.createFormTextLabel("Categorie: "), 0, 0);
    filterPane.add(carKitCategoriesCombo, 1, 0);
    filterPane.add(NodeProvider.createFormTextLabel("Nume: "), 0, 1);
    filterPane.add(carKitNameField, 1, 1);
  }

  public TableView<CarKitModel> getCarKitTable() {
    return carKitTable;
  }

  public ComboBox<CarKitCategoryModel> getCarKitCategoriesCombo() {
    return carKitCategoriesCombo;
  }

  @Override
  public Button getAddButton() {
    return addButton;
  }

  @Override
  public ToggleButton getFilterButton() {
    return filterButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
