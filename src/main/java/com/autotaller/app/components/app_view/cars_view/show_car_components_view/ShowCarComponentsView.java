package com.autotaller.app.components.app_view.cars_view.show_car_components_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;

/**
 * Created by razvanolar on 02.06.2017
 */
public class ShowCarComponentsView extends IterableView implements ShowCarComponentsController.IShowCarComponentsView {

  private TableView<CarComponentModel> carComponentsTable;
  private Button sellComponentButton;

  public ShowCarComponentsView() {
    init();
  }

  private void init() {
    sellComponentButton = NodeProvider.createToolbarButton("Vanzare componenta", null);
    toolBar.getItems().addAll(new Separator(), sellComponentButton);

    carComponentsTable = NodeProvider.createCarComponentTable(false);
    borderPane.setCenter(carComponentsTable);
  }

  public TableView<CarComponentModel> getCarComponentsTable() {
    return carComponentsTable;
  }

  public Button getSellComponentButton() {
    return sellComponentButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
