package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_subkit_view;

import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.ModelFilter;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Created by razvanolar on 01.05.2017
 */
public class AdminCarSubkitForm implements IAdminCarSubkitFormView {

  protected GridPane gridPane;
  protected ComboBox<CarKitCategoryModel> carKitCategoriesCombo;
  protected ComboBox<CarKitModel> carKitsCombo;
  protected TextField carSubkitNameField;
  protected CheckBox gasolineCheckBox;
  protected CheckBox dieselCheckBox;
  protected CheckBox gplCheckBox;
  protected CheckBox electricCheckBox;

  private List<CarKitCategoryModel> carKitCategories;
  private List<CarKitModel> carKits;

  private static int FIELD_WIDTH = 220;

  public AdminCarSubkitForm() {
    init();
    initHandlers();
  }

  private void init() {
    carKitCategoriesCombo = NodeProvider.createCarKitCategoriesCombo(FIELD_WIDTH);
    carKitsCombo = NodeProvider.createCarKitCombo(FIELD_WIDTH);
    carSubkitNameField = NodeProvider.createTextField(FIELD_WIDTH);
    gasolineCheckBox = new CheckBox();
    dieselCheckBox = new CheckBox();
    gplCheckBox = new CheckBox();
    electricCheckBox = new CheckBox();

    gridPane = new GridPane();
    gridPane.add(NodeProvider.createFormTextLabel("Categorie: "), 0 ,0);
    gridPane.add(carKitCategoriesCombo, 1, 0);
    gridPane.add(NodeProvider.createFormTextLabel("Ansamblu: "), 0 ,1);
    gridPane.add(carKitsCombo, 1, 1);
    gridPane.add(NodeProvider.createFormTextLabel("Nume: "), 0, 2);
    gridPane.add(carSubkitNameField, 1, 2);
    gridPane.add(NodeProvider.createFormTextLabel("Benzina: "), 0, 3);
    gridPane.add(gasolineCheckBox, 1, 3);
    gridPane.add(NodeProvider.createFormTextLabel("Diesel: "), 0, 4);
    gridPane.add(dieselCheckBox, 1, 4);
    gridPane.add(NodeProvider.createFormTextLabel("GPL: "), 0, 5);
    gridPane.add(gplCheckBox, 1, 5);
    gridPane.add(NodeProvider.createFormTextLabel("Electric: "), 0, 6);
    gridPane.add(electricCheckBox, 1, 6);

    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(10);
    gridPane.setVgap(10);
  }

  private void initHandlers() {
    carKitCategoriesCombo.valueProperty().addListener((observable, oldValue, newValue) -> populateCarKitsCombo(ModelFilter.filterCarKitsByCategory(carKits, newValue)));
  }

  private void populateCarKitsCombo(List<CarKitModel> filteredData) {
    carKitsCombo.getItems().clear();
    carKitsCombo.getItems().addAll(filteredData);
    if (!filteredData.isEmpty())
      carKitsCombo.setValue(filteredData.get(0));
  }

  @Override
  public void injectData(List<CarKitCategoryModel> carKitCategories, List<CarKitModel> carKits) {
    this.carKitCategories = carKitCategories;
    this.carKits = carKits;

    carKitCategoriesCombo.getItems().clear();
    carKitCategoriesCombo.getItems().addAll(carKitCategories);
    if (!carKitCategories.isEmpty()) {
      CarKitCategoryModel value = carKitCategories.get(0);
      carKitCategoriesCombo.setValue(value);
      populateCarKitsCombo(ModelFilter.filterCarKitsByCategory(carKits, value));
    }
  }

  public ComboBox<CarKitCategoryModel> getCarKitCategoriesCombo() {
    return carKitCategoriesCombo;
  }

  public ComboBox<CarKitModel> getCarKitsCombo() {
    return carKitsCombo;
  }

  public TextField getCarSubkitNameField() {
    return carSubkitNameField;
  }

  public CheckBox getGasolineCheckBox() {
    return gasolineCheckBox;
  }

  public CheckBox getDieselCheckBox() {
    return dieselCheckBox;
  }

  public CheckBox getGplCheckBox() {
    return gplCheckBox;
  }

  public CheckBox getElectricCheckBox() {
    return electricCheckBox;
  }

  @Override
  public Node asNode() {
    return gridPane;
  }
}
