package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view;

import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.AdminToolbarPane;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.scene.Node;
import javafx.scene.control.*;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminCarMakeView extends AdminToolbarPane implements AdminCarMakeController.ICarsAdminView {

  private JFXMasonryPane masonryPane;

  private int CELL_WIDTH = 150;
  private int CELL_HEIGHT = 40;

  private ScrollPane scrollPane;

  public AdminCarMakeView() {
    super("Marca", "Adauga Marca", "Filtrare");
    init();
  }

  private void init() {
    masonryPane = new JFXMasonryPane();

    scrollPane = NodeProvider.createScrollPane(masonryPane);
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);

    content.getItems().add(scrollPane);
  }

  @Override
  public void addNode(CarMakeModel model) {
    masonryPane.getChildren().add(NodeProvider.createCarMakeMenu(model.getName(), null, CELL_WIDTH, CELL_HEIGHT));
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
