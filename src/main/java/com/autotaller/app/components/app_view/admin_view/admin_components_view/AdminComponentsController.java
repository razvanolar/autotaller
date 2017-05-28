package com.autotaller.app.components.app_view.admin_view.admin_components_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.utils.NotificationsUtil;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.GetCarComponentsEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.*;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.events.view_stack.BackToPreviousViewEvent;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.callbacks.EmptyCallback;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;

import java.util.List;

/**
 * Created by razvanolar on 13.05.2017
 */
public class AdminComponentsController implements Controller<AdminComponentsController.IAdminComponentsView> {

  public interface IAdminComponentsView extends View {
    Button getAddComponentButton();
    Button getEditComponentButton();
    Button getDeleteComponentButton();
    ToggleButton getFilterComponentsButton();
    Button getDetailsButton();
    TableView<CarComponentModel> getCarComponentsTable();
  }

  private Repository repository;
  private int injectedCarId = -1;
  private IAdminComponentsView view;

  private List<CarComponentModel> carComponents;

  @Override
  public void bind(IAdminComponentsView view) {
    this.view = view;

    view.getAddComponentButton().setOnAction(event -> EventBus.fireEvent(new ShowSaveCarComponentsEvent()));

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> {
      this.repository = event.getRepository();
      initHandlers();
    }, true);

    EventBus.addHandler(ShowSaveCarComponentsEvent.TYPE, (ShowSaveCarComponentsEventHandler) event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_SAVE_COMPONENTS_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.ADMIN_SAVE_COMPONENTS_VIEW.getTitle()));
        EventBus.fireEvent(new InjectCarInformationEvent(injectedCarId));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    }, true);

    EventBus.addHandler(InjectCarInformationEvent.TYPE, (InjectCarInformationEventHandler) event -> this.injectedCarId = event.getCarId(), true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> load(event.getCallback()), true);
  }

  private void initHandlers() {
    EventBus.addHandler(AddCarComponentsEvent.TYPE, (AddCarComponentsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Adaugare componente..."));
        Thread thread = new Thread(() -> {
          try {
            repository.addComponents(event.getCarComponents());
            Platform.runLater(() -> {
              NotificationsUtil.showInfoNotification("Info", "Componentele au fost adaugate cu succes", 5);
              EventBus.fireEvent(new UnmaskViewEvent());
              EventBus.fireEvent(new BackToPreviousViewEvent());
              load(null);
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
        NotificationsUtil.showErrorNotification("Eroare", "A aparut o eroare la adaugarea componentelor", -1);
      }
    }, true);
  }

  private void load(EmptyCallback callback) {
    if (injectedCarId <= 0) {
      EventBus.fireEvent(new GetCarComponentsEvent(components -> {
        loadComponents(components);
        if (callback != null)
          callback.call();
      }));
    } else {
      EventBus.fireEvent(new GetCarComponentsByCarIdEvent(injectedCarId, components -> {
        loadComponents(components);
        if (callback != null)
          callback.call();
      }));
    }
  }

  private void loadComponents(List<CarComponentModel> carComponents) {
    this.carComponents = carComponents;
    ObservableList<CarComponentModel> items = view.getCarComponentsTable().getItems();
    items.clear();
    items.addAll(carComponents);
  }
}
