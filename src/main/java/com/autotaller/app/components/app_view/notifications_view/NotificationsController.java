package com.autotaller.app.components.app_view.notifications_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.admin_view.GetSimpleSellModelsEvent;
import com.autotaller.app.events.app_view.admin_view.GetSimpleSellModelsEventHandler;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.model.notifications.SimpleSellModel;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.SellModelStatus;
import com.autotaller.app.utils.View;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by razvanolar on 04.06.2017
 */
public class NotificationsController implements Controller<NotificationsController.INotificationsView> {

  public interface INotificationsView extends View {
    TableView<SimpleSellModel> getSellModelTable();
  }

  private INotificationsView view;
  private Repository repository;

  @Override
  public void bind(INotificationsView view) {
    this.view = view;

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> this.repository = event.getRepository());

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> load());

    initHandlers();
  }

  private void load() {
    EventBus.fireEvent(new GetSimpleSellModelsEvent(SellModelStatus.UNSOLVED, sellModels -> {
      ObservableList<SimpleSellModel> items = view.getSellModelTable().getItems();
      items.clear();
      items.addAll(sellModels);
    }));
  }

  private void initHandlers() {
    EventBus.addHandler(GetSimpleSellModelsEvent.TYPE, (GetSimpleSellModelsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Incarcare Notificari Vanzari"));
        Thread thread = new Thread(() -> {
          try {
            List<SimpleSellModel> simpleSellModels = repository.getSimpleSellModels(event.getRetrieveStatus());
            Platform.runLater(() -> {
              EventBus.fireEvent(new UnmaskViewEvent());
              event.getCallback().call(simpleSellModels);
            });
          } catch (Exception e) {
            //TODO show error dialog
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO show error dialog
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
      }
    });
  }
}
