package com.autotaller.app.components.app_view.cars_view.show_car_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.utils.DefaultCarController;
import com.autotaller.app.components.app_view.utils.DefaultCarView;
import com.autotaller.app.components.utils.CarDetailesView;
import com.autotaller.app.components.utils.NodeDialog;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetAllSystemDefinedModelsEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarsByTypeIdEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.InjectCarsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_view.InjectCarsEventHandler;
import com.autotaller.app.events.app_view.search_views.InjectCarEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarTypeEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarTypeEventHandler;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.util.List;

/**
 * Created by razvanolar on 02.06.2017
 */
public class ShowCarController implements Controller<ShowCarController.IShowCarView> {

  public interface IShowCarView extends View {
    DefaultCarView getDefaultCarView();
    ToggleButton getShowFilterButton();
    Button getDetailsButton();
    Button getContinueButton();
  }

  private IShowCarView view;
  private CarTypeModel injectedCarType;
  private List<CarModel> injectedCars;

  private DefaultCarController defaultCarController;

  @Override
  public void bind(IShowCarView view) {
    this.view = view;

    defaultCarController = new DefaultCarController();
    defaultCarController.bind(view.getDefaultCarView());

    view.getShowFilterButton().setOnAction(event -> {
      if (view.getShowFilterButton().isSelected()) {
        view.getDefaultCarView().showFilterPane();
      } else {
        view.getDefaultCarView().hideFilterPane();
      }
    });

    view.getDetailsButton().setOnAction(event -> {
      CarModel selectedCar = defaultCarController.getSelectedCar();
      if (selectedCar == null) {
        return;
      }
      CarDetailesView detailesView = new CarDetailesView(selectedCar);
      EventBus.fireEvent(new ShowDialogEvent(new NodeDialog("Detalii Masina", "Ok", detailesView.asNode(), false)));
    });

    view.getContinueButton().setOnAction(event -> {
      CarModel selectedCar = defaultCarController.getSelectedCar();
      if (selectedCar == null) {
        return;
      }
      Component component = ComponentFactory.createComponent(ComponentType.SHOW_CAR_KITS_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.SHOW_CAR_KITS_VIEW.getTitle()));
        EventBus.fireEvent(new InjectCarEvent(selectedCar));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    });

    // fired when want to load the cars for a specific car type
    EventBus.addHandler(InjectCarTypeEvent.TYPE, (InjectCarTypeEventHandler) event -> this.injectedCarType = event.getCarType(), true);

    // fired when want to load the specified cars
    EventBus.addHandler(InjectCarsEvent.TYPE, (InjectCarsEventHandler) event -> this.injectedCars = event.getCars());

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> load(), true);
  }

  private void load() {
    if (injectedCarType != null) {
      EventBus.fireEvent(new GetCarsByTypeIdEvent(injectedCarType.getId(), this::load));
    } else if (injectedCars != null) {
      load(injectedCars);
    }
  }

  private void load(List<CarModel> cars) {
    ObservableList<CarModel> items = view.getDefaultCarView().getCarsTable().getItems();
    items.clear();
    items.addAll(cars);
    defaultCarController.setCars(cars);
    EventBus.fireEvent(new GetAllSystemDefinedModelsEvent(models -> defaultCarController.setSystemModels(models, injectedCarType)));
  }
}
