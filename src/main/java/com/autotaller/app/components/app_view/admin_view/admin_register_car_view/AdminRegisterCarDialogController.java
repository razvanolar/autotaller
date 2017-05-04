package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.utils.Controller;
import com.autotaller.app.utils.DialogController;
import com.autotaller.app.utils.View;
import javafx.scene.control.Button;

import java.util.List;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminRegisterCarDialogController implements Controller<AdminRegisterCarDialogController.IAdminRegisterCarDialogView>, DialogController {

  public interface IAdminRegisterCarDialogView extends View {

  }

  private IAdminRegisterCarDialogView view;
  private Button actionButton;

  private List<CarMakeModel> carMakes;
  private List<CarTypeModel> carModels;


  @Override
  public void bind(IAdminRegisterCarDialogView view) {
    this.view = view;
  }

  @Override
  public void injectActionButton(Button actionButton) {
    this.actionButton = actionButton;
  }
}
