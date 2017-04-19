package com.autotaller.app.utils.resources;

import javafx.scene.image.Image;

/**
 *
 * Created by razvanolar on 24.01.2016.
 */
public class ImageProvider {

  public static Image authenticationIcon() {
    return getImage("icons/user_authentication.png");
  }

  public static Image signUpIcon() {
    return getImage("icons/sign_up_icon.png");
  }

  public static Image logo() {
    return getImage("icons/logo.jpg");
  }

  public static Image carMenuIcon() {
    return getImage("icons/car_menu_icon.png");
  }

  public static Image componentMenuIcon() {
    return getImage("icons/component_menu_icon.png");
  }

  public static Image searchMenuIcon() {
    return getImage("icons/search_menu_icon.png");
  }

  public static Image adminMenuIcon() {
    return getImage("icons/admin_menu_icon.png");
  }

  public static Image settingsMenuIcon() {
    return getImage("icons/settings_menu_icon.png");
  }

  public static Image exitMenuIcon() {
    return getImage("icons/exit_menu_icon.png");
  }

  private static Image getImage(String file) {
    String path = ImageProvider.class.getResource(file).getPath();
    if (path.contains("!")) {
      path = path.substring(path.lastIndexOf("!") + 1);
      return new Image(path);
    } else {
    return new Image("file:///" + path.replace("\\", "/"));
    }
  }
}
