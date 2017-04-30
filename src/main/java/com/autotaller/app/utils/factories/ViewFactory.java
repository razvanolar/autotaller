package com.autotaller.app.utils.factories;

import com.autotaller.app.components.app_view.AutoTallerMenuBarView;
import com.autotaller.app.components.app_view.admin_view.AdminView;
import com.autotaller.app.components.app_view.admin_view.admin_car_kit_view.AdminCarKitView;
import com.autotaller.app.components.app_view.admin_view.admin_car_make_view.AddCarMakeDialogView;
import com.autotaller.app.components.app_view.admin_view.admin_car_make_view.AdminCarMakeView;
import com.autotaller.app.components.app_view.admin_view.admin_car_model_view.AdminCarModelView;
import com.autotaller.app.components.app_view.admin_view.admin_car_subkit_view.AdminCarSubkitView;
import com.autotaller.app.components.login_view.login.LoginView;
import com.autotaller.app.components.login_view.sign_up.SignUpView;
import com.autotaller.app.utils.ComponentType;
import com.autotaller.app.utils.View;

/**
 * Created by razvanolar on 11.04.2017
 */
public class ViewFactory {

  public View createView(ComponentType type) {
    switch (type) {
      case LOGIN_VIEW:
        return new LoginView();
      case SIGN_UP_VIEW:
        return new SignUpView();
      case APP_MENU_BAR:
        return new AutoTallerMenuBarView();
      case ADMIN_VIEW:
        return new AdminView();
      case ADMIN_CAR_MAKE_VIEW:
        return new AdminCarMakeView();
      case ADD_CAR_MAKE_VIEW:
        return new AddCarMakeDialogView();
      case ADMIN_CAR_MODEL_VIEW:
        return new AdminCarModelView();
      case ADMIN_CAR_KIT_VIEW:
        return new AdminCarKitView();
      case ADMIN_CAR_SUBKIT_VIEW:
        return new AdminCarSubkitView();
    }
    return null;
  }
}
