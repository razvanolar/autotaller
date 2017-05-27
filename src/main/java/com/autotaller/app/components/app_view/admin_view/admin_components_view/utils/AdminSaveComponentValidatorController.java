package com.autotaller.app.components.app_view.admin_view.admin_components_view.utils;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.InjectPreviewCarComponentsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.InjectPreviewCarComponentsEventHandler;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Created by razvanolar on 26.05.2017
 */
public class AdminSaveComponentValidatorController implements Controller<AdminSaveComponentValidatorController.IAdminSaveCarComponentValidatorView> {

  public interface IAdminSaveCarComponentValidatorView extends View {
    TableView<CarComponentModel> getCarComponentTable();
  }

  private IAdminSaveCarComponentValidatorView view;

  @Override
  public void bind(IAdminSaveCarComponentValidatorView view) {
    this.view = view;

    EventBus.addHandler(InjectPreviewCarComponentsEvent.TYPE, (InjectPreviewCarComponentsEventHandler) event -> {
      ObservableList<CarComponentModel> items = view.getCarComponentTable().getItems();
      items.clear();
      if (event.getCarComponents() != null)
        items.addAll(event.getCarComponents());
    }, true);
  }
}
