package com.autotaller.app.components.login_view.login;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.login_view.AuthenticationFailedEvent;
import com.autotaller.app.events.login_view.AuthenticationFailedEventHandler;
import com.autotaller.app.events.login_view.TestCredentialsEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 10.04.2017
 */
public class LoginController implements Controller<LoginController.ILoginView> {

  public interface ILoginView extends View {
    void showAuthenticationFailedLabel();
    Button getLoginButton();
    TextField getUsernameTextField();
    TextField getPasswordField();
    Text getSignUpLabel();
    Text getForgotPasswordLabel();
  }

  private ILoginView view;

  @Override
  public void bind(ILoginView view) {
    this.view = view;

    view.getLoginButton().setOnAction(event -> {
      if (!isValidSelection()) {
        view.showAuthenticationFailedLabel();
      } else {
        EventBus.fireEvent(new TestCredentialsEvent(view.getUsernameTextField().getText(), view.getPasswordField().getText()));
      }
    });

    view.getSignUpLabel().setOnMouseClicked(event -> {
      Component component = ComponentFactory.createComponent(ComponentTypes.SIGN_UP_VIEW);
      if (component != null)
        EventBus.fireEvent(new AddViewToStackEvent(component.getView()));
    });

    view.getForgotPasswordLabel().setOnMouseClicked(event -> {

    });

    EventBus.addHandler(AuthenticationFailedEvent.TYPE, (AuthenticationFailedEventHandler) event -> view.showAuthenticationFailedLabel());

    view.getUsernameTextField().setText("admin");
    view.getPasswordField().setText("admin");
  }

  private boolean isValidSelection() {
    return !StringValidator.isNullOrEmpty(view.getUsernameTextField().getText()) &&
            !StringValidator.isNullOrEmpty(view.getPasswordField().getText());
  }
}
