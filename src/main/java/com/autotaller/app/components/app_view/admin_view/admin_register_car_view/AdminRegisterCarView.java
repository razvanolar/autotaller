package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 02.05.2017
 */
public class AdminRegisterCarView extends IterableView implements AdminRegisterCarController.IAdminRegisterCarView {

  private SplitPane splitPane;
  private GridPane filterPane;
  private GridPane detailPane;
  private TableView<CarModel> carTable;
  private ScrollPane filterScrollPane;
  private ScrollPane detailScrollPane;
  private Button addCarButton;
  private Button editCarButton;
  private Button deleteCarButton;
  private ToggleButton showFilterCarButton;
  private ToggleButton carDetailsButton;

  private double lastFilterDividerPosition = 0.2;
  private double lastDetailsDividerPosition = 0.8;

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

    filterPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    detailPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    filterScrollPane = NodeProvider.createScrollPane(filterPane, true);
    detailScrollPane = NodeProvider.createScrollPane(detailPane, true);

    carTable = NodeProvider.createCarTable();
    splitPane = new SplitPane(carTable);
    borderPane.setCenter(splitPane);

    SplitPane.setResizableWithParent(filterScrollPane, false);
    SplitPane.setResizableWithParent(detailScrollPane, false);
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

  public void showDetailsPane() {
    ObservableList<Node> items = splitPane.getItems();
    if (!items.contains(detailScrollPane)) {
      items.add(detailScrollPane);
      splitPane.setDividerPosition(items.size() > 2 ? 1 : 0 , lastDetailsDividerPosition);
    }
  }

  public void hideDetailsPane() {
    ObservableList<Node> items = splitPane.getItems();
    if (items.contains(detailScrollPane)) {
      double[] dividers = splitPane.getDividerPositions();
      if (dividers != null && dividers.length > 0) {
        if (dividers.length > 1)
          lastDetailsDividerPosition = dividers[1];
        else
          lastDetailsDividerPosition = dividers[0];
      }
      items.remove(detailScrollPane);
    }
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
