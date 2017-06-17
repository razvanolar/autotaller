package com.autotaller.app.components.app_view.cars_view.search_car_type_view;

import com.autotaller.app.components.app_view.admin_view.util.FilterPanelView;
import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;


/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarTypeView extends IterableView implements SearchCarTypeController.ISearchCarTypeView {

  private BorderPane container;
  private FilterPanelView<Integer> yearFilterPane;
  private TableView<CarTypeModel> carTypeTable;
  private Button continueButton;

  public SearchCarTypeView() {
    init();
  }

  private void init() {
    continueButton = NodeProvider.createToolbarButton("Continua", null);
    toolBar.getItems().addAll(new Separator(), new FillToolItem(), continueButton);

    carTypeTable = NodeProvider.createCarModelTable();
    yearFilterPane = new FilterPanelView<>(100);

    container = NodeProvider.createBorderPane();
    container.setCenter(carTypeTable);
    container.setTop(yearFilterPane.asNode());

    borderPane.setCenter(container);
  }

  public TableView<CarTypeModel> getCarTypeTable() {
    return carTypeTable;
  }

  public FilterPanelView<Integer> getYearFilterPane() {
    return yearFilterPane;
  }

  public Button getContinueButton() {
    return continueButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
