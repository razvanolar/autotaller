package com.autotaller.app.components.app_view.notifications_view.utils;

import com.autotaller.app.model.*;
import com.autotaller.app.model.notifications.DetailedSellModel;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Created by razvanolar on 08.06.2017
 */
public class DetailedSellModelView implements View {

  private GridPane mainContainer;
  private DetailedSellModel sellModel;

  public DetailedSellModelView(DetailedSellModel sellModel) {
    this.sellModel = sellModel;
    init();
  }

  private void init() {
    mainContainer = NodeProvider.createGridPane(Pos.CENTER, 10, 5);
    int row = 0;
    VBox sellTitlePane = NodeProvider.createTitlePane("Vanzare", null, StyleProvider.WHITE_BACKGROUND_CLASS);
    VBox componentDetailsTitlePane = NodeProvider.createTitlePane("Detalii Componenta", null, StyleProvider.WHITE_BACKGROUND_CLASS);
    VBox carTitlePane = NodeProvider.createTitlePane("Detalii Masina: ", null, StyleProvider.WHITE_BACKGROUND_CLASS);
    sellTitlePane.setMinWidth(NodeProvider.DEFAULT_FIELD_WIDTH);
    componentDetailsTitlePane.setMinWidth(NodeProvider.DEFAULT_FIELD_WIDTH);
    carTitlePane.setMinWidth(NodeProvider.DEFAULT_FIELD_WIDTH);

    mainContainer.add(sellTitlePane, 0, row++, 2, 1);
    mainContainer.add(NodeProvider.createFormTextLabel("Bucati Vandute"), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(String.valueOf(sellModel.getSoldPieces())), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Pret Vanzare: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(String.valueOf(sellModel.getSoldPrice())), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Data Vanzare: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(sellModel.getSoldDate().toString()), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Vandut de: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(sellModel.getUserName()), 1, row++);
    mainContainer.add(componentDetailsTitlePane, 0, row++, 2, 1);
    if (sellModel.getCarSubkitModel() != null) {
      CarSubkitModel subkit = sellModel.getCarSubkitModel();
      if (subkit.getCarKit() != null) {
        CarKitModel kit = subkit.getCarKit();
        if (kit.getKitCategory() != null) {
          CarKitCategoryModel category = kit.getKitCategory();
          mainContainer.add(NodeProvider.createFormTextLabel("Categorie: "), 0, row);
          mainContainer.add(NodeProvider.createFormTextLabel(category.getName()), 1, row++);
        }
        mainContainer.add(NodeProvider.createFormTextLabel("Ansamblu: "), 0, row);
        mainContainer.add(NodeProvider.createFormTextLabel(kit.getName()), 1, row++);
      }
      mainContainer.add(NodeProvider.createFormTextLabel("Sub Ansamblu: "), 0, row);
      mainContainer.add(NodeProvider.createFormTextLabel(subkit.getName()), 1, row++);
    }
    mainContainer.add(NodeProvider.createFormTextLabel("Nume Componenta: "), 0, row);
    CarComponentModel component = sellModel.getCarComponent();
    mainContainer.add(NodeProvider.createFormTextLabel(component.getName()), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Cod: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(component.getCode()), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Stoc: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(component.getStock().getName()), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Grad de uzura: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(component.getUsageState().getName()), 1, row++);
    mainContainer.add(NodeProvider.createFormTextLabel("Pret: "), 0, row);
    mainContainer.add(NodeProvider.createFormTextLabel(String.valueOf(component.getPrice())), 1, row++);
    mainContainer.add(carTitlePane, 0, row++ ,2 ,1);
    if (sellModel.getCar() != null) {
      CarModel car = sellModel.getCar();
      if (car.getCarType() != null) {
        CarTypeModel carType = car.getCarType();
        if (carType.getCarMake() != null) {
          CarMakeModel carMake = carType.getCarMake();
          mainContainer.add(NodeProvider.createFormTextLabel("Marca: "), 0, row);
          mainContainer.add(NodeProvider.createFormTextLabel(carMake.getName()), 1, row++);
        }
        mainContainer.add(NodeProvider.createFormTextLabel("Model: "), 0, row);
        mainContainer.add(NodeProvider.createFormTextLabel(carType.getName()), 1, row++);
      }
      mainContainer.add(NodeProvider.createFormTextLabel("Motorizare: "), 0, row);
      mainContainer.add(NodeProvider.createFormTextLabel(car.getName()), 1, row);
    }

    mainContainer.getStyleClass().add(StyleProvider.WHITE_BACKGROUND_CLASS);
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
