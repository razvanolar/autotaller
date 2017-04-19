package com.autotaller.app.components.app_view;

import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

/**
 * Created by razvanolar on 14.04.2017
 */
public class AutoTallerMenuBarView implements AutoTallerMenuBarController.IAutoTallerMenuBarView {

  private MenuBar menuBar;
  private Menu fileMenu;
  private Menu settingMenu;
  private Menu helpMenu;

  public AutoTallerMenuBarView() {
    init();
  }

  private void init() {
    fileMenu = new Menu("Fisier");
    settingMenu = new Menu("Setari");
    helpMenu = new Menu("Ajutor");
    menuBar = new MenuBar(fileMenu, settingMenu, helpMenu);
  }

  @Override
  public Node asNode() {
    return menuBar;
  }
}
