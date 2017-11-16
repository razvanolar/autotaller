package com.autotaller.app.components.app_view.search_cars_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.utils.extensions.BodyTypeCheckBox;
import com.autotaller.app.components.app_view.utils.extensions.FuelCheckBox;
import com.autotaller.app.components.app_view.utils.extensions.WheelTypeCheckBox;
import com.autotaller.app.components.utils.SimpleDialog;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.InjectCarsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.SearchCarEvent;
import com.autotaller.app.events.app_view.search_views.InjectSystemModelsEvent;
import com.autotaller.app.events.app_view.search_views.InjectSystemModelsEventHandler;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.model.*;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class SearchCarsMainController implements Controller<SearchCarsMainController.ISearchCarsMainView> {

  public interface ISearchCarsMainView extends View {
    void injectFuelModels(List<FuelModel> fuels);
    void injectBodyTypeModels(List<CarBodyTypeModel> bodyTypes);
    ComboBox<CarMakeModel> getCarMakeComboBox();
    ComboBox<CarTypeModel> getCarTypeComboBox();
    TextField getParkNumberTextField();
    TextField getProductionYearTextField();
    WheelTypeCheckBox getLeftWheelCheckBox();
    WheelTypeCheckBox getRightWheelCheckBox();
    TextField getPowerFromTextField();
    TextField getPowerToTextField();
    RadioButton getKwPowerRadio();
    TextField getKmFromTextField();
    TextField getKmToTextField();
    TextField getCapacityFromTextField();
    TextField getCapacityToTextField();
    TextField getPriceFromTextField();
    TextField getPriceToTextField();
    List<FuelCheckBox> getFuelCheckBoxList();
    List<BodyTypeCheckBox> getBodyTypeCheckBoxList();
    Button getSearchButton();
  }

  private static String PARK_NUMBER_ERROR_MESSAGE = "Numarul de parc nu este introdus corect!";
  private static String PRODUCTION_YEAR_ERROR_MESSAGE = "Anul de fabricatie trebuie sa fie un numar pozitiv!";
  private static String POWER_ERROR_MESSAGE = "Campul 'Putere' nu este completat corect!";
  private static String KILOMETER_ERROR_MESSAGE = "Campul 'Kilometrii' nu este completat corect!";
  private static String CAPACITY_ERROR_MESSAGE = "Campul 'Capacitate' nu este completat corect!";
  private static String PRICE_ERROR_MESSAGE = "Campul 'Pret' nu este completat corect!";

  private CarMakeModel defaultCarMake = new CarMakeModel(-1, "--Toate--");
  private CarTypeModel defaultCarType = new CarTypeModel(-1, null, "--Toate--", null, null);
  private SystemModelsDTO systemModels;

  private ISearchCarsMainView view;

  @Override
  public void bind(ISearchCarsMainView view) {
    this.view = view;

    view.getSearchButton().setOnAction(event -> {
      String parkNumberValue = getDefaultIfMissingValue(view.getParkNumberTextField().getText());
      String productionYearValue = getDefaultIfMissingValue(view.getProductionYearTextField().getText());
      String powerFromValue = getDefaultIfMissingValue(view.getPowerFromTextField().getText());
      String powerToValue = getDefaultIfMissingValue(view.getPowerToTextField().getText());
      String kmFromValue = getDefaultIfMissingValue(view.getKmFromTextField().getText());
      String kmToValue = getDefaultIfMissingValue(view.getKmToTextField().getText());
      String capacityFromValue = getDefaultIfMissingValue(view.getCapacityFromTextField().getText());
      String capacityToValue = getDefaultIfMissingValue(view.getCapacityToTextField().getText());
      String priceFromValue = getDefaultIfMissingValue(view.getPriceFromTextField().getText());
      String priceToValue = getDefaultIfMissingValue(view.getPriceToTextField().getText());
      if (!validateNumber(parkNumberValue, PARK_NUMBER_ERROR_MESSAGE) ||
              !validateNumber(productionYearValue, PRODUCTION_YEAR_ERROR_MESSAGE) ||
              !validateNumber(powerFromValue, POWER_ERROR_MESSAGE) ||
              !validateNumber(powerToValue, POWER_ERROR_MESSAGE) ||
              !validateNumber(kmFromValue, KILOMETER_ERROR_MESSAGE) ||
              !validateNumber(kmToValue, KILOMETER_ERROR_MESSAGE) ||
              !validateNumber(capacityFromValue, CAPACITY_ERROR_MESSAGE) ||
              !validateNumber(capacityToValue, CAPACITY_ERROR_MESSAGE) ||
              !validateNumber(priceFromValue, PRICE_ERROR_MESSAGE) ||
              !validateNumber(priceToValue, PRICE_ERROR_MESSAGE)) {
        return;
      }
      CarMakeModel carMake = view.getCarMakeComboBox().getValue();
      CarTypeModel carType = carMake.equals(defaultCarMake) ? null : view.getCarTypeComboBox().getValue();
      carMake = carMake.equals(defaultCarMake) ? null : carMake;
      carType = carType != null && carType.equals(defaultCarType) ? null : carType;
      List<CarWheelSideType> wheelList = collectWheelList();
      List<FuelModel> fuelList = collectFuelList();
      List<CarBodyTypeModel> bodyTypeList = collectBodyTypeList();

      SearchCarModel searchCarModel = new SearchCarModel(carMake, carType, Integer.parseInt(parkNumberValue),
              Integer.parseInt(productionYearValue), wheelList, Integer.parseInt(powerFromValue),
              Integer.parseInt(powerToValue), view.getKwPowerRadio().isSelected(), Integer.parseInt(kmFromValue),
              Integer.parseInt(kmToValue), Integer.parseInt(capacityFromValue), Integer.parseInt(capacityToValue),
              Integer.parseInt(priceFromValue), Integer.parseInt(priceToValue), fuelList, bodyTypeList);

      EventBus.fireEvent(new SearchCarEvent(searchCarModel, cars -> {
        Component component = ComponentFactory.createComponent(ComponentType.SHOW_CAR_VIEW);
        if (component != null) {
          EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.SHOW_CAR_VIEW.getTitle()));
          EventBus.fireEvent(new InjectCarsEvent(cars));
          EventBus.fireEvent(new BindLastViewEvent());
        }
      }));
    });

    view.getCarMakeComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.equals(defaultCarMake)) {
        view.getCarTypeComboBox().getItems().clear();
        view.getCarTypeComboBox().setDisable(true);
      } else {
        view.getCarTypeComboBox().setDisable(false);
        ObservableList<CarTypeModel> carTypes = view.getCarTypeComboBox().getItems();
        carTypes.clear();
        carTypes.add(defaultCarType);
        carTypes.addAll(systemModels.getCarTypesByMake(newValue));
        view.getCarTypeComboBox().setValue(defaultCarType);
      }
    });

    EventBus.addHandler(InjectSystemModelsEvent.TYPE, (InjectSystemModelsEventHandler) event -> {
      this.systemModels = event.getSystemModels();
      view.injectFuelModels(systemModels.getFuels());
      view.injectBodyTypeModels(systemModels.getBodyTypes());

      // load car makes
      ObservableList<CarMakeModel> carMakes = view.getCarMakeComboBox().getItems();
      carMakes.add(defaultCarMake);
      carMakes.addAll(systemModels.getCarMakes());
      view.getCarMakeComboBox().setValue(defaultCarMake);

      view.getCarTypeComboBox().setDisable(true);
    }, true);
  }

  private List<CarWheelSideType> collectWheelList() {
    List<CarWheelSideType> list = new ArrayList<>();
    if (view.getLeftWheelCheckBox().isSelected())
      list.add(view.getLeftWheelCheckBox().getWheelType());
    if (view.getRightWheelCheckBox().isSelected())
      list.add(view.getRightWheelCheckBox().getWheelType());
    return list;
  }

  private List<FuelModel> collectFuelList() {
    List<FuelModel> list = new ArrayList<>();
    for (FuelCheckBox checkBox : view.getFuelCheckBoxList()) {
      if (checkBox.isSelected())
        list.add(checkBox.getFuel());
    }
    return list;
  }

  private List<CarBodyTypeModel> collectBodyTypeList() {
    List<CarBodyTypeModel> list = new ArrayList<>();
    for (BodyTypeCheckBox checkBox : view.getBodyTypeCheckBoxList()) {
      if (checkBox.isSelected())
        list.add(checkBox.getBodyType());
    }
    return list;
  }

  private String getDefaultIfMissingValue(String value) {
    return StringValidator.isNullOrEmpty(value) ? "0" : value;
  }

  private boolean validateNumber(String value, String errorMessage) {
    if (!StringValidator.isPositiveInteger(value)) {
      EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", errorMessage)));
      return false;
    }
    return true;
  }
}