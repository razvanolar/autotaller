package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_model_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.util.FilterPanelView;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_make_view.AdminLoadCarMakesEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.AdminLoadCarModelsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_model_view.AdminLoadCarModelsEventHandler;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.utils.YearsRange;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.DialogFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by razvanolar on 20.04.2017
 */
public class AdminCarModelController implements Controller<AdminCarModelController.IAdminCarModelView> {

  public interface IAdminCarModelView extends AdminToolbarView {
    TableView<CarTypeModel> getCarModelTable();
    ComboBox<CarMakeModel> getCarMakeCombo();
    TextField getCarModelNameTextField();
    DatePicker getFromDatePicker();
    DatePicker getToDatePicker();
    TextField getEngineTextField();
    FilterPanelView<Integer> getFilterPanelView();
  }

  private IAdminCarModelView view;

  private CarMakeModel allCarMakesItem = new CarMakeModel(-1, "--Toate--");
  private List<CarMakeModel> carMakes;
  private List<CarTypeModel> carModels;

  @Override
  public void bind(IAdminCarModelView view) {
    this.view = view;

    view.getAddButton().setOnAction(event -> {
      AddCarModelDialogController controller = new AddCarModelDialogController(carMakes);
      AddCarModelDialogView dialogView = new AddCarModelDialogView();
      Component component = new Component(controller, dialogView);
      EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_MODEL_DIALOG, component)));
    });

    view.showFilterPane();
    view.getFilterButton().setSelected(true);

    EventBus.addHandler(AdminLoadCarMakesEvent.TYPE, (AdminLoadCarMakesEventHandler) event -> {
      carMakes = event.getCarMakeModels();
      updateFilterPane();
    });

    EventBus.addHandler(AdminLoadCarModelsEvent.TYPE, (AdminLoadCarModelsEventHandler) event -> {
      carModels = event.getCarModels();
      ObservableList<CarTypeModel> items = view.getCarModelTable().getItems();
      items.clear();
      items.addAll(carModels);
      updateFilterPane();
    });
  }

  private void updateFilterPane() {
    CarMakeModel selectedCarMake = view.getCarMakeCombo().getValue();
    ObservableList<CarMakeModel> carMakeStore = view.getCarMakeCombo().getItems();
    carMakeStore.clear();
    carMakeStore.add(allCarMakesItem);
    if (carMakes != null)
      carMakeStore.addAll(carMakes);
    view.getCarMakeCombo().setValue(selectedCarMake != null ? selectedCarMake : allCarMakesItem);


    view.getFilterPanelView().showFilterPanels(getYearsRange().toList());
  }

  private YearsRange getYearsRange() {
    if (carModels == null || carModels.isEmpty())
      return getDefaultYearRange();
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (CarTypeModel carModel : carModels) {
      int fromYear = carModel.getFrom().getYear();
      int toYear = carModel.getTo() != null ? carModel.getTo().getYear() : 0;
      if (min > fromYear)
        min = fromYear;
      if (max < fromYear)
        max = fromYear;

      if (toYear != 0) {
        if (min > toYear)
          min = toYear;
        if (max < toYear)
          max = toYear;
      }
    }

    return min != Integer.MAX_VALUE && max != Integer.MIN_VALUE ? new YearsRange(min, max) : getDefaultYearRange();
  }

  private YearsRange getDefaultYearRange() {
    LocalDate now = LocalDate.now();
    return new YearsRange(now.getYear(), now.getYear());
  }
}
