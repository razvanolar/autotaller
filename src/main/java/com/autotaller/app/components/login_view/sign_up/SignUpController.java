package com.autotaller.app.components.login_view.sign_up;

import com.autotaller.app.EventBus;
import com.autotaller.app.events.view_stack.BackToPreviousViewEvent;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.View;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 12.04.2017
 */
public class SignUpController implements Controller<SignUpController.ISignUpView> {

  public interface ISignUpView extends View {
    TextField getEmplyeeNameTextField();
    TextField getUsernameTextField();
    PasswordField getPasswordField();
    PasswordField getRe_passwordField();
    Text getBackLabel();
    Button getSignUpButton();
  }

  @Override
  public void bind(ISignUpView view) {

    view.getBackLabel().setOnMouseClicked(event -> EventBus.fireEvent(new BackToPreviousViewEvent()));
  }
}
