package com.autotaller.app.components.app_view.cars_view.search_car_components_view;

import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.TableView;

/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarComponentsView extends IterableView implements SearchCarComponentsController.ISearchCarComponentsView {

  private TableView<CarComponentModel> carComponentsTable;

  public SearchCarComponentsView() {
    init();
  }

  private void init() {
    carComponentsTable = NodeProvider.createCarComponentTable(false);

    borderPane.setCenter(carComponentsTable);
  }

  public TableView<CarComponentModel> getCarComponentsTable() {
    return carComponentsTable;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
