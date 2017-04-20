package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AddCarMakeEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AddCarMakeEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarMakesEvent;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.application.Platform;
import javafx.scene.control.ToggleButton;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminController implements Controller<AdminController.IAdminView> {

  public interface IAdminView extends View {
    void setContent(View view);
    ToggleButton getAddCarMakeButton();
    ToggleButton getAddCarModelButton();
    ToggleButton getAddCarButton();
  }

  private Repository repository;

  @Override
  public void bind(IAdminView view) {
    view.getAddCarMakeButton().setSelected(true);

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> {
      this.repository = event.getRepository();
      initRepoHandlers();
    });

    EventBus.fireEvent(new GetCarMakesEvent(cars -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_CAR_MAKE_VIEW);
      if (component != null) {
        view.setContent(component.getView());
        EventBus.fireEvent(new AdminLoadCarMakesEvent(cars));
      } else {
        //TODO handle exception
      }
    }));
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
              EventBus.fireEvent(new GetCarMakesEvent(cars -> EventBus.fireEvent(new AdminLoadCarMakesEvent(cars))));
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
    });
  }
}
