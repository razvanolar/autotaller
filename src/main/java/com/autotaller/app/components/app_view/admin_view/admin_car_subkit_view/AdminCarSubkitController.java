package com.autotaller.app.components.app_view.admin_view.admin_car_subkit_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarKitsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarKitsEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarSubkitsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarSubkitsEventHandler;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.utils.AdminToolbarView;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogComponentType;
import com.autotaller.app.utils.factories.DialogFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AdminCarSubkitController implements Controller<AdminCarSubkitController.IAdminCarSubkitView> {

  public interface IAdminCarSubkitView extends AdminToolbarView {
    TableView<CarSubkitModel> getCarSubkitModelTable();
  }

  private List<CarKitModel> carKits;
  private List<CarSubkitModel> carSubkits;

  @Override
  public void bind(IAdminCarSubkitView view) {

    view.getAddButton().setOnAction(event -> {
      AddCarSubkitDialogController dialogController = new AddCarSubkitDialogController(carKits);
      AddCarSubkitDialogView dialogView = new AddCarSubkitDialogView();
      Component component = new Component(dialogController, dialogView);
      EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_SUBKIT_DIALOG, component)));
    });

    EventBus.addHandler(AdminLoadCarKitsEvent.TYPE, (AdminLoadCarKitsEventHandler) event -> {
      carKits = event.getCarKits();
    });

    EventBus.addHandler(AdminLoadCarSubkitsEvent.TYPE, (AdminLoadCarSubkitsEventHandler) event -> {
      carSubkits = event.getCarSubkits();
      ObservableList<CarSubkitModel> items = view.getCarSubkitModelTable().getItems();
      items.clear();
      if (carSubkits != null)
        items.addAll(carSubkits);
    });
  }
}