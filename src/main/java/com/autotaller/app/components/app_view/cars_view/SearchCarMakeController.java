package com.autotaller.app.components.app_view.cars_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.utils.ChipSet;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.GetCarMakesEvent;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarMakeController implements Controller<SearchCarMakeController.ISearchCarMakeView> {

  public interface ISearchCarMakeView extends View {
    void addChipSet(ChipSet chipSet);
    TableView<CarMakeModel> getCarMakeTable();
  }

  private ISearchCarMakeView view;
  private List<CarMakeModel> carMakes;

  @Override
  public void bind(ISearchCarMakeView view) {
    this.view = view;

    view.addChipSet(new ChipSet("test"));

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> load());
  }

  private void load() {
    EventBus.fireEvent(new GetCarMakesEvent(cars -> {
      this.carMakes = cars;
      ObservableList<CarMakeModel> items = view.getCarMakeTable().getItems();
      items.clear();
      items.addAll(cars);
    }));
  }
}
