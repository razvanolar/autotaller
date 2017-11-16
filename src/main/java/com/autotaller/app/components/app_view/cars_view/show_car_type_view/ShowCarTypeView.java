package com.autotaller.app.components.app_view.cars_view.show_car_type_view;

import com.autotaller.app.components.app_view.admin_view.util.FilterPanelView;
import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * Created by razvanolar on 02.06.2017
 */
public class ShowCarTypeView extends IterableView implements ShowCarTypeController.IShowCarTypeView {

  private FilterPanelView<Integer> yearFilterPane;
  private TableView<CarTypeModel> carTypeTable;
  private TextField searchNameField;
  private Button searchNameButton;
  private TextField searchFrameField;
  private Button searchFrameButton;
  private Button continueButton;

  public ShowCarTypeView() {
    init();
  }

  private void init() {
    searchNameField = NodeProvider.createTextField();
    searchNameButton = NodeProvider.createButton("Cauta");
    searchFrameField = NodeProvider.createTextField();
    searchFrameButton = NodeProvider.createButton("Cauta");
    continueButton = NodeProvider.createToolbarButton("Continua", null);
    toolBar.getItems().addAll(new Separator(), new FillToolItem(), continueButton);

    carTypeTable = NodeProvider.createCarModelTable();
    yearFilterPane = new FilterPanelView<>(100);

    HBox filterFieldsContainer = NodeProvider.createHBox(Pos.CENTER, 10);
    filterFieldsContainer.getChildren().addAll(
            NodeProvider.createFormTextLabel("Nume Model: "), searchNameField, searchNameButton,
            NodeProvider.createFormTextLabel("Serie Sasiu: "), searchFrameField, searchFrameButton
    );

    VBox filterPane = NodeProvider.createVBox(Pos.CENTER, 5, yearFilterPane.asNode(), filterFieldsContainer);
    filterPane.setPadding(new Insets(5, 0, 5, 0));

    BorderPane container = NodeProvider.createBorderPane();
    container.setCenter(carTypeTable);
    container.setTop(filterPane);

    borderPane.setCenter(container);
  }

  public TableView<CarTypeModel> getCarTypeTable() {
    return carTypeTable;
  }

  public FilterPanelView<Integer> getYearFilterPane() {
    return yearFilterPane;
  }

  public TextField getSearchNameField() {
    return searchNameField;
  }

  public Button getSearchNameButton() {
    return searchNameButton;
  }

  public TextField getSearchFrameField() {
    return searchFrameField;
  }

  public Button getSearchFrameButton() {
    return searchFrameButton;
  }

  public Button getContinueButton() {
    return continueButton;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
