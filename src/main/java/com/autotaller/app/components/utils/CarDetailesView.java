package com.autotaller.app.components.utils;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.OpenCarImageGalleryEvent;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.utils.StringValidator;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.NodeProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Created by razvanolar on 16.06.2017
 */
public class CarDetailesView implements View {

  private GridPane pane;
  private CarModel car;
  private Button carGalleryButton;

  public CarDetailesView(CarModel car) {
    this.car = car;
    init();
    addHandlers();
  }

  private void init() {
    carGalleryButton = NodeProvider.createButton("Spre Galerie");
    pane = NodeProvider.createGridPane(Pos.CENTER, 5, 8);
    pane.getStyleClass().add(StyleProvider.WHITE_BACKGROUND_CLASS);
    if (car == null)
      return;
    int row = 0;
    pane.add(NodeProvider.createFormTextLabel("ID: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(String.valueOf(car.getId())), 1, row++);
    if (car.getCarType() != null) {
      if (car.getCarType().getCarMake() != null) {
        pane.add(NodeProvider.createFormTextLabel("Marca: "), 0, row);
        pane.add(NodeProvider.createFormTextLabel(car.getCarType().getCarMake().getName()), 1, row++);
      }
      pane.add(NodeProvider.createFormTextLabel("Model: "), 0, row);
      pane.add(NodeProvider.createFormTextLabel(car.getCarType().getName()), 1, row++);
    }
    pane.add(NodeProvider.createFormTextLabel("Motorizare: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(car.getName()), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("Caroserie: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(car.getBodyType().getName()), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("De la: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(car.getFrom() != null ? car.getFrom().toString() : "---"), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("Pana la: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(car.getTo() != null ? car.getTo().toString() : "---"), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("An Fabricatie: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(car.getProductionYear() != null ? car.getProductionYear().toString() : "---"), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("KM: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(String.valueOf(car.getParkNumber())), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("KW: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(String.valueOf(car.getKw())), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("CP: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(String.valueOf(car.getHp())), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("Capacitate Cilindrica: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(String.valueOf(car.getCapacity())), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("Cilindrii: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(String.valueOf(car.getCilinders())), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("Combustibil: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(car.getFuel() != null ? car.getFuel().getName() : "---"), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("Cod Culoare: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(!StringValidator.isNullOrEmpty(car.getColorCode())? car.getColorCode() : "---"), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("Pret Achizitie: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(String.valueOf(car.getPrice())), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("Volan: "), 0, row);
    pane.add(NodeProvider.createFormTextLabel(car.getWheelSide().getValue()), 1, row++);
    pane.add(NodeProvider.createFormTextLabel("Imagini: "), 0, row);
    pane.add(carGalleryButton, 1, row);
  }

  private void addHandlers() {
    carGalleryButton.setOnAction(event -> {
      if (car == null)
        return;
      EventBus.fireEvent(new OpenCarImageGalleryEvent(car.getId()));
    });
  }

  @Override
  public Node asNode() {
    return pane;
  }
}
