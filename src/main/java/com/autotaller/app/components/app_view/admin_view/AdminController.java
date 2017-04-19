package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.LoadCarMakesEvent;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import javafx.scene.Node;

/**
 * Created by razvanolar on 18.04.2017
 */
public class AdminController implements Controller<AdminController.IAdminView> {

  public interface IAdminView extends View {
    Node getCarMakeRegistrationMenu();
  }

  @Override
  public void bind(IAdminView view) {
    EventBus.fireEvent(new LoadCarMakesEvent(cars -> {

    }));
  }
}
