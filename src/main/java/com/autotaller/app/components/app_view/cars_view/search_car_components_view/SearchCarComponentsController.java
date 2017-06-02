package com.autotaller.app.components.app_view.cars_view.search_car_components_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.*;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarComponentsController implements Controller<SearchCarComponentsController.ISearchCarComponentsView> {

  public interface ISearchCarComponentsView extends View {
    TableView<CarComponentModel> getCarComponentsTable();
  }

  private ISearchCarComponentsView view;
  private int carId;
  private int kitId;

  @Override
  public void bind(ISearchCarComponentsView view) {
    this.view = view;

    EventBus.addHandler(InjectCarInformationEvent.TYPE, (InjectCarInformationEventHandler) event -> this.carId = event.getCarId(), true);

    EventBus.addHandler(InjectCarKitInformationEvent.TYPE, (InjectCarKitInformationEventHandler) event -> this.kitId = event.getKitId(), true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> load(), true);
  }

  private void load() {
    EventBus.fireEvent(new GetCarComponentsByCarAndKitIdEvent(carId, kitId, carComponents -> {
      ObservableList<CarComponentModel> items = view.getCarComponentsTable().getItems();
      items.clear();
      items.addAll(carComponents);
    }));
  }
}
