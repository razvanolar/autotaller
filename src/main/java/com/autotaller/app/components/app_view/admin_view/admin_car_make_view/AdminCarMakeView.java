package com.autotaller.app.components.app_view.admin_view.admin_car_make_view;

import com.autotaller.app.components.app_view.admin_view.AdminToolbarPane;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminCarMakeView extends AdminToolbarPane implements AdminCarMakeController.ICarsAdminView {

  private BorderPane borderPane;
  private SplitPane splitPane;
  private JFXMasonryPane masonryPane;
  private Button addCarMakeButton;
  private ToggleButton filterCarMakeButton;

  private GridPane filterCarMakePane;

  private int CELL_WIDTH = 150;
  private int CELL_HEIGHT = 40;

  private double lastDividerPosition = 0.3;
  private ScrollPane scrollPane;

  public AdminCarMakeView() {
    super("Marca");
    init();
    setContentNode(borderPane);
  }

  private void init() {
    masonryPane = new JFXMasonryPane();

    scrollPane = new ScrollPane(masonryPane) {
      @Override
      public void requestFocus() {

      }
    };
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);

    addCarMakeButton = new Button("Adauga");
    filterCarMakeButton = new ToggleButton("Filtreaza");
    addCarMakeButton.setRotate(-90);
    filterCarMakeButton.setRotate(-90);
    Group addCarMakeGroup = new Group(addCarMakeButton);
    Group filterCarMakeGroup = new Group(filterCarMakeButton);


    ToolBar toolBar = NodeProvider.createToolBar();
    toolBar.getItems().addAll(addCarMakeGroup, filterCarMakeGroup);
    toolBar.setOrientation(Orientation.VERTICAL);

    splitPane = new SplitPane(scrollPane);
    borderPane = new BorderPane(splitPane);
    borderPane.setLeft(toolBar);

    initFilterCarMakePane();

    borderPane.setPrefHeight(500);
  }

  private void initFilterCarMakePane() {
    filterCarMakePane = new GridPane();
  }

  @Override
  protected void addToolbarButtons() {

  }

  @Override
  public void addNode(CarMakeModel model) {
    masonryPane.getChildren().add(NodeProvider.createCarMakeMenu(model.getName(), null, CELL_WIDTH, CELL_HEIGHT));
  }

  public Button getAddCarMakeButton() {
    return addCarMakeButton;
  }

  public ToggleButton getFilterCarMakeButton() {
    return filterCarMakeButton;
  }

  public void showFilterCarMakePane() {
    hideAdditionalPanel();
    splitPane.getItems().add(0, filterCarMakePane);
    splitPane.setDividerPositions(lastDividerPosition);
  }

  public void hideAdditionalPanel() {
    double[] dividerPositions = splitPane.getDividerPositions();
    if (dividerPositions != null && dividerPositions.length > 0)
      lastDividerPosition = dividerPositions[0];
    if (splitPane.getItems().contains(filterCarMakePane))
      splitPane.getItems().remove(filterCarMakePane);
  }

  @Override
  public void clearNodes() {
    masonryPane = new JFXMasonryPane();
    masonryPane.setCellWidth(CELL_WIDTH);
    masonryPane.setCellHeight(CELL_HEIGHT);
    scrollPane.setContent(masonryPane);
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
