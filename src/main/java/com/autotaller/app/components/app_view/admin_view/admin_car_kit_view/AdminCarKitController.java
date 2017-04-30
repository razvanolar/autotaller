package com.autotaller.app.components.app_view.admin_view.admin_car_kit_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarKitCategoreisEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarKitCategoriesEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarKitsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_kit_view.AdminLoadCarKitsEventHandler;
import com.autotaller.app.model.CarKitCategoryModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.DialogFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by razvanolar on 29.04.2017
 */
public class AdminCarKitController implements Controller<AdminCarKitController.IAdminCarKitView> {

  public interface IAdminCarKitView extends AdminToolbarView {
    TableView<CarKitModel> getCarKitTable();
    ComboBox<CarKitCategoryModel> getCarKitCategoriesCombo();
  }

  private IAdminCarKitView view;

  private CarKitCategoryModel allCarKitCategoryItem = new CarKitCategoryModel(-1, "--Toate--");
  private List<CarKitCategoryModel> carKitCategories;
  private List<CarKitModel> carKits;

  @Override
  public void bind(IAdminCarKitView view) {
    this.view = view;

    view.showFilterPane();
    view.getFilterButton().setSelected(true);

    view.getAddButton().setOnAction(event -> {
      AddCarKitDialogController dialogController = new AddCarKitDialogController(carKitCategories);
      AddCarKitDialogView dialogView = new AddCarKitDialogView();
      Component component = new Component(dialogController, dialogView);
      EventBus.fireEvent(new ShowDialogEvent(DialogFactory.createDialog(DialogComponentType.ADD_CAR_KIT_DIALOG, component)));
    });

    EventBus.addHandler(AdminLoadCarKitCategoriesEvent.TYPE, (AdminLoadCarKitCategoreisEventHandler) event -> {
      carKitCategories = event.getCarKitCategories();
      ObservableList<CarKitCategoryModel> items = view.getCarKitCategoriesCombo().getItems();
      items.clear();
      items.add(allCarKitCategoryItem);
      items.addAll(carKitCategories);
      view.getCarKitCategoriesCombo().setValue(allCarKitCategoryItem);
    });

    EventBus.addHandler(AdminLoadCarKitsEvent.TYPE, (AdminLoadCarKitsEventHandler) event -> {
      carKits = event.getCarKits();
      ObservableList<CarKitModel> items = view.getCarKitTable().getItems();
      items.clear();
      items.addAll(carKits);
    });
  }
}
