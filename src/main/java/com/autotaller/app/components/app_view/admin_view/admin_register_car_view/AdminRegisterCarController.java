package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.admin_register_car_view.utils.AdminCarTableViewContextMenu;
import com.autotaller.app.components.app_view.utils.DefaultCarController;
import com.autotaller.app.components.app_view.utils.DefaultCarView;
import com.autotaller.app.components.utils.*;
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
import com.autotaller.app.events.app_view.admin_view.admin_car_view.DeleteCarEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.DeleteCarEventHandler;
import com.autotaller.app.events.app_view.search_views.InjectCarEvent;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.events.view_stack.BackToPreviousViewEvent;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.utils.SaveCarResult;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.repository.utils.CarStatus;
import com.autotaller.app.repository.utils.ImageStatus;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.callbacks.EmptyCallback;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.util.List;

/**
 * Created by razvanolar on 02.05.2017
 */
public class AdminRegisterCarController implements Controller<AdminRegisterCarController.IAdminRegisterCarView> {

  public interface IAdminRegisterCarView extends View {
    DefaultCarView getDefaultCarView();
    Button getAddCarButton();
    Button getEditCarButton();
    Button getDeleteCarButton();
    ToggleButton getShowFilterCarButton();
    Button getCarDetailsButton();
    Button getComponentsButton();
    Button getStatisticsButton();
  }

  private IAdminRegisterCarView view;
  private Repository repository;
  private List<CarModel> allCars;

  private DefaultCarController defaultCarController;

  @Override
  public void bind(IAdminRegisterCarView view) {
    this.view = view;

    defaultCarController = new DefaultCarController();
    defaultCarController.bind(view.getDefaultCarView());

    view.getAddCarButton().setOnAction(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_SAVE_CAR_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.ADMIN_SAVE_CAR_VIEW.getTitle()));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    });

    view.getEditCarButton().setOnAction(event -> {
      CarModel selectedCar = view.getDefaultCarView().getCarsTable().getSelectionModel().getSelectedItem();
      if (selectedCar != null) {

      } else {
        EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Te rugam selecteaza o masina")));
      }
    });

    view.getDeleteCarButton().setOnAction(event -> {
      CarModel selectedCar = view.getDefaultCarView().getCarsTable().getSelectionModel().getSelectedItem();
      if (selectedCar != null) {
        YesNoDialog dialog = new YesNoDialog("Atentie", "Esti sugur ca vrei sa stergi aceasta inregistrare?");
        dialog.getYesButton().setOnAction(event1 -> {
          dialog.close();
          EventBus.fireEvent(new DeleteCarEvent(selectedCar));
        });
        EventBus.fireEvent(new ShowDialogEvent(dialog));
      } else {
        EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Te rugam selecteaza o masina")));
      }
    });

    view.getShowFilterCarButton().setOnAction(event -> {
      if (view.getShowFilterCarButton().isSelected()) {
        view.getDefaultCarView().showFilterPane();
      } else {
        view.getDefaultCarView().hideFilterPane();
      }
    });

    view.getComponentsButton().setOnAction(event -> {
      CarModel selectedCar = defaultCarController.getSelectedCar();
      if (selectedCar == null)
        return;
      showCarComponentsView(selectedCar, null);
    });

    view.getCarDetailsButton().setOnAction(event -> {
      CarModel selectedCar = defaultCarController.getSelectedCar();
      if (selectedCar == null)
        return;
      CarDetailesView detailesView = new CarDetailesView(selectedCar);
      EventBus.fireEvent(new ShowDialogEvent(new NodeDialog("Detalii Masina", "Ok", detailesView.asNode(), false)));
    });

    view.getStatisticsButton().setOnAction(event -> {
      CarModel selectedCar = defaultCarController.getSelectedCar();
      if (selectedCar == null)
        return;
      Component component = ComponentFactory.createComponent(ComponentType.CAR_STATISTICS_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.CAR_STATISTICS_VIEW + " (" + selectedCar.getName() + ")"));
        EventBus.fireEvent(new InjectCarEvent(selectedCar));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    });

    // set table context menu
    AdminCarTableViewContextMenu contextMenu = new AdminCarTableViewContextMenu();
    view.getDefaultCarView().getCarsTable().setContextMenu(contextMenu.getContextMenu());
    contextMenu.getGalleryMenuItem().setOnAction(event -> {
      CarModel selectedCar = view.getDefaultCarView().getCarsTable().getSelectionModel().getSelectedItem();
      if (selectedCar != null)
        EventBus.fireEvent(new OpenCarImageGalleryEvent(selectedCar.getId()));
    });

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> {
      this.repository = event.getRepository();
      initHandlers();
    }, true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> {
      load(null);
      EventBus.fireEvent(new GetAllSystemDefinedModelsEvent(models -> defaultCarController.setSystemModels(models)));
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

    EventBus.addHandler(DeleteCarEvent.TYPE, (DeleteCarEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Stergere masina"));
        Thread thread = new Thread(() -> {
          try {
            repository.deleteCar(event.getCar());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              NotificationsUtil.showInfoNotification("Info", "Masina a fost stearsa cu succes", 10);
              load(null);
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              NotificationsUtil.showErrorNotification("Eroare", "Masina selectata nu a putut fi stearsa", -1);
            });
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
        NotificationsUtil.showErrorNotification("Eroare", "Masina selectata nu a putut fi stearsa", -1);
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
      defaultCarController.setCars(allCars);
      loadCars(allCars);
      if (callback != null)
        callback.call();
    }));
  }

  private void loadCars(List<CarModel> cars) {
    ObservableList<CarModel> items = view.getDefaultCarView().getCarsTable().getItems();
    items.clear();
    items.addAll(cars);
  }
}
