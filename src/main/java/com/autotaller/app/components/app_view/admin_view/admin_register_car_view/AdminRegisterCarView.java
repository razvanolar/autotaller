package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.components.utils.filter_views.DefaultCarFilterView;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;

/**
 * Created by razvanolar on 02.05.2017
 */
public class AdminRegisterCarView extends IterableView implements AdminRegisterCarController.IAdminRegisterCarView {

  private SplitPane splitPane;
  private DefaultCarFilterView filterView;
  private TableView<CarModel> carTable;
  private ScrollPane filterScrollPane;
  private Button addCarButton;
  private Button editCarButton;
  private Button deleteCarButton;
  private ToggleButton showFilterCarButton;
  private ToggleButton carDetailsButton;

  private double lastFilterDividerPosition = 0.30;

  public AdminRegisterCarView() {
    init();
  }

  private void init() {
    addCarButton = new Button("Adauga");
    editCarButton = new Button("Editeaza");
    deleteCarButton = new Button("Sterge");
    showFilterCarButton = new ToggleButton("Filtreaza");
    carDetailsButton = new ToggleButton("Detalii");

    toolBar.getItems().addAll(
            new Separator(),
            addCarButton,
            editCarButton,
            deleteCarButton,
            new Separator(),
            showFilterCarButton,
            carDetailsButton
    );

    filterView = new DefaultCarFilterView();
    filterScrollPane = NodeProvider.createScrollPane(filterView.asRegion(), true);

    carTable = NodeProvider.createCarTable();
    splitPane = new SplitPane(carTable);
    borderPane.setCenter(splitPane);

    SplitPane.setResizableWithParent(filterScrollPane, false);
  }

  public TableView<CarModel> getCarTable() {
    return carTable;
  }

  public DefaultCarFilterView getFilterView() {
    return filterView;
  }

  public Button getAddCarButton() {
    return addCarButton;
  }

  public Button getEditCarButton() {
    return editCarButton;
  }

  public Button getDeleteCarButton() {
    return deleteCarButton;
  }

  public ToggleButton getShowFilterCarButton() {
    return showFilterCarButton;
  }

  public ToggleButton getCarDetailsButton() {
    return carDetailsButton;
  }

  public void showFilterPane() {
    ObservableList<Node> items = splitPane.getItems();
    if (!items.contains(filterScrollPane)) {
      items.add(0, filterScrollPane);
      splitPane.setDividerPosition(0, lastFilterDividerPosition);
    }
  }

  public void hideFilterPane() {
    ObservableList<Node> items = splitPane.getItems();
    if (items.contains(filterScrollPane)) {
      double[] dividers = splitPane.getDividerPositions();
      if (dividers != null && dividers.length > 0)
        lastFilterDividerPosition = dividers[0];
      items.remove(filterScrollPane);
    }
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
