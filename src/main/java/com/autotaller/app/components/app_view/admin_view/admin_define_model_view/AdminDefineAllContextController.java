package com.autotaller.app.components.app_view.admin_view.admin_define_model_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_kit_view.AdminCarKitController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_kit_view.AdminCarKitView;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view.AdminCarMakeController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_make_view.AdminCarMakeView;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view.AdminCarModelController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view.AdminCarModelView;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_subkit_view.AdminCarSubkitController;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_subkit_view.AdminCarSubkitView;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.utils.IDefineModelController;
import com.autotaller.app.components.utils.NotificationsUtil;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.*;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.*;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.*;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.*;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminDefineAllContextController implements Controller<AdminDefineAllContextController.IAdminDefineAllContextView> {

  public interface IAdminDefineAllContextView extends View {
    void setContent(Node content);
    Button getRefreshButton();
    Button getAddButton();
    Button getEditButton();
    Button getDeleteButton();
    Button getFilterButton();
    ToggleButton getShowCarMakePaneButton();
    ToggleButton getShowCarTypePaneButton();
    ToggleButton getShowCarKitPaneButton();
    ToggleButton getShowCarSubKitPaneButton();
  }

  private IAdminDefineAllContextView view;
  private Repository repository;

  private IDefineModelController carMakeController;
  private IDefineModelController carTypeController;
  private IDefineModelController carKitController;
  private IDefineModelController carSubKitController;

  @Override
  public void bind(IAdminDefineAllContextView view) {
    this.view = view;

    ChangeListener<Boolean> viewChangeListener = (observable, oldValue, newValue) -> {
      BooleanProperty property = (BooleanProperty) observable;
      Object source = property.getBean();
      IDefineModelController controller = null;
      if (source == view.getShowCarMakePaneButton()) {
        controller = getCarMakeController();
      } else if (source == view.getShowCarTypePaneButton()) {
        controller = getCarTypeController();
      } else if (source == view.getShowCarKitPaneButton()) {
        controller = getCarKitController();
      } else if (source == view.getShowCarSubKitPaneButton()) {
        controller = getCarSubKitController();
      }

      if (controller != null) {
        view.setContent(controller.getView().asNode());
        controller.loadIfEmpty();
      }
    };

    view.getShowCarMakePaneButton().selectedProperty().addListener(viewChangeListener);
    view.getShowCarTypePaneButton().selectedProperty().addListener(viewChangeListener);
    view.getShowCarKitPaneButton().selectedProperty().addListener(viewChangeListener);
    view.getShowCarSubKitPaneButton().selectedProperty().addListener(viewChangeListener);

    view.getAddButton().setOnAction(event -> {
      IDefineModelController controller = getCurrentController();
      if (controller != null) {
        controller.addEntity();
      }
    });

    view.getDeleteButton().setOnAction(event -> {
      IDefineModelController controller = getCurrentController();
      if (controller != null) {
        controller.deleteEntity();
      }
    });

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> {
      this.repository = event.getRepository();
      initRepoHandlers();
    }, true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> view.getShowCarMakePaneButton().setSelected(true), true);
  }

  private IDefineModelController getCarMakeController() {
    if (carMakeController == null) {
      AdminCarMakeController controller = new AdminCarMakeController();
      AdminCarMakeView view = new AdminCarMakeView();
      controller.bind(view);
      carMakeController = controller;
    }
    return carMakeController;
  }

  private IDefineModelController getCarTypeController() {
    if (carTypeController == null) {
      AdminCarModelController controller = new AdminCarModelController();
      AdminCarModelView view = new AdminCarModelView();
      controller.bind(view);
      carTypeController = controller;
    }
    return carTypeController;
  }

  private IDefineModelController getCarKitController() {
    if (carKitController == null) {
      AdminCarKitController controller = new AdminCarKitController();
      AdminCarKitView view = new AdminCarKitView();
      controller.bind(view);
      carKitController = controller;
    }
    return carKitController;
  }

  private IDefineModelController getCarSubKitController() {
    if (carSubKitController == null) {
      AdminCarSubkitController controller = new AdminCarSubkitController();
      AdminCarSubkitView view = new AdminCarSubkitView();
      controller.bind(view);
      carSubKitController = controller;
    }
    return carSubKitController;
  }

  private IDefineModelController getCurrentController() {
    if (view.getShowCarMakePaneButton().isSelected()) {
      return getCarMakeController();
    } else if (view.getShowCarTypePaneButton().isSelected()) {
      return getCarTypeController();
    } else if (view.getShowCarKitPaneButton().isSelected()) {
      return getCarKitController();
    } else if (view.getShowCarSubKitPaneButton().isSelected()) {
      return getCarSubKitController();
    }
    return null;
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

    view.setContent(carMakeComponent.getView().asNode());
    view.setContent(carModelComponent.getView().asNode());
    view.setContent(carKitComponent.getView().asNode());
    view.setContent(carSubkitComponent.getView().asNode());
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

    EventBus.addHandler(DeleteCarMakeEvent.TYPE, (DeleteCarMakeEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Stergere Marca"));
        Thread thread = new Thread(() -> {
          try {
            repository.deleteCarMake(event.getCarMake());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              NotificationsUtil.showInfoNotification("Info", "Inregistrarile pentru " + event.getCarMake().getName() + " au fost sterse cu succes.", 10);
              loadCarMakes();
              loadCarModels();
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              NotificationsUtil.showErrorNotification("Eroare", "Marca selectata nu a putut fi stearsa", -1);
            });
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
        NotificationsUtil.showErrorNotification("Eroare", "Marca selectata nu a putut fi stearsa", -1);
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

    EventBus.addHandler(DeleteCarModelEvent.TYPE, (DeleteCarModelEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Stergere model"));
        Thread thread = new Thread(() -> {
          try {
            repository.deleteCarType(event.getCarType());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              NotificationsUtil.showInfoNotification("Info", "Inregistrarile pentru " + event.getCarType().getName() + " au fost sterse cu succes.", 10);
              loadCarModels();
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              NotificationsUtil.showErrorNotification("Eroare", "Modelul selectat nu a putut fi sters", -1);
            });
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
        NotificationsUtil.showErrorNotification("Eroare", "Modelul selectat nu a putut fi sters", -1);
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
