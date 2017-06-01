package com.autotaller.app.components.app_view.admin_view.admin_define_model_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.*;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.*;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AddCarMakeEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AddCarMakeEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.AddCarModelEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.AddCarModelEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.AdminLoadCarModelsEvent;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.application.Platform;
import javafx.scene.Node;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminDefineAllContextController implements Controller<AdminDefineAllContextController.IAdminDefineAllContextView> {

  public interface IAdminDefineAllContextView extends View {
    void addToolbarPane(Node toolbarPane);
  }

  private IAdminDefineAllContextView view;
  private Repository repository;

  @Override
  public void bind(IAdminDefineAllContextView view) {
    this.view = view;

    initToolbarPanes();

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> {
      this.repository = event.getRepository();
      initRepoHandlers();
    }, true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> {
      loadCarMakes();
      loadCarModels();
      loadCarKitCategories();
      loadCarKits();
      loadCarSubkits();
    }, true);
  }

  private void initToolbarPanes() {
    Component carMakeComponent = ComponentFactory.createComponent(ComponentType.ADMIN_CAR_MAKE_VIEW);
    Component carModelComponent = ComponentFactory.createComponent(ComponentType.ADMIN_CAR_MODEL_VIEW);
    Component carKitComponent = ComponentFactory.createComponent(ComponentType.ADMIN_CAR_KIT_VIEW);
    Component carSubkitComponent = ComponentFactory.createComponent(ComponentType.ADMIN_CAR_SUBKIT_VIEW);

    if (carMakeComponent == null || carModelComponent == null || carKitComponent == null || carSubkitComponent == null) {
      //TODO handle exception
      return;
    }

    view.addToolbarPane(carMakeComponent.getView().asNode());
    view.addToolbarPane(carModelComponent.getView().asNode());
    view.addToolbarPane(carKitComponent.getView().asNode());
    view.addToolbarPane(carSubkitComponent.getView().asNode());
  }

  private void initRepoHandlers() {
    EventBus.addHandler(AddCarMakeEvent.TYPE, (AddCarMakeEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Adaugare Marca"));
        Thread thread = new Thread(() -> {
          try {
            repository.addCarMake(event.getCarMakeName());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              loadCarMakes();
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    }, true);

    EventBus.addHandler(AddCarModelEvent.TYPE, (AddCarModelEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Adaugare Marca"));
        Thread thread = new Thread(() -> {
          try {
            repository.addCarModel(event.getCarTypeModel());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              loadCarModels();
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    }, true);

    EventBus.addHandler(AddCarKitEvent.TYPE, (AddCarKitEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Adaugare Ansamblu"));
        Thread thread = new Thread(() -> {
          try {
            repository.addCarKit(event.getCarKit());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              loadCarKits();
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    }, true);

    EventBus.addHandler(AddCarSubkitEvent.TYPE, (AddCarSubkitEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Adaugare Sub-Ansamblu"));
        Thread thread = new Thread(() -> {
          try {
            repository.addCarSubkit(event.getCarSubkits());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              loadCarSubkits();
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    }, true);
  }

  private void loadCarMakes() {
    EventBus.fireEvent(new GetCarMakesEvent(cars -> EventBus.fireEvent(new AdminLoadCarMakesEvent(cars))));
  }

  private void loadCarModels() {
    EventBus.fireEvent(new GetCarModelsEvent(carModels -> EventBus.fireEvent(new AdminLoadCarModelsEvent(carModels))));
  }

  private void loadCarKitCategories() {
    EventBus.fireEvent(new GetCarKitCategoriesEvent(carKitCategories -> EventBus.fireEvent(new AdminLoadCarKitCategoriesEvent(carKitCategories))));
  }

  private void loadCarKits() {
    EventBus.fireEvent(new GetCarKitsEvent(carKits -> EventBus.fireEvent(new AdminLoadCarKitsEvent(carKits))));
  }

  private void loadCarSubkits() {
    EventBus.fireEvent(new GetCarSubkitsEvent(carSubkits -> EventBus.fireEvent(new AdminLoadCarSubkitsEvent(carSubkits)), true));
  }
}
