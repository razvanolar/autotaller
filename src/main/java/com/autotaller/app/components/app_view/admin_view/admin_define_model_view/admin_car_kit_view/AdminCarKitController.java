package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_kit_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.utils.IDefineModelController;
import com.autotaller.app.components.utils.SimpleDialog;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarKitCategoriesEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarKitsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarKitCategoreisEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarKitCategoriesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarKitsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarKitsEventHandler;
import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.DialogFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by razvanolar on 29.04.2017
 */
public class AdminCarKitController implements Controller<AdminCarKitController.IAdminCarKitView>, IDefineModelController {

  public interface IAdminCarKitView extends View {
    TableView<CarKitModel> getCarKitTable();
  }

  private IAdminCarKitView view;

  private List<CarKitCategoryModel> carKitCategories;
  private List<CarKitModel> carKits;

  @Override
  public void bind(IAdminCarKitView view) {
    this.view = view;

    EventBus.addHandler(AdminLoadCarKitCategoriesEvent.TYPE, (AdminLoadCarKitCategoreisEventHandler) event -> {
      carKitCategories = event.getCarKitCategories();
    });

    EventBus.addHandler(AdminLoadCarKitsEvent.TYPE, (AdminLoadCarKitsEventHandler) event -> loadKits(event.getCarKits()));
  }

  private void loadKits() {
    EventBus.fireEvent(new GetCarKitsEvent(this::loadKits));
  }

  private void loadKits(List<CarKitModel> carKits) {
    this.carKits = carKits;
    ObservableList<CarKitModel> items = view.getCarKitTable().getItems();
    items.clear();
    items.addAll(carKits);
  }

  private void loadKitCategories() {
    EventBus.fireEvent(new GetCarKitCategoriesEvent(this::loadKitCategories));
  }

  private void loadKitCategories(List<CarKitCategoryModel> carKitCategories) {
    this.carKitCategories = carKitCategories;
  }

  @Override
  public void loadIfEmpty() {
    ObservableList<CarKitModel> items = view.getCarKitTable().getItems();
    if (items.isEmpty())
      loadKits();
    if (carKitCategories == null || carKitCategories.isEmpty())
      loadKitCategories();
  }

  @Override
  public void addEntity() {
    if (carKitCategories == null || carKitCategories.isEmpty()) {
      EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Lista categoriilor de ansamble nu a putut fi incarcata")));
      return;
    }
    AddCarKitDialogController dialogController = new AddCarKitDialogController(carKitCategories);
    AddCarKitDialogView dialogView = new AddCarKitDialogView();
    Component component = new Component(dialogController, dialogView);
    EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_KIT_DIALOG, component)));
  }

  @Override
  public void deleteEntity() {

  }

  @Override
  public View getView() {
    return view;
  }
}
