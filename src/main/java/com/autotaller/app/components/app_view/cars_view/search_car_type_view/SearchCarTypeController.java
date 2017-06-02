package com.autotaller.app.components.app_view.cars_view.search_car_type_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.search_views.InjectCarTypeEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarTypesEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarTypesEventHandler;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarTypeController implements Controller<SearchCarTypeController.ISearchCarTypeView> {

  public interface ISearchCarTypeView extends View {
    TableView<CarTypeModel> getCarTypeTable();
    Button getContinueButton();
  }

  private ISearchCarTypeView view;
  private List<CarTypeModel> carTypes;

  @Override
  public void bind(ISearchCarTypeView view) {
    this.view = view;

    view.getContinueButton().setOnAction(event -> {
      CarTypeModel selectedCarType = view.getCarTypeTable().getSelectionModel().getSelectedItem();
      if (selectedCarType == null) {
        return;
      }
      Component component = ComponentFactory.createComponent(ComponentType.SEARCH_CAR_VIEW);
      if (component != null) {
        String title = ComponentType.SEARCH_CAR_VIEW.getTitle();
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), title + "(Model: " + selectedCarType.getName() + ")"));
        EventBus.fireEvent(new InjectCarTypeEvent(selectedCarType));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    });

    EventBus.addHandler(InjectCarTypesEvent.TYPE, (InjectCarTypesEventHandler) event -> this.carTypes = event.getCarTypes());

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> {
      ObservableList<CarTypeModel> items = view.getCarTypeTable().getItems();
      items.clear();
      if (carTypes != null)
        items.addAll(carTypes);
    });
  }
}
