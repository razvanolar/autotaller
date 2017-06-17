package com.autotaller.app.components.app_view.cars_view.search_car_type_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.util.FilterPanelView;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.search_views.InjectCarTypeEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarTypesEvent;
import com.autotaller.app.events.app_view.search_views.InjectCarTypesEventHandler;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.utils.YearsRange;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import com.autotaller.app.utils.filters.car_model_filters.CarTypeModelYearsFilter;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by razvanolar on 02.06.2017
 */
public class SearchCarTypeController implements Controller<SearchCarTypeController.ISearchCarTypeView> {

  public interface ISearchCarTypeView extends View {
    TableView<CarTypeModel> getCarTypeTable();
    Button getContinueButton();
    FilterPanelView<Integer> getYearFilterPane();
  }

  private ISearchCarTypeView view;
  private List<CarTypeModel> carTypes;

  private CarTypeModelYearsFilter carTypeModelYearsFilter;

  @Override
  public void bind(ISearchCarTypeView view) {
    this.view = view;

    carTypeModelYearsFilter = new CarTypeModelYearsFilter();
    carTypeModelYearsFilter.getFields().addListener((ListChangeListener<Integer>) c -> filter());

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

    EventBus.addHandler(InjectCarTypesEvent.TYPE, (InjectCarTypesEventHandler) event -> {
      this.carTypes = event.getCarTypes();
      view.getYearFilterPane().showFilterPanels(computeYearRange(carTypes).toList());
      view.getYearFilterPane().setFilterObject(carTypeModelYearsFilter);
    }, true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> loadCarTypes(carTypes), true);
  }

  private void loadCarTypes(List<CarTypeModel> carTypeList) {
    ObservableList<CarTypeModel> items = view.getCarTypeTable().getItems();
    items.clear();
    if (carTypeList != null)
      items.addAll(carTypeList);
  }

  private void filter() {
    List<CarTypeModel> result = carTypeModelYearsFilter.filter(carTypes);
    loadCarTypes(result);
  }

  private YearsRange computeYearRange(List<CarTypeModel> carTypes) {
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    int now = LocalDate.now().getYear();

    for (CarTypeModel carType : carTypes) {
      int fromYear = carType.getFrom().getYear();
      int toYear = carType.getTo() != null ? carType.getTo().getYear() : 0;
      if (min > fromYear)
        min = fromYear;
      if (toYear != 0 && max < toYear)
        max = toYear;
      else
        max = now;
    }

    if (min == Integer.MAX_VALUE)
      min = now;
    if (max == Integer.MIN_VALUE || max > now)
      max = now;
    return new YearsRange(min, max);
  }
}
