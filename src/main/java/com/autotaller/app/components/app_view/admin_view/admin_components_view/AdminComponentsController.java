package com.autotaller.app.components.app_view.admin_view.admin_components_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.GetCarComponentsByCarIdEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.InjectCarInformationEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.InjectCarInformationEventHandler;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;

import java.util.List;

/**
 * Created by razvanolar on 13.05.2017
 */
public class AdminComponentsController implements Controller<AdminComponentsController.IAdminComponentsView> {

  public interface IAdminComponentsView extends View {
    Button getAddComponentButton();
    Button getEditComponentButton();
    Button getDeleteComponentButton();
    ToggleButton getFilterComponentsButton();
    Button getDetailsButton();
    TableView<CarComponentModel> getCarComponentsTable();
  }

  private int injectedCarId;
  private IAdminComponentsView view;

  private List<CarComponentModel> carComponents;

  @Override
  public void bind(IAdminComponentsView view) {
    this.view = view;

    EventBus.addHandler(InjectCarInformationEvent.TYPE, (InjectCarInformationEventHandler) event -> this.injectedCarId = event.getCarId());

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> {
      loadComponents();
    }, true);
  }

  private void loadComponents() {
    EventBus.fireEvent(new GetCarComponentsByCarIdEvent(injectedCarId, carComponents -> {
      this.carComponents = carComponents;
      ObservableList<CarComponentModel> items = view.getCarComponentsTable().getItems();
      items.clear();
      items.addAll(carComponents);
    }));
  }
}
