package com.autotaller.app.components.app_view.cars_view.search_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.GetCarsByTypeIdEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarTypeEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarTypeEventHandler;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarController implements Controller<SearchCarController.ISearchCarView> {

  public interface ISearchCarView extends View {
    TableView<CarModel> getCarsTable();
    Button getContinueButton();
  }

  private ISearchCarView view;
  private CarTypeModel carType;

  @Override
  public void bind(ISearchCarView view) {
    this.view = view;

    EventBus.addHandler(InjectCarTypeEvent.TYPE, (InjectCarTypeEventHandler) event -> this.carType = event.getCarType());

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> load());
  }

  private void load() {
    EventBus.fireEvent(new GetCarsByTypeIdEvent(carType.getId(), cars -> {
      ObservableList<CarModel> items = view.getCarsTable().getItems();
      items.clear();
      items.addAll(cars);
    }));
  }
}
