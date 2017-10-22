package com.autotaller.app.components.app_view.search_components_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.scene.Node;

public class SearchComponentsMainController implements Controller<SearchComponentsMainController.ISearchComponentsMainView> {

  public interface ISearchComponentsMainView extends View {
    Node getSubkitSearchNode();
  }

  @Override
  public void bind(ISearchComponentsMainView view) {

    view.getSubkitSearchNode().setOnMouseClicked(event -> {
      Component component = ComponentFactory.createComponent(ComponentType.SHOW_CAR_KITS_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.SHOW_CAR_KITS_VIEW.getTitle()));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    });
  }
}
