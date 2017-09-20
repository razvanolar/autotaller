package com.autotaller.app;

import com.autotaller.app.components.utils.ImageGalleryDialog;
import com.autotaller.app.components.utils.NotificationsUtil;
import com.autotaller.app.components.utils.PasswordDialog;
import com.autotaller.app.events.app_view.*;
import com.autotaller.app.events.app_view.admin_view.*;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.*;
import com.autotaller.app.events.app_view.search_views.AddPreSellComponentEvent;
import com.autotaller.app.events.app_view.search_views.AddPreSellComponentEventHandler;
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
import com.autotaller.app.utils.resources.ImageProvider;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by razvanolar on 11.04.2017
 */
public class AutoTallerController implements Controller<AutoTallerController.IAutoTallerView> {

  public interface IAutoTallerView extends View {
    Node getCarsMenu();
    Node getAdminMenu();
    Node getNotificationsMenu();
    Node getExitMenu();
  }

  private IAutoTallerView autoTallerView;
  private Repository repository;
  private UserModel activeUser;

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
    }, true);

    EventBus.addHandler(TestCredentialsEvent.TYPE, (TestCredentialsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Autentificare"));
        Thread thread = new Thread(() -> {
          try {
            UserModel user = repository.getUserIdByCredentials(event.getUsername(), event.getPassword());
            Platform.runLater(() -> {
              if (user == null) {
                EventBus.fireEvent(new AuthenticationFailedEvent());
                EventBus.fireEvent(new UnmaskViewEvent());
                return;
              }
              activeUser = user;
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
    }, true);

    EventBus.addHandler(GetActiveUserEvent.TYPE, (GetActiveUserEventHandler) event -> event.getCallback().call(activeUser), true);
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

  private void tryOpenNotificationView(String password) {
    EventBus.fireEvent(new CheckUserPasswordEvent(activeUser.getId(), password, () -> {
      Component component = ComponentFactory.createComponent(ComponentType.NOTIFICATIONS_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.NOTIFICATIONS_VIEW.getTitle()));
        EventBus.fireEvent(new InjectRepoToAdminEvent(repository));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    }));
  }

  private void initAppViewHandlers() {
    autoTallerView.getCarsMenu().setOnMouseClicked(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.SEARCH_CAR_MAKE_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.SEARCH_CAR_MAKE_VIEW.getTitle()));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    });

    autoTallerView.getAdminMenu().setOnMouseClicked(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.ADMIN_VIEW.getTitle()));
        EventBus.fireEvent(new InjectRepoToAdminEvent(repository));
      }
    });

    autoTallerView.getNotificationsMenu().setOnMouseClicked(event -> {
      PasswordDialog dialog = new PasswordDialog("Introduceti parola");
      EventBus.fireEvent(new ShowDialogEvent(dialog));
      dialog.getPasswordField().setOnKeyPressed(keyEvent -> {
        if (keyEvent.getCode() == KeyCode.ENTER) {
          dialog.close();
          tryOpenNotificationView(dialog.getText());
        }
      });
      dialog.getConfirmationButton().setOnAction(dialogEvent -> {
        dialog.close();
        tryOpenNotificationView(dialog.getText());
      });
    });

    autoTallerView.getExitMenu().setOnMouseClicked(event -> EventBus.fireEvent(new ShowLoginScreenEvent()));

    EventBus.addHandler(OpenCarImageGalleryEvent.TYPE, (OpenCarImageGalleryEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Imagini"));
        Thread thread = new Thread(() -> {
          try {
            List<File> files = repository.getCarImageFiles(event.getCarId());
            List<Image> images = new ArrayList<>();
            if (files != null && !files.isEmpty()) {
              for (File file : files) {
                images.add(ImageProvider.getImageFromPath(file.getAbsolutePath()));
              }
            }

            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              EventBus.fireEvent(new ShowDialogEvent(new ImageGalleryDialog(images)));
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
    }, true);

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
    }, true);

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
    }, true);

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
    }, true);

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
    }, true);

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
    }, true);

    EventBus.addHandler(GetAllSystemDefinedModelsEvent.TYPE, (GetAllSystemDefinedModelsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Model Sistem"));
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
    }, true);

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
    }, true);

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
    }, true);

    EventBus.addHandler(GetCarComponentsEvent.TYPE, (GetCarComponentsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Componente"));
        Thread thread = new Thread(() -> {
          try {
            List<CarComponentModel> result = repository.getCarComponents(event.getLimit(), event.getOffset());
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
    }, true);

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
    }, true);

    EventBus.addHandler(GetCarComponentsByCarAndKitIdEvent.TYPE, (GetCarComponentsByCarAndKitIdEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Componente"));
        Thread thread = new Thread(() -> {
          try {
            List<CarComponentModel> result = repository.getCarComponentsByCarAndKitId(event.getCarId(), event.getKitId());
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
    }, true);

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
    }, true);

    EventBus.addHandler(AddPreSellComponentEvent.TYPE, (AddPreSellComponentEventHandler) event -> {
      try {
         EventBus.fireEvent(new MaskViewEvent("Inregistrare actiune"));
         Thread thread = new Thread(() -> {
           try {
             repository.addPresellComponent(event.getPreSellComponent(), activeUser.getId());
             Platform.runLater(() -> {
               EventBus.fireEvent(new UnmaskViewEvent());
               NotificationsUtil.showInfoNotification("Notificare", "Inregistrarea a fost efectuata cu succes", 10);
               event.getCallback().call();
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

    EventBus.addHandler(CheckUserPasswordEvent.TYPE, (CheckUserPasswordEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Testare parola"));
        Thread thread = new Thread(() -> {
          try {
            boolean result = repository.checkUserPassword(event.getUserId(), event.getPassword());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              if (result) {
                NotificationsUtil.showInfoNotification("Notificare", "Parola testata", 5);
                event.getCallback().call();
              } else {
                NotificationsUtil.showErrorNotification("Atentie", "Parola nu a putut fi validata", -1);
              }
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

    EventBus.addHandler(GetCarComponentsCountEvent.TYPE, (GetCarComponentsCountEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Interogare numar componente..."));
        Thread thread = new Thread(() -> {
          try {
            int carComponentsCount = repository.getCarComponentsCount();
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(carComponentsCount);
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

  private void initAutotallerUtilities() {

  }
}
