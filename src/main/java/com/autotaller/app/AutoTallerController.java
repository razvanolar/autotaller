package com.autotaller.app;

import com.autotaller.app.components.utils.NotificationsUtil;
import com.autotaller.app.events.app_view.admin_view.*;
import com.autotaller.app.events.app_view.ShowAppViewEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.GetCarComponentsByCarIdEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.GetCarComponentsByCarIdEventHandler;
import com.autotaller.app.events.login_view.*;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.test_connection.TestConnectionEvent;
import com.autotaller.app.events.test_connection.TestConnectionEventHandler;
import com.autotaller.app.events.test_connection.TestConnectionFailedEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.model.*;
import com.autotaller.app.model.utils.CarDefinedModelsDTO;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.ComponentFactory;
import com.autotaller.app.utils.filters.ModelFilter;
import javafx.application.Platform;
import javafx.scene.Node;

import java.util.Date;
import java.util.List;

/**
 * Created by razvanolar on 11.04.2017
 */
public class AutoTallerController implements Controller<AutoTallerController.IAutoTallerView> {

  public interface IAutoTallerView extends View {
    Node getAdminMenu();
    Node getExitMenu();
  }

  private IAutoTallerView autoTallerView;
  private Repository repository;

  @Override
  public void bind(IAutoTallerView view) {
    // ignore received view object, routerView is computed internally

    EventBus.addHandler(TestConnectionEvent.TYPE, (TestConnectionEventHandler) handler -> {
      Thread th = new Thread(() -> {
        try {
//          Thread.sleep(500);
          repository = new Repository();
          repository.testConnection();
          Platform.runLater(() -> {
            // remove the handler after the db connection was tested successfully
            EventBus.removeHandlersByType(TestConnectionEvent.TYPE);
            EventBus.fireEvent(new ShowLoginScreenEvent());
            EventBus.fireEvent(new TestCredentialsEvent("admin", "admin"));
          });
        } catch (Exception e) {
          e.printStackTrace();
          Platform.runLater(() -> EventBus.fireEvent(new TestConnectionFailedEvent()));
        }
      });
      th.start();
    });

    EventBus.addHandler(TestCredentialsEvent.TYPE, (TestCredentialsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Autentificare"));
        Thread thread = new Thread(() -> {
          try {
            int userId = repository.getUserIdByCredentials(event.getUsername(), event.getPassword());
            Platform.runLater(() -> {
              if (userId < 0) {
                EventBus.fireEvent(new AuthenticationFailedEvent());
                EventBus.fireEvent(new UnmaskViewEvent());
                return;
              }
              EventBus.fireEvent(new ReleaseLoginViewEvent());
              // init autoTallerView
              initAppView();
              EventBus.fireEvent(new ShowAppViewEvent(autoTallerView));
              NotificationsUtil.showInfoNotification("Notificare", "Autentificat la: " + (new Date(System.currentTimeMillis())).toLocaleString(), 4);
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
          }
        });
        thread.start();
      } catch (NullPointerException npe) {
        npe.printStackTrace();
      } catch (Exception e) {
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });
  }

  private void initAppView() {
    try {
      autoTallerView = new AutoTallerView();
      initAppViewHandlers();
      initAutotallerUtilities();
    } catch (Exception e) {
      System.out.println("Exception when trying to init App View");
      e.printStackTrace();
    }
  }

  private void initAppViewHandlers() {
    autoTallerView.getAdminMenu().setOnMouseClicked(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView()));
        EventBus.fireEvent(new InjectRepoToAdminEvent(repository));
      }
    });

    autoTallerView.getExitMenu().setOnMouseClicked(event -> EventBus.fireEvent(new ShowLoginScreenEvent()));

    EventBus.addHandler(GetCarMakesEvent.TYPE, (GetCarMakesEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Masini"));
        Thread thread = new Thread(() -> {
          try {
            List<CarMakeModel> allCarMakes = repository.getAllCarMakes();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(allCarMakes);
            });
          } catch (Exception ex) {
            //TODO show error dialog
            ex.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO show error dialog
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });

    EventBus.addHandler(GetCarModelsEvent.TYPE, (GetCarModelsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Modele"));
        Thread thread = new Thread(() -> {
          try {
            List<CarTypeModel> carModels = repository.getCarModels();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(carModels);
            });
          } catch (Exception e) {
            //TODO show error dialog
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO show error dialog
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });

    EventBus.addHandler(GetCarKitCategoriesEvent.TYPE, (GetCarKitCategoriesEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Categorii Ansamble"));
        Thread thread = new Thread(() -> {
          try {
            List<CarKitCategoryModel> carKitCategories = repository.getCarKitCategories();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(carKitCategories);
            });
          } catch (Exception e) {
            //TODO show error dialog
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO show error dialog
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });

    EventBus.addHandler(GetCarKitsEvent.TYPE, (GetCarKitsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Ansamble"));
        Thread thread = new Thread(() -> {
          try {
            List<CarKitModel> carKits = repository.getCarKits();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(carKits);
            });
          } catch (Exception e) {
            //TODO show error dialog
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO show error dialog
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });

    EventBus.addHandler(GetCarSubkitsEvent.TYPE, (GetCarSubkitsEventHandler) event -> {
      try {
        if (event.isMaskView())
          EventBus.fireEvent(new MaskViewEvent("Incarcare Sub-Ansamble"));
        Thread thread = new Thread(() -> {
          try {
            List<CarSubkitModel> carSubkits = repository.getCarSubkits();
            Platform.runLater(() -> {
              if (event.isMaskView())
                EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(carSubkits);
            });
          } catch (Exception e) {
            //TODO show error dialog
            e.printStackTrace();
            Platform.runLater(() -> {
              if (event.isMaskView())
                EventBus.fireEvent(new UnmaskViewEvent());
            });
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO show error dialog
        e.printStackTrace();
        if (event.isMaskView())
          EventBus.fireEvent(new UnmaskViewEvent());
      }
    });

    EventBus.addHandler(GetAllSystemDefinedModelsEvent.TYPE, (GetAllSystemDefinedModelsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Model"));
        Thread thread = new Thread(() -> {
          try {
            SystemModelsDTO allDefinedModels = repository.getAllDefinedModels();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(allDefinedModels);
            });
          } catch (Exception e) {
            //TODO show error dialog
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO show error dialog
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });

    EventBus.addHandler(GetAllCarDefinedModelsEvent.TYPE, (GetAllCarDefinedModelsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare metrici sistem"));
        Thread thread = new Thread(() -> {
          try {
            CarDefinedModelsDTO carDefinedModels = repository.getCarDefinedModels();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(carDefinedModels);
            });
          } catch (Exception e) {
            //TODO handle exception
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });

    EventBus.addHandler(GetCarsEvent.TYPE, (GetCarsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Masini"));
        Thread thread = new Thread(() -> {
          try {
            List<CarModel> cars = repository.getCars();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(cars);
            });
          } catch (Exception e) {
            //TODO show error dialog
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO show error dialog
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });

    EventBus.addHandler(GetCarComponentsEvent.TYPE, (GetCarComponentsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Componente"));
        Thread thread = new Thread(() -> {
          try {
            List<CarComponentModel> result = repository.getCarComponents();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(result);
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
    });

    EventBus.addHandler(GetCarComponentsByCarIdEvent.TYPE, (GetCarComponentsByCarIdEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Componente"));
        Thread thread = new Thread(() -> {
          try {
            List<CarComponentModel> result = repository.getCarComponentsByCarId(event.getCarTypeId());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(result);
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
    });

    EventBus.addHandler(GetCarsByTypeIdEvent.TYPE, (GetCarsByTypeIdEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Masini"));
        Thread thread = new Thread(() -> {
          try {
            List<CarModel> cars = repository.getCarsByTypeId(event.getCarTypeId());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(cars);
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
    });
  }

  private void initAutotallerUtilities() {
    ModelFilter.init();
  }
}
