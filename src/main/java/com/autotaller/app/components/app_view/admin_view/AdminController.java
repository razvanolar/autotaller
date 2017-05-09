package com.autotaller.app.components.app_view.admin_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.scene.Node;

/**
 * Created by razvanolar on 02.05.2017
 */
public class AdminController implements Controller<AdminController.IAdminView> {

  public interface IAdminView extends View {
    Node getDefineModelMenu();
    Node getAddCarMenu();
    Node getAddComponentMenu();
    Node getManageUsersMenu();
  }

  private Repository repository;

  @Override
  public void bind(IAdminView view) {

    view.getDefineModelMenu().setOnMouseClicked(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_DEFINE_MODEL_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView()));
        EventBus.fireEvent(new BindLastViewEvent());
        EventBus.fireEvent(new InjectRepoToAdminEvent(repository));
      }
    });

    view.getAddCarMenu().setOnMouseClicked(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_REGISTER_CAR_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView()));
        EventBus.fireEvent(new BindLastViewEvent());
        EventBus.fireEvent(new InjectRepoToAdminEvent(repository));
      }
    });

    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> this.repository = event.getRepository());
  }
}
