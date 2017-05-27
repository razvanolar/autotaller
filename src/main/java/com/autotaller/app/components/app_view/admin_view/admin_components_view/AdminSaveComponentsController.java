package com.autotaller.app.components.app_view.admin_view.admin_components_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.admin_components_view.utils.KitSelectionListener;
import com.autotaller.app.components.utils.NodeDialog;
import com.autotaller.app.components.utils.NotificationsUtil;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetAllSystemDefinedModelsEvent;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.InjectPreviewCarComponentsEvent;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.model.CarKitModel;
import com.autotaller.app.model.CarSubkitModel;
import com.autotaller.app.model.utils.SystemModelsDTO;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by razvanolar on 21.05.2017
 */
public class AdminSaveComponentsController implements Controller<AdminSaveComponentsController.IAdminSaveComponentsView>, KitSelectionListener {

  public interface IAdminSaveComponentsView extends View {
    TableView<CarComponentModel> getCarComponentTable();
    void initButtonsBar(SystemModelsDTO systemModelsDTO);
    void registerListener(KitSelectionListener listener);
    Button getPreviousKitButton();
    Button getNextKitButton();
    Button getSaveButton();
    void previousKit();
    void nextKit();
  }

  private IAdminSaveComponentsView view;
  private SystemModelsDTO modelsDTO;

  private Map<CarKitModel, List<CarComponentModel>> componentsMap;

  @Override
  public void bind(IAdminSaveComponentsView view) {
    this.view = view;

    this.componentsMap = new HashMap<>();

    view.getPreviousKitButton().setOnAction(event -> view.previousKit());

    view.getNextKitButton().setOnAction(event -> view.nextKit());

    view.getSaveButton().setOnAction(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_SAVE_COMPONENTS_VALIDATOR_VIEW);
      if (component != null) {
        NodeDialog dialog = new NodeDialog("Validare selectie", "Ok", component.getView().asNode());
        EventBus.fireEvent(new ShowDialogEvent(dialog));
        List<CarComponentModel> carComponents = collectCarComponents();
        EventBus.fireEvent(new InjectPreviewCarComponentsEvent(carComponents));
        dialog.getConfirmationButton().setOnAction(event1 -> {
          dialog.close();

        });
      }
    });

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> loadSystemData());
  }

  @Override
  public void onKitSelection(CarKitModel catKit) {
    if (modelsDTO == null) {
      return;
    } else if (catKit == null) {
      view.getCarComponentTable().getItems().clear();
      return;
    }

    ObservableList<CarComponentModel> items = view.getCarComponentTable().getItems();
    items.clear();

    List<CarComponentModel> carComponents = componentsMap.get(catKit);
    if (carComponents == null) {
      carComponents = new ArrayList<>();
      List<CarSubkitModel> carSubkitByKit = modelsDTO.getCarSubkitByKit(catKit);
      for (CarSubkitModel carSubkit : carSubkitByKit) {
        carComponents.add(new CarComponentModel(-1, 1, carSubkit.getId(), carSubkit.getName(), "", "", ""));
      }
      componentsMap.put(catKit, carComponents);
    }
    items.addAll(carComponents);
  }

  private void saveComponents(List<CarComponentModel> carComponents) {
    if (carComponents == null || carComponents.isEmpty()) {
      NotificationsUtil.showWarningNotification("Atentie", "Nici o componenta nu a fost inregistrata corect", 5);
      return;
    }

  }

  private List<CarComponentModel> collectCarComponents() {
    List<CarComponentModel> result = new ArrayList<>();
    for (CarKitModel carKit : componentsMap.keySet()) {
      result.addAll(componentsMap.get(carKit));
    }
    return result;
  }

  private List<CarComponentModel> filterValidComponents(List<CarComponentModel> carComponents) {
    List<CarComponentModel> result = new ArrayList<>();
    for (CarComponentModel carComponent : carComponents) {
      if (ModelValidator.isValidCarComponent(carComponent))
        result.add(carComponent);
    }
    return result;
  }

  private void loadSystemData() {
    EventBus.fireEvent(new GetAllSystemDefinedModelsEvent(models -> {
      this.modelsDTO = models;
      view.initButtonsBar(modelsDTO);
      view.registerListener(this);
    }));
  }
}
