package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.admin_view.GetCarModelsEvent;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

  private IAdminView view;
  private Repository repository;

  @Override
  public void bind(IAdminView view) {
    this.view = view;

    view.getAddCarMakeButton().setSelected(true);

    EventHandler<ActionEvent> actionEventHandler = event -> {
      if (event.getSource() == view.getAddCarMakeButton() && view.getAddCarMakeButton().isSelected()) {
        initCarMakesView();
      } else if (event.getSource() == view.getAddCarModelButton() && view.getAddCarModelButton().isSelected()) {
        initCarModelsView();
      } else if (event.getSource() == view.getAddCarButton() && view.getAddCarButton().isSelected()) {
        initCarsView();
      }
    };
    view.getAddCarMakeButton().setOnAction(actionEventHandler);
    view.getAddCarModelButton().setOnAction(actionEventHandler);
    view.getAddCarButton().setOnAction(actionEventHandler);

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> {
      this.repository = event.getRepository();
      initRepoHandlers();
    });

    initCarMakesView();
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

  private void initCarMakesView() {
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

  private void initCarModelsView() {
    System.out.println("initCarModelsView");
    EventBus.fireEvent(new GetCarModelsEvent(carModels -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADD_CAR_MODEL_VIEW);
      if (component != null) {
        view.setContent(component.getView());
      } else {
        //TODO handle exception
      }
    }));
  }

  private void initCarsView() {

  }
}
