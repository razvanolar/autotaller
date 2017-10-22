package com.autotaller.app.components.app_view.cars_view.show_car_kits_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.utils.listeners.KitSelectionListener;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.GetAllSystemDefinedModelsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.InjectCarInformationEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.InjectCarKitInformationEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarEventHandler;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.model.CarModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;

/**
 * Created by razvanolar on 02.06.2017
 */
public class ShowCarKitsController implements Controller<ShowCarKitsController.IShowCarKitsView>, KitSelectionListener {

  public interface IShowCarKitsView extends View {
    void createKitLabels(SystemModelsDTO systemModels, KitSelectionListener listener);
  }

  private IShowCarKitsView view;
  private CarModel selectedCar;

  @Override
  public void bind(IShowCarKitsView view) {
    this.view = view;

    EventBus.addHandler(InjectCarEvent.TYPE, (InjectCarEventHandler) event -> this.selectedCar = event.getCar(), true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> load(), true);
  }

  @Override
  public void onKitSelection(CarKitModel catKit) {
    Component component = ComponentFactory.createComponent(ComponentType.SEARCH_CAR_COMPONENTS_VIEW);
    if (component != null) {
      String title = ComponentType.SEARCH_CAR_COMPONENTS_VIEW.getTitle();
      EventBus.fireEvent(new AddViewToStackEvent(component.getView(), title + " (" + catKit.getName() + ")"));
      if (selectedCar != null)
        EventBus.fireEvent(new InjectCarInformationEvent(selectedCar.getId()));
      EventBus.fireEvent(new InjectCarKitInformationEvent(catKit.getId()));
      EventBus.fireEvent(new BindLastViewEvent());
    }
  }

  private void load() {
    EventBus.fireEvent(new GetAllSystemDefinedModelsEvent(models -> view.createKitLabels(models, this)));
  }
}
