package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.AddCarEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.AddCarEventHandler;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

/**
 * Created by razvanolar on 02.05.2017
 */
public class AdminRegisterCarController implements Controller<AdminRegisterCarController.IAdminRegisterCarView> {

  public interface IAdminRegisterCarView extends View {
    Button getAddCarButton();
    Button getEditCarButton();
    Button getDeleteCarButton();
    ToggleButton getShowFilterCarButton();
    ToggleButton getCarDetailsButton();
    void showFilterPane();
    void hideFilterPane();
    void showDetailsPane();
    void hideDetailsPane();
  }

  private Repository repository;

  @Override
  public void bind(IAdminRegisterCarView view) {

    view.getAddCarButton().setOnAction(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_SAVE_CAR_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView()));
      }
    });

    view.getShowFilterCarButton().setOnAction(event -> {
      if (view.getShowFilterCarButton().isSelected()) {
        view.showFilterPane();
      } else {
        view.hideFilterPane();
      }
    });

    view.getCarDetailsButton().setOnAction(event -> {
      if (view.getCarDetailsButton().isSelected()) {
        view.showDetailsPane();
      } else {
        view.hideDetailsPane();
      }
    });

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> {
      this.repository = event.getRepository();
      initHandlers();
    });
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
              loadCars();
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

  private void loadCars() {

  }
}
