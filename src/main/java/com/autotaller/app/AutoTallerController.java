package com.autotaller.app;

import com.autotaller.app.events.app_view.admin_view.*;
import com.autotaller.app.events.app_view.ShowAppViewEvent;
import com.autotaller.app.events.login_view.*;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.test_connection.TestConnectionEvent;
import com.autotaller.app.events.test_connection.TestConnectionEventHandler;
import com.autotaller.app.events.test_connection.TestConnectionFailedEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

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
          Thread.sleep(500);
          repository = new Repository();
          repository.testConnection();
          Platform.runLater(() -> {
            // remove the handler after the db connection was tested successfully
            EventBus.removeHandlersByType(TestConnectionEvent.TYPE);
            EventBus.fireEvent(new ShowLoginScreenEvent());
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
        int userId = repository.getUserIdByCredentials(event.getUsername(), event.getPassword());
        if (userId < 0) {
          EventBus.fireEvent(new AuthenticationFailedEvent());
          EventBus.fireEvent(new UnmaskViewEvent());
          return;
        }
        EventBus.fireEvent(new ReleaseLoginViewEvent());
        // init autoTallerView
        initAppView();
        EventBus.fireEvent(new ShowAppViewEvent(autoTallerView));
        Notifications notification = Notifications.create()
                .title("Notificare")
                .text("Autentificat la: " + (new Date(System.currentTimeMillis())).toLocaleString())
                .hideAfter(Duration.seconds(4))
                .position(Pos.BOTTOM_RIGHT)
                .graphic(null)
                .materiaDesignStyle()
                .setHeaderCheck();
        notification.show();
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
              event.getCallback().call(allCarMakes);
              EventBus.fireEvent(new UnmaskViewEvent());
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
        event.getCallback().call(null);
        EventBus.fireEvent(new UnmaskViewEvent());
      } catch (Exception e) {
        //TODO show error dialog
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });
  }
}
