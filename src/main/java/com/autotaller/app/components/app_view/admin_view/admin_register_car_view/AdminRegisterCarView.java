package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.components.utils.filter_views.DefaultCarFilterView;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.collections.ObservableList;
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
  private Button carDetailsButton;
  private Button componentsButton;

  private double lastFilterDividerPosition = 0.30;

  public AdminRegisterCarView() {
    init();
  }

  private void init() {
    addCarButton = NodeProvider.createToolbarButton("Adauga", ImageProvider.addIcon());
    editCarButton = NodeProvider.createToolbarButton("Editeaza", ImageProvider.editIcon());
    deleteCarButton = NodeProvider.createToolbarButton("Sterge", ImageProvider.deleteIcon());
    showFilterCarButton = NodeProvider.createToolbarToggleButton("Filtreaza", ImageProvider.filterIcon());
    carDetailsButton = NodeProvider.createToolbarButton("Detalii", ImageProvider.detailsIcon());
    componentsButton = NodeProvider.createToolbarButton("Componente", ImageProvider.componentsIcon());

    toolBar.getItems().addAll(
            new Separator(),
            addCarButton,
            editCarButton,
            deleteCarButton,
            new Separator(),
            showFilterCarButton,
            carDetailsButton,
            componentsButton
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

  public Button getCarDetailsButton() {
    return carDetailsButton;
  }

  public Button getComponentsButton() {
    return componentsButton;
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
