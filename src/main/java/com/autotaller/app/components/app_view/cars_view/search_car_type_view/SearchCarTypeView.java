package com.autotaller.app.components.app_view.cars_view.search_car_type_view;

import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;


/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarTypeView extends IterableView implements SearchCarTypeController.ISearchCarTypeView {

  private TableView<CarTypeModel> carTypeTable;
  private Button continueButton;

  public SearchCarTypeView() {
    init();
  }

  private void init() {
    continueButton = NodeProvider.createToolbarButton("Continua", null);
    toolBar.getItems().addAll(new Separator(), new FillToolItem(), continueButton);

    carTypeTable = NodeProvider.createCarModelTable();
    borderPane.setCenter(carTypeTable);
  }

  public TableView<CarTypeModel> getCarTypeTable() {
    return carTypeTable;
  }

  public Button getContinueButton() {
    return continueButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
