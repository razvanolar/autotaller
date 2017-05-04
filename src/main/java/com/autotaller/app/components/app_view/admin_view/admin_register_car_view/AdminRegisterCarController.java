package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogComponentType;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.DialogFactory;
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
      AdminRegisterCarDialogController dialogController = new AdminRegisterCarDialogController();
      AdminRegisterCarDialogView dialogView = new AdminRegisterCarDialogView();
      Component component = new Component(dialogController, dialogView);
      EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_DIALOG, component)));
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

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> this.repository = repository);
  }
}
