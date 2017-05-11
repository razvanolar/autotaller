package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.utils.NotificationsUtil;
import com.autotaller.app.components.utils.filter_views.DefaultCarFilterView;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.GetAllCarDefinedModelsEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarsEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.AddCarEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.AddCarEventHandler;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.FuelModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.ComponentFactory;
import com.autotaller.app.utils.filters.ModelFilter;
import com.autotaller.app.utils.filters.car_model_filters.*;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;

import java.util.List;

/**
 * Created by razvanolar on 02.05.2017
 */
public class AdminRegisterCarController implements Controller<AdminRegisterCarController.IAdminRegisterCarView> {

  private CarModelYearFilter carModelYearFilter;
  private CarModelMakeFilter carModelMakeFilter;
  private CarModelTypeFilter carModelTypeFilter;
  private CarModelNameFilter carModelNameFilter;
  private CarModelKWFilter carModelKWFilter;
  private CarModelCapacityFilter carModelCapacityFilter;
  private CarModelCilindersFilter carModelCilindersFilter;
  private CarModelFuelFilter carModelFuelFilter;

  public interface IAdminRegisterCarView extends View {
    TableView<CarModel> getCarTable();
    DefaultCarFilterView getFilterView();
    Button getAddCarButton();
    Button getEditCarButton();
    Button getDeleteCarButton();
    ToggleButton getShowFilterCarButton();
    Button getCarDetailsButton();
    void showFilterPane();
    void hideFilterPane();
  }

  private IAdminRegisterCarView view;
  private Repository repository;
  private List<CarModel> allCars;
  private SystemModelsDTO modelsDTO;

  private CarMakeModel allMakes = new CarMakeModel(-1, "--Toate--");
  private CarTypeModel allTypes = new CarTypeModel(-1, allMakes, "--Toate--", null, null, null);
  private FuelModel allFuels = new FuelModel(-1, "--Toate--");

  @Override
  public void bind(IAdminRegisterCarView view) {
    this.view = view;

    carModelYearFilter = new CarModelYearFilter();
    carModelMakeFilter = new CarModelMakeFilter();
    carModelTypeFilter = new CarModelTypeFilter();
    carModelNameFilter = new CarModelNameFilter();
    carModelKWFilter = new CarModelKWFilter();
    carModelCapacityFilter = new CarModelCapacityFilter();
    carModelCilindersFilter = new CarModelCilindersFilter();
    carModelFuelFilter = new CarModelFuelFilter();

    view.getFilterView().getYearsPanelView().setFilterObject(carModelYearFilter);
    carModelYearFilter.getYears().addListener((ListChangeListener<Integer>) c -> filterCars());

    view.getAddCarButton().setOnAction(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_SAVE_CAR_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView()));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    });

    view.getShowFilterCarButton().setOnAction(event -> {
      if (view.getShowFilterCarButton().isSelected()) {
        view.showFilterPane();
      } else {
        view.hideFilterPane();
      }
    });

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
    view.getFilterView().getSearchButton().setOnAction(event -> filterCars());

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
      filterCars();
    });

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> {
      this.repository = event.getRepository();
      initHandlers();
    });

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> {
      loadCars();
      EventBus.fireEvent(new GetAllCarDefinedModelsEvent(models -> {
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
      }));
    }, true);
  }

  private void initHandlers() {
    EventBus.addHandler(AddCarEvent.TYPE, (AddCarEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Adaugare Masina"));
        Thread thread = new Thread(() -> {
          try {
            repository.addCar(event.getCar(), event.getCarComponents());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              NotificationsUtil.showInfoNotification("Notificare", "Masina a fost adaugata cu succes", 3);
              loadCars();
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
            NotificationsUtil.showErrorNotification("Eroare", "Masina nu a putut fi inregistrata", -1);
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
        NotificationsUtil.showErrorNotification("Eroare", "Masina nu a putut fi inregistrata", -1);
      }
    });
  }

  private void filterCars() {
    List<CarModel> cars = ModelFilter.filterCars(allCars, carModelYearFilter, carModelMakeFilter, carModelTypeFilter,
            carModelNameFilter, carModelKWFilter, carModelCapacityFilter, carModelCilindersFilter, carModelFuelFilter);
    loadCars(cars);
  }

  private void loadCars() {
    EventBus.fireEvent(new GetCarsEvent(cars -> {
      this.allCars = cars;
      loadCars(cars);
    }));
  }

  private void loadCars(List<CarModel> cars) {
    ObservableList<CarModel> items = view.getCarTable().getItems();
    items.clear();
    items.addAll(cars);
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
