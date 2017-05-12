package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view;

import com.autotaller.app.components.app_view.admin_view.util.AdminToolbarPane;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view.utils.YearsPanelView;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.*;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AdminCarModelView extends AdminToolbarPane implements AdminCarModelController.IAdminCarModelView {

  private TableView<CarTypeModel> carModelTable;

  private ComboBox<CarMakeModel> carMakeCombo;
  private TextField carModelNameTextField;
  private DatePicker fromDatePicker;
  private DatePicker toDatePicker;
  private TextField engineTextField;
  private YearsPanelView yearsPanelView;

  public AdminCarModelView() {
    super("Model", true, true);
    init();
  }

  private void init() {
    initFilterPane();
    carModelTable = NodeProvider.createCarModelTable();
    content.getItems().add(carModelTable);
  }

  private void initFilterPane() {
    carMakeCombo = NodeProvider.createCarMakesCombo(FIELD_WIDTH);
    carModelNameTextField = NodeProvider.createTextField(FIELD_WIDTH);
    fromDatePicker = NodeProvider.createDatePicker(FIELD_WIDTH);
    toDatePicker = NodeProvider.createDatePicker(FIELD_WIDTH);
    engineTextField = NodeProvider.createTextField(FIELD_WIDTH);
    yearsPanelView = new YearsPanelView(FIELD_WIDTH);

    filterPane.add(NodeProvider.createFormTextLabel("Marca: "), 0, 0);
    filterPane.add(carMakeCombo, 1, 0);
    filterPane.add(NodeProvider.createFormTextLabel("Nume Model: "), 0, 1);
    filterPane.add(carModelNameTextField, 1, 1);
    filterPane.add(NodeProvider.createFormTextLabel("De la: "), 0, 2);
    filterPane.add(fromDatePicker, 1, 2);
    filterPane.add(NodeProvider.createFormTextLabel("Pana la: "), 0, 3);
    filterPane.add(toDatePicker, 1, 3);
    filterPane.add(NodeProvider.createFormTextLabel("Sasiu: "), 0, 4);
    filterPane.add(engineTextField, 1, 4);
    filterPane.add(yearsPanelView.asNode(), 0, 5, 2, 1);
  }

  public Button getAddButton() {
    return addButton;
  }

  public ToggleButton getFilterButton() {
    return filterButton;
  }

  public TableView<CarTypeModel> getCarModelTable() {
    return carModelTable;
  }

  public ComboBox<CarMakeModel> getCarMakeCombo() {
    return carMakeCombo;
  }

  public TextField getCarModelNameTextField() {
    return carModelNameTextField;
  }

  public DatePicker getFromDatePicker() {
    return fromDatePicker;
  }

  public DatePicker getToDatePicker() {
    return toDatePicker;
  }

  public TextField getEngineTextField() {
    return engineTextField;
  }

  public YearsPanelView getYearsPanelView() {
    return yearsPanelView;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
