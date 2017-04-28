package com.autotaller.app.components.app_view.admin_view.admin_car_model_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEventHandler;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogComponentType;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.DialogFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.util.List;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AdminCarModelController implements Controller<AdminCarModelController.IAdminCarModelView> {

  public interface IAdminCarModelView extends View {
    Button getAddCarModelButton();
    ToggleButton getFilterButton();
    void showFilterPane();
    void hideFilterPane();
  }

  private List<CarMakeModel> carMakes;

  @Override
  public void bind(IAdminCarModelView view) {

    view.getAddCarModelButton().setOnAction(event -> {
      AddCarModelDialogController controller = new AddCarModelDialogController(carMakes);
      AddCarModelDialogView dialogView = new AddCarModelDialogView();
      Component component = new Component(controller, dialogView);
      EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_MODEL_DIALOG, component)));
    });

    view.getFilterButton().setOnAction(event -> {
      if (view.getFilterButton().isSelected()) {
        view.showFilterPane();
      } else {
        view.hideFilterPane();
      }
    });

    EventBus.addHandler(AdminLoadCarMakesEvent.TYPE, (AdminLoadCarMakesEventHandler) event -> carMakes = event.getCarMakeModels());
  }
}
