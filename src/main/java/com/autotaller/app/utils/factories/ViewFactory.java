package com.autotaller.app.utils.factories;

import com.autotaller.app.components.app_view.AutoTallerMenuBarView;
import com.autotaller.app.components.app_view.admin_view.AdminView;
import com.autotaller.app.components.login_view.login.LoginView;
import com.autotaller.app.components.login_view.sign_up.SignUpView;
import com.autotaller.app.utils.ComponentTypes;
import com.autotaller.app.utils.View;

/**
 * Created by razvanolar on 11.04.2017
 */
public class ViewFactory {

  public View createView(ComponentTypes type) {
    switch (type) {
      case LOGIN_VIEW:
        return new LoginView();
      case SIGN_UP_VIEW:
        return new SignUpView();
      case APP_MENU_BAR:
        return new AutoTallerMenuBarView();
      case ADMIN_VIEW:
        return new AdminView();
    }
    return null;
  }
}
