package com.autotaller.app;

import com.autotaller.app.components.loading_view.LoadingView;
import com.autotaller.app.components.utils.MaskableView;
import com.autotaller.app.events.app_view.*;
import com.autotaller.app.events.login_view.ShowLoginScreenEvent;
import com.autotaller.app.events.login_view.ShowLoginScreenEventHandler;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.MaskViewEventHandler;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEventHandler;
import com.autotaller.app.events.test_connection.TestConnectionEvent;
import com.autotaller.app.events.test_connection.TestConnectionFailedEvent;
import com.autotaller.app.events.test_connection.TestConnectionFailedEventHandler;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEventHandler;
import com.autotaller.app.events.view_stack.BackToPreviousViewEvent;
import com.autotaller.app.events.view_stack.BackToPreviousViewEventHandler;
import com.autotaller.app.utils.Component;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.View;
import com.autotaller.app.utils.factories.ComponentFactory;
import com.autotaller.app.utils.resources.StyleProvider;
import com.jfoenix.controls.JFXDialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Stack;

public class AutoTallerApplication extends Application {

  private Stage loadingScreenStage;
  private Scene loadingScreenScene;
  private BorderPane loadingScreenPane;

  private Stage primaryStage;
  private Scene primaryScene;
  private StackPane stackContainer;
  private BorderPane primaryContainer;

  private Stack<View> viewStack;

  private String defaultThemePath;
  private JFXDialog activeDialog;

  @Override
  public void init() throws Exception {
    viewStack = new Stack<>();
    defaultThemePath = StyleProvider.getDefaultTheme();
    loadingScreenPane = new BorderPane();
    loadingScreenScene = new Scene(loadingScreenPane, 700, 400);
    loadingScreenScene.getStylesheets().add(defaultThemePath);

    primaryContainer = new BorderPane();
    stackContainer = new StackPane(primaryContainer);
    primaryScene = new Scene(stackContainer, 700, 400);
    primaryScene.getStylesheets().add(defaultThemePath);

    // create and bind the app controller
    (new AutoTallerController()).bind(null);

    // init the login view handler
    EventBus.addHandler(ShowLoginScreenEvent.TYPE, (ShowLoginScreenEventHandler) handler -> {
      if (loadingScreenStage != null && loadingScreenStage.isShowing()) {
        loadingScreenStage.close();
        loadingScreenStage = null;
      }
      EventBus.removeHandlersByType(TestConnectionFailedEvent.TYPE);
      initAndShowLoginScreen();
    });

    // init the app view handler
    EventBus.addHandler(ShowAppViewEvent.TYPE, (ShowAppViewEventHandler) event -> initAndShowAppScreen(event.getAppView()));

    initStackViewHandlers();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    initAndShowLoadingScreen();
  }

  private void initAndShowLoadingScreen() {
    loadingScreenStage = new Stage(StageStyle.UNDECORATED);
    loadingScreenStage.setScene(loadingScreenScene);

    LoadingView loadingView = new LoadingView();
    BorderPane pane = new BorderPane(loadingView.asNode());

    pane.prefWidthProperty().bind(loadingScreenStage.widthProperty());
    pane.prefHeightProperty().bind(loadingScreenStage.heightProperty());

    loadingScreenPane.setCenter(pane);
    loadingScreenStage.show();
    EventBus.fireEvent(new TestConnectionEvent());
    EventBus.addHandler(TestConnectionFailedEvent.TYPE, (TestConnectionFailedEventHandler) handler -> loadingView.showFailedConnectionTest());
  }

  private void initStackViewHandlers() {
    EventBus.addHandler(AddViewToStackEvent.TYPE, (AddViewToStackEventHandler) event -> {
      if (event.getView() != null) {
        primaryContainer.setCenter(event.getView().asNode());
        viewStack.add(event.getView());
      }
    });

    EventBus.addHandler(BackToPreviousViewEvent.TYPE, (BackToPreviousViewEventHandler) event -> {
      if (viewStack.size() > 1) {
        viewStack.pop();
        primaryContainer.setCenter(viewStack.peek().asNode());
      }
    });

    EventBus.addHandler(ShowDialogEvent.TYPE, (ShowDialogEventHandler) event -> {
      activeDialog = event.getDialog();
      activeDialog.setDialogContainer(stackContainer);
      activeDialog.show();
    });

    EventBus.addHandler(HideDialogEvent.TYPE, (HideDialogEventHandler) event -> {
      if (activeDialog != null)
        activeDialog.close();
    });
  }

  private void initAndShowLoginScreen() {
    Component component = ComponentFactory.createComponent(ComponentType.LOGIN_VIEW);
    if (component != null) {
      viewStack.clear();
      viewStack.add(component.getView());
      primaryContainer.setCenter(component.getView().asNode());
    }
    primaryStage.setTitle("Auto Taller Cars Trader");
    primaryStage.setScene(primaryScene);
    primaryStage.setMaximized(true);
    primaryStage.show();
  }

  private void initAndShowAppScreen(View appView) {
    viewStack.clear();
    viewStack.add(appView);
    primaryContainer.setCenter(appView.asNode());
    Component menuBar = ComponentFactory.createComponent(ComponentType.APP_MENU_BAR);
    if (menuBar != null)
      primaryContainer.setTop(menuBar.getView().asNode());

    EventBus.addHandler(MaskViewEvent.TYPE, (MaskViewEventHandler) event -> {
      if (!viewStack.isEmpty()) {
        View view = viewStack.peek();
        if (view instanceof MaskableView) {
          MaskableView maskableView = (MaskableView) view;
          maskableView.maskView(event.getMessage());
        }
      }
    });

    EventBus.addHandler(UnmaskViewEvent.TYPE, (UnmaskViewEventHandler) event -> {
      if (!viewStack.isEmpty()) {
        View view = viewStack.peek();
        if (view instanceof MaskableView) {
          MaskableView maskableView = (MaskableView) view;
          maskableView.unmaskView();
        }
      }
    });
  }

  public static void main(String[] args) {
    try {
      launch(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
