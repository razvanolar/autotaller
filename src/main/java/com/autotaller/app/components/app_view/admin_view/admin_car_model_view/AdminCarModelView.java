package com.autotaller.app.components.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.components.app_view.admin_view.AdminToolbarPane;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AdminCarModelView extends AdminToolbarPane implements AdminCarModelController.IAdminCarModelView {

  private BorderPane borderPane;

  private Button addCarModelButton;
  private ToggleButton filterButton;

  private SplitPane splitPane;
  private GridPane filterPane;
  private TableView<CarTypeModel> carModelTable;

  private double lastDividerPosition = .25;

  public AdminCarModelView() {
    super("Model");
    init();
    setContentNode(borderPane);
  }

  private void init() {
    filterPane = new GridPane();
    carModelTable = NodeProvider.createCarModelTable();

    splitPane = new SplitPane(carModelTable);

    addCarModelButton = new Button("Adauga Model");
    filterButton = new ToggleButton("Filtrare");
    addCarModelButton.setRotate(-90);
    filterButton.setRotate(-90);
    Group addCarModelGroup = new Group(addCarModelButton);
    Group filterGroup = new Group(filterButton);

    ToolBar toolBar = NodeProvider.createToolBar();
    toolBar.getItems().addAll(addCarModelGroup, filterGroup);
    toolBar.setOrientation(Orientation.VERTICAL);

    borderPane = new BorderPane(splitPane);
    borderPane.setLeft(toolBar);
    borderPane.setPrefHeight(500);
  }

  @Override
  protected void addToolbarButtons() {

  }

  public Button getAddCarModelButton() {
    return addCarModelButton;
  }

  public ToggleButton getFilterButton() {
    return filterButton;
  }

  public TableView<CarTypeModel> getCarModelTable() {
    return carModelTable;
  }

  public void showFilterPane() {
    if (!splitPane.getItems().contains(filterPane)) {
      splitPane.getItems().add(0, filterPane);
      splitPane.setDividerPositions(lastDividerPosition);
    }
  }

  public void hideFilterPane() {
    if (splitPane.getItems().contains(filterPane)) {
      double[] dividers = splitPane.getDividerPositions();
      if (dividers != null && dividers.length > 0)
        lastDividerPosition = dividers[0];
      splitPane.getItems().remove(filterPane);
    }
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
