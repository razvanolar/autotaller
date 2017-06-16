package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.admin_register_car_view.utils.AdminCarTableViewContextMenu;
import com.autotaller.app.components.utils.CarDetailesView;
import com.autotaller.app.components.utils.NodeDialog;
import com.autotaller.app.components.utils.NotificationsUtil;
import com.autotaller.app.components.utils.YesNoDialog;
import com.autotaller.app.components.utils.filter_views.DefaultCarFilterView;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.OpenCarImageGalleryEvent;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetAllSystemDefinedModelsEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarsEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.InjectCarInformationEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.ShowSaveCarComponentsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.AddCarEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.AddCarEventHandler;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.events.view_stack.BackToPreviousViewEvent;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.FuelModel;
import com.autotaller.app.model.utils.SaveCarResult;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.repository.utils.CarStatus;
import com.autotaller.app.repository.utils.ImageStatus;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.callbacks.EmptyCallback;
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

  private CarModelYearsFilter carModelYearsFilter;
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
    Button getComponentsButton();
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

    carModelYearsFilter = new CarModelYearsFilter();
    carModelMakeFilter = new CarModelMakeFilter();
    carModelTypeFilter = new CarModelTypeFilter();
    carModelNameFilter = new CarModelNameFilter();
    carModelKWFilter = new CarModelKWFilter();
    carModelCapacityFilter = new CarModelCapacityFilter();
    carModelCilindersFilter = new CarModelCilindersFilter();
    carModelFuelFilter = new CarModelFuelFilter();

    view.getFilterView().getFilterPanelView().setFilterObject(carModelYearsFilter);
    carModelYearsFilter.getFields().addListener((ListChangeListener<Integer>) c -> filterCars());

    view.getAddCarButton().setOnAction(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_SAVE_CAR_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.ADMIN_SAVE_CAR_VIEW.getTitle()));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    });

    view.getComponentsButton().setOnAction(event -> {
      CarModel selectedCar = view.getCarTable().getSelectionModel().getSelectedItem();
      if (selectedCar == null)
        return;
      showCarComponentsView(selectedCar, null);
    });

    view.getShowFilterCarButton().setOnAction(event -> {
      if (view.getShowFilterCarButton().isSelected()) {
        view.showFilterPane();
      } else {
        view.hideFilterPane();
      }
    });

    view.getCarDetailsButton().setOnAction(event -> {
      CarModel selectedCar = view.getCarTable().getSelectionModel().getSelectedItem();
      if (selectedCar == null)
        return;
      CarDetailesView detailesView = new CarDetailesView(selectedCar);
      EventBus.fireEvent(new ShowDialogEvent(new NodeDialog("Detalii Masina", "Ok", detailesView.asNode(), false)));
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

    // set table context menu
    AdminCarTableViewContextMenu contextMenu = new AdminCarTableViewContextMenu();
    view.getCarTable().setContextMenu(contextMenu.getContextMenu());
    contextMenu.getGalleryMenuItem().setOnAction(event -> {
      CarModel selectedCar = view.getCarTable().getSelectionModel().getSelectedItem();
      if (selectedCar != null)
        EventBus.fireEvent(new OpenCarImageGalleryEvent(selectedCar.getId()));
    });

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> {
      this.repository = event.getRepository();
      initHandlers();
    }, true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> {
      load(null);
      EventBus.fireEvent(new GetAllSystemDefinedModelsEvent(models -> {
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

  private void showCarComponentsView(CarModel car, EmptyCallback bindCallback) {
    Component component = ComponentFactory.createComponent(ComponentType.ADMIN_COMPONENTS_VIEW);
    if (component != null) {
      String title = ComponentType.ADMIN_COMPONENTS_VIEW.getTitle() + " (" + car.getName() + ")";
      EventBus.fireEvent(new AddViewToStackEvent(component.getView(), title));
      EventBus.fireEvent(new InjectCarInformationEvent(car.getId()));
      EventBus.fireEvent(new InjectRepoToAdminEvent(repository));
      EventBus.fireEvent(new BindLastViewEvent(bindCallback));
    }
  }

  private void initHandlers() {
    EventBus.addHandler(AddCarEvent.TYPE, (AddCarEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Adaugare Masina"));
        Thread thread = new Thread(() -> {
          try {
            String carName = event.getCar().getName();
            SaveCarResult result = repository.addCar(event.getCar(), event.getImages());
            CarModel car = result.getCarModel();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              CarStatus carStatus = result.getCarStatus();
              ImageStatus imageStatus = result.getImageStatus();
              if (carStatus != null) {
                if (carStatus == CarStatus.SUCCESS_SAVE)
                  NotificationsUtil.showInfoNotification("Notificare", carStatus.getText(carName), 10);
                else
                  NotificationsUtil.showWarningNotification("Atentie", carStatus.getText(carName), 10);
              }
              if (imageStatus != null) {
                if (imageStatus == ImageStatus.SUCCESS_IMAGE_SAVE)
                  NotificationsUtil.showInfoNotification("Notificare", imageStatus.getText(carName), 10);
                else
                  NotificationsUtil.showWarningNotification("Atentie", imageStatus.getText(carName), 10);
              }
              EventBus.fireEvent(new BackToPreviousViewEvent());
              if (car != null)
                showAddComponentsDialogAfterCarRegistration(car);
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              NotificationsUtil.showErrorNotification("Eroare", "Masina nu a putut fi inregistrata", -1);
            });
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
        NotificationsUtil.showErrorNotification("Eroare", "Masina nu a putut fi inregistrata", -1);
      }
    }, true);
  }

  private void showAddComponentsDialogAfterCarRegistration(CarModel car) {
    YesNoDialog dialog = new YesNoDialog("Adaugare componente", "Doriti sa adaugati componente pentru " + car.getName() + "?");
    EventBus.fireEvent(new ShowDialogEvent(dialog));
    dialog.getYesButton().setOnAction(event -> {
      dialog.close();
      load(() -> showCarComponentsView(car, () -> EventBus.fireEvent(new ShowSaveCarComponentsEvent())));
    });
    dialog.getNoButton().setOnAction(event -> {
      dialog.close();
      load(null);
    });
  }

  private void load(EmptyCallback callback) {
    EventBus.fireEvent(new GetCarsEvent(cars -> {
      this.allCars = cars;
      filterCars();
      if (callback != null)
        callback.call();
    }));
  }

  private void filterCars() {
    List<CarModel> cars = ModelFilter.filterCars(allCars, carModelYearsFilter, carModelMakeFilter, carModelTypeFilter,
            carModelNameFilter, carModelKWFilter, carModelCapacityFilter, carModelCilindersFilter, carModelFuelFilter);
    loadCars(cars);
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
