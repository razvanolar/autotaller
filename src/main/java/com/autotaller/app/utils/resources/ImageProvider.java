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

  public static Image clientsMenuIcon() {
    return getImage("icons/clients_menu_icon.png");
  }

  public static Image adminMenuIcon() {
    return getImage("icons/admin_menu_icon.png");
  }

  public static Image notificationMenuIcon() {
    return getImage("icons/notification_menu_icon.png");
  }

  public static Image settingsMenuIcon() {
    return getImage("icons/settings_menu_icon.png");
  }

  public static Image exitMenuIcon() {
    return getImage("icons/exit_menu_icon.png");
  }

  public static Image plusIcon() {
    return getImage("icons/plus.png");
  }

  public static Image minusIcon() {
    return getImage("icons/minus.png");
  }

  public static Image backIcon() {
    return getImage("icons/back.png");
  }

  public static Image nextIcon() {
    return getImage("icons/next.png");
  }

  public static Image exitIcon() {
    return getImage("icons/exit.png");
  }

  public static Image addIcon() {
    return getImage("icons/add.png");
  }

  public static Image editIcon() {
    return getImage("icons/edit.png");
  }

  public static Image deleteIcon() {
    return getImage("icons/delete.png");
  }

  public static Image filterIcon() {
    return getImage("icons/filter.png");
  }

  public static Image componentsIcon() {
    return getImage("icons/components.png");
  }

  public static Image infoIcon() {
    return getImage("icons/info.png");
  }

  public static Image detailsIcon() {
    return getImage("icons/details.png");
  }

  public static Image imageIcon() {
    return getImage("icons/image.png");
  }

  public static Image descriptionIcon() {
    return getImage("icons/description.png");
  }

  public static Image uploadIcon() {
    return getImage("icons/upload.png");
  }

  public static Image binIcon() {
    return getImage("icons/bin.png");
  }

  public static Image galleryIcon() {
    return getImage("icons/gallery.png");
  }

  public static Image cameraIcon() {
    return getImage("icons/camera.png");
  }

  public static Image chipSetCloseIcon() {
    return getImage("icons/chip_set_close_icon.png");
  }

  public static Image checkIcon() {
    return getImage("icons/check.png");
  }

  public static Image lockIcon() {
    return getImage("icons/lock.png");
  }

  public static Image getImage(String file) {
    String path = ImageProvider.class.getResource(file).getPath();
    if (path.contains("!")) {
      path = path.substring(path.lastIndexOf("!") + 1);
      return new Image(path);
    } else {
      return new Image("file:///" + path.replace("\\", "/"));
    }
  }

  public static Image getImageFromPath(String path) {
    return new Image("file:///" + path.replace("\\", "/"));
  }
}
