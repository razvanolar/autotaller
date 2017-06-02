package com.autotaller.app.components.app_view.admin_view.admin_components_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 13.05.2017
 */
public class AdminComponentsView extends IterableView implements AdminComponentsController.IAdminComponentsView {

  private SplitPane mainSplitPane;
  private GridPane filterPane;
  private TableView<CarComponentModel> carComponentsTable;

  private Button addComponentButton;
  private Button editComponentButton;
  private Button deleteComponentButton;
  private ToggleButton filterComponentsButton;
  private Button detailsButton;

  private double lastDividerPosition = 0.3;

  public AdminComponentsView() {
    init();
  }

  private void init() {
    addComponentButton = NodeProvider.createToolbarButton("Adauga", ImageProvider.addIcon());
    editComponentButton = NodeProvider.createToolbarButton("Editeaza", ImageProvider.editIcon());
    deleteComponentButton = NodeProvider.createToolbarButton("Sterge", ImageProvider.deleteIcon());
    filterComponentsButton = NodeProvider.createToolbarToggleButton("Filtreaza", ImageProvider.filterIcon());
    detailsButton = NodeProvider.createToolbarButton("Detalii", ImageProvider.detailsIcon());

    toolBar.getItems().addAll(new Separator(), addComponentButton, editComponentButton, deleteComponentButton,
            new Separator(), filterComponentsButton, detailsButton);

    carComponentsTable = NodeProvider.createCarComponentTable(true);
    filterPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);

    mainSplitPane = new SplitPane(carComponentsTable);
    mainSplitPane.setDividerPosition(0, lastDividerPosition);

    borderPane.setCenter(mainSplitPane);
  }

  public Button getAddComponentButton() {
    return addComponentButton;
  }

  public Button getEditComponentButton() {
    return editComponentButton;
  }

  public Button getDeleteComponentButton() {
    return deleteComponentButton;
  }

  public ToggleButton getFilterComponentsButton() {
    return filterComponentsButton;
  }

  public Button getDetailsButton() {
    return detailsButton;
  }

  public TableView<CarComponentModel> getCarComponentsTable() {
    return carComponentsTable;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
