package com.autotaller.app.components.app_view.admin_view.admin_define_model_view.admin_car_subkit_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.admin_define_model_view.utils.IDefineModelController;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarKitCategoriesEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarKitsEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarSubkitsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.*;
import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.DialogFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by razvanolar on 30.04.2017
 */
public class AdminCarSubkitController implements Controller<AdminCarSubkitController.IAdminCarSubkitView>, IDefineModelController {

  public interface IAdminCarSubkitView extends View {
    TableView<CarSubkitModel> getCarSubkitModelTable();
  }

  private List<CarKitCategoryModel> carKitCategories;
  private List<CarKitModel> carKits;
  private List<CarSubkitModel> carSubkits;

  private IAdminCarSubkitView view;

  @Override
  public void bind(IAdminCarSubkitView view) {
    this.view = view;

    EventBus.addHandler(AdminLoadCarKitCategoriesEvent.TYPE, (AdminLoadCarKitCategoreisEventHandler) event -> loadCarKitCategories(event.getCarKitCategories()));

    EventBus.addHandler(AdminLoadCarKitsEvent.TYPE, (AdminLoadCarKitsEventHandler) event -> loadCarKits(event.getCarKits()));

    EventBus.addHandler(AdminLoadCarSubkitsEvent.TYPE, (AdminLoadCarSubkitsEventHandler) event -> loadCarSubKits(event.getCarSubkits()));
  }

  private void loadCarSubKits() {
    EventBus.fireEvent(new GetCarSubkitsEvent(this::loadCarSubKits, true));
  }

  private void loadCarSubKits(List<CarSubkitModel> carSubkits) {
    this.carSubkits = carSubkits;
    ObservableList<CarSubkitModel> items = view.getCarSubkitModelTable().getItems();
    items.clear();
    items.addAll(carSubkits);
  }

  private void loadCarKits() {
    EventBus.fireEvent(new GetCarKitsEvent(this::loadCarKits));
  }

  private void loadCarKits(List<CarKitModel> carKits) {
    this.carKits = carKits;
  }

  private void loadCarKitCategories() {
    EventBus.fireEvent(new GetCarKitCategoriesEvent(this::loadCarKitCategories));
  }

  private void loadCarKitCategories(List<CarKitCategoryModel> carKitCategories) {
    this.carKitCategories = carKitCategories;
  }

  @Override
  public void loadIfEmpty() {
    ObservableList<CarSubkitModel> items = view.getCarSubkitModelTable().getItems();
    if(items.isEmpty())
      loadCarSubKits();
    if (carKits == null || carKits.isEmpty())
      loadCarKits();
    if (carKitCategories == null || carKitCategories.isEmpty())
      loadCarKitCategories();
  }

  @Override
  public void addEntity() {
    AddCarSubkitDialogController dialogController = new AddCarSubkitDialogController(carKitCategories, carKits);
    AddCarSubkitDialogView dialogView = new AddCarSubkitDialogView();
    Component component = new Component(dialogController, dialogView);
    EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_SUBKIT_DIALOG, component)));
  }

  @Override
  public View getView() {
    return view;
  }
}
