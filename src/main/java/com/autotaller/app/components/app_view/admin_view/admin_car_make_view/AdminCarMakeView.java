package com.autotaller.app.components.app_view.admin_view.admin_car_make_view;

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
public class AdminCarMakeView implements AdminCarMakeController.ICarsAdminView {

  private BorderPane mainContainer;
  private SplitPane splitPane;
  private JFXMasonryPane masonryPane;
  private Button addCarMakeButton;
  private ToggleButton filterCarMakeButton;

  private GridPane filterCarMakePane;

  private int CELL_WIDTH = 150;
  private int CELL_HEIGHT = 40;

  private double lastDividerPosition = 0.3;

  public AdminCarMakeView() {
    init();
  }

  private void init() {
    masonryPane = new JFXMasonryPane();
    masonryPane.setCellWidth(CELL_WIDTH);
    masonryPane.setCellHeight(CELL_HEIGHT);

    ScrollPane scrollPane = new ScrollPane(masonryPane);
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);

    addCarMakeButton = new Button("Adauga");
    filterCarMakeButton = new ToggleButton("Filtreaza");
    addCarMakeButton.setRotate(-90);
    filterCarMakeButton.setRotate(-90);
    Group addCarMakeGroup = new Group(addCarMakeButton);
    Group filterCarMakeGroup = new Group(filterCarMakeButton);

    ToolBar toolBar = new ToolBar(addCarMakeGroup, filterCarMakeGroup);
    toolBar.setOrientation(Orientation.VERTICAL);

    splitPane = new SplitPane(scrollPane);
    mainContainer = new BorderPane(splitPane);
    mainContainer.setLeft(toolBar);

    initFilterCarMakePane();
  }

  private void initFilterCarMakePane() {
    filterCarMakePane = new GridPane();
  }

  @Override
  public void addNode(CarMakeModel model) {
    masonryPane.getChildren().add(NodeProvider.createAppMenu(model.getName(), null, CELL_WIDTH, CELL_HEIGHT));
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
  public Node asNode() {
    return mainContainer;
  }
}
