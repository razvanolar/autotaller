package com.autotaller.app.components.app_view.utils;

import com.autotaller.app.components.app_view.admin_view.util.FilterPanelView;
import com.autotaller.app.components.utils.filter_views.DefaultCarFilterView;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.FuelModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.model.utils.YearsRange;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.filters.SystemModelsFilter;
import com.autotaller.app.utils.filters.car_filters.car_model_filters.*;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by razvanolar on 17.06.2017
 */
public class DefaultCarController implements Controller<DefaultCarController.IDefaultCarView> {

  public interface IDefaultCarView extends View {
    TableView<CarModel> getCarsTable();
    FilterPanelView<Integer> getYearFilterPane();
    DefaultCarFilterView getFilterView();
    void showFilterPane();
    void hideFilterPane();
  }

  private CarModelYearsFilter carModelYearsFilter;
  private CarModelMakeFilter carModelMakeFilter;
  private CarModelTypeFilter carModelTypeFilter;
  private CarModelNameFilter carModelNameFilter;
  private CarModelKWFilter carModelKWFilter;
  private CarModelCapacityFilter carModelCapacityFilter;
  private CarModelCilindersFilter carModelCilindersFilter;

  private CarModelFuelFilter carModelFuelFilter;
  private IDefaultCarView view;
  private List<CarModel> cars;

  private SystemModelsDTO modelsDTO;
  private CarMakeModel allMakes = new CarMakeModel(-1, "--Toate--");
  private CarTypeModel allTypes = new CarTypeModel(-1, allMakes, "--Toate--", null, null);
  private FuelModel allFuels = new FuelModel(-1, "--Toate--");

  @Override
  public void bind(IDefaultCarView view) {
    this.view = view;

    carModelYearsFilter = new CarModelYearsFilter();
    carModelMakeFilter = new CarModelMakeFilter();
    carModelTypeFilter = new CarModelTypeFilter();
    carModelNameFilter = new CarModelNameFilter();
    carModelKWFilter = new CarModelKWFilter();
    carModelCapacityFilter = new CarModelCapacityFilter();
    carModelCilindersFilter = new CarModelCilindersFilter();
    carModelFuelFilter = new CarModelFuelFilter();

    carModelYearsFilter.getFields().addListener((ListChangeListener<Integer>) c -> filter());
    view.getYearFilterPane().setFilterObject(carModelYearsFilter);

    // car make combo listener
    view.getFilterView().getMakeCombo().valueProperty().addListener((observable, oldValue, newValue) -> {
      populateCarModelsCombo(newValue);
      carModelMakeFilter.setCarMake(newValue);
    });

    // car type combo listener
    view.getFilterView().getTypeCombo().valueProperty().addListener((observable, oldValue, newValue) -> carModelTypeFilter.setCarType(newValue));

    // car name field listener
    view.getFilterView().getNameField().textProperty().addListener((observable, oldValue, newValue) -> carModelNameFilter.setName(newValue));

    // car kw value listeners
    view.getFilterView().getKwFromSpinner().valueProperty().addListener((observable, oldValue, newValue) -> carModelKWFilter.setFrom(newValue));
    view.getFilterView().getKwToSpinner().valueProperty().addListener((observable, oldValue, newValue) -> carModelKWFilter.setTo(newValue));

    // car capacity value listeners
    view.getFilterView().getCapacityFromSpinner().valueProperty().addListener((observable, oldValue, newValue) -> carModelCapacityFilter.setFrom(newValue));
    view.getFilterView().getCapacityToSpinner().valueProperty().addListener((observable, oldValue, newValue) -> carModelCapacityFilter.setTo(newValue));

    // car cylinders value listener
    view.getFilterView().getCilindersSpinner().valueProperty().addListener((observable, oldValue, newValue) -> carModelCilindersFilter.setCilinders(newValue));

    // car fuel combo listener
    view.getFilterView().getFuelCombo().valueProperty().addListener((observable, oldValue, newValue) -> carModelFuelFilter.setFuel(newValue));

    // filter cars listeners
    view.getFilterView().getSearchButton().setOnAction(event -> filter());

    view.getFilterView().getResetFiltersButton().setOnAction(event -> {
      DefaultCarFilterView filterView = view.getFilterView();
      filterView.getMakeCombo().setValue(allMakes);
      filterView.getNameField().setText("");
      filterView.getKwFromSpinner().getValueFactory().setValue(0);
      filterView.getKwToSpinner().getValueFactory().setValue(0);
      filterView.getCapacityFromSpinner().getValueFactory().setValue(0);
      filterView.getCapacityToSpinner().getValueFactory().setValue(0);
      filterView.getCilindersSpinner().getValueFactory().setValue(0);
      filterView.getFuelCombo().setValue(allFuels);
      filter();
    });
  }

  public void setCars(List<CarModel> cars) {
    this.cars = cars;
    view.getYearFilterPane().showFilterPanels(getYearRange().toList());
  }

  public void setSystemModels(SystemModelsDTO models, CarTypeModel carTypeLock) {
    setSystemModels(models);
    view.getFilterView().getMakeCombo().setValue(carTypeLock.getCarMake());
    view.getFilterView().getTypeCombo().setValue(carTypeLock);
    view.getFilterView().getMakeCombo().setDisable(true);
    view.getFilterView().getTypeCombo().setDisable(true);
  }

  public void setSystemModels(SystemModelsDTO models) {
    this.modelsDTO = models;

    List<CarMakeModel> carMakes = models.getCarMakes();
    if (carMakes != null && !carMakes.isEmpty()) {
      ComboBox<CarMakeModel> makesCombo = view.getFilterView().getMakeCombo();
      makesCombo.getItems().add(allMakes);
      makesCombo.getItems().addAll(carMakes);
      makesCombo.setValue(allMakes);
      populateCarModelsCombo(allMakes);
    }

    List<FuelModel> fuels = models.getFuels();
    if (fuels != null && !fuels.isEmpty()) {
      ComboBox<FuelModel> fuelCombo = view.getFilterView().getFuelCombo();
      fuelCombo.getItems().add(allFuels);
      fuelCombo.getItems().addAll(fuels);
      fuelCombo.setValue(allFuels);
    }
  }

  public CarModel getSelectedCar() {
    return view.getCarsTable().getSelectionModel().getSelectedItem();
  }

  private YearsRange getYearRange() {
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    int now = LocalDate.now().getYear();

    if (cars != null && !cars.isEmpty()) {
      for (CarModel car : cars) {
        int from = car.getFrom() != null ? car.getFrom().getYear() : 0;
        int to = car.getTo() != null ? car.getTo().getYear() : 0;
        if (from != 0 && min > from)
          min = from;
        if (to != 0 && max < to)
          max = to;
        else
          max = now;
      }
    }

    if (min == Integer.MAX_VALUE)
      min = now;
    if (max == Integer.MIN_VALUE)
      max = now;
    return new YearsRange(min, max);
  }

  private void load(List<CarModel> carList) {
    ObservableList<CarModel> items = view.getCarsTable().getItems();
    items.clear();
    if (carList != null)
      items.addAll(carList);
  }

  private void filter() {
    load(SystemModelsFilter.filterCars(cars, carModelYearsFilter, carModelMakeFilter, carModelTypeFilter,
            carModelNameFilter, carModelKWFilter, carModelCapacityFilter, carModelCilindersFilter, carModelFuelFilter));
  }

  private void populateCarModelsCombo(CarMakeModel carMake) {
    if (carMake != null && modelsDTO != null) {
      List<CarTypeModel> carTypes;
      if (carMake.getId() == -1) {
        carTypes = modelsDTO.getCarTypes();
      } else {
        carTypes = modelsDTO.getCarTypesByMake(carMake);
      }
      ComboBox<CarTypeModel> typesCombo = view.getFilterView().getTypeCombo();
      ObservableList<CarTypeModel> items = typesCombo.getItems();
      items.clear();
      items.add(allTypes);
      if (!carTypes.isEmpty()) {
        items.addAll(carTypes);
        typesCombo.setValue(carTypes.get(0));
      }
      typesCombo.setValue(allTypes);
    }
  }
}
