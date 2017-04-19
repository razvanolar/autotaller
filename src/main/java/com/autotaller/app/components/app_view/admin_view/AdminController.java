package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarMakesEvent;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
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

  @Override
  public void bind(IAdminView view) {
    view.getAddCarMakeButton().setSelected(true);

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
}
