package com.autotaller.app.components.app_view.admin_view.admin_car_subkit_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarSubkitsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarSubkitsEventHandler;
import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.utils.AdminToolbarView;
import com.autotaller.app.utils.Controller;
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

  private List<CarSubkitModel> carSubkits;

  @Override
  public void bind(IAdminCarSubkitView view) {

    EventBus.addHandler(AdminLoadCarSubkitsEvent.TYPE, (AdminLoadCarSubkitsEventHandler) event -> {
      carSubkits = event.getCarSubkits();
      ObservableList<CarSubkitModel> items = view.getCarSubkitModelTable().getItems();
      items.clear();
      if (carSubkits != null)
        items.addAll(carSubkits);
    });
  }
}
