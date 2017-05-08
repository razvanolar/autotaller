package com.autotaller.app.components.utils;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * Created by razvanolar on 08.05.2017
 */
public class NotificationsUtil {

  /**
   * Display a notification popup in the bottom right corner of the screen
   * @param title notification title
   * @param message notification message
   * @param duration notification duration expressed in seconds
   *                 if a value less or equal to 0 is provided, the notification will not be automatically hide
   */
  public static void showInfoNotification(String title, String message, int duration) {
    Notifications notification = createNotification(title, message, duration);
    notification.setHeaderCheck();
    notification.show();
  }

  public static void showWarningNotification(String title, String message, int duration) {
    Notifications notification = createNotification(title, message, duration);
    notification.setHeaderWarning();
    notification.show();
  }

  public static void showErrorNotification(String title, String message, int duration) {
    Notifications notification = createNotification(title, message, duration);
    notification.setHeaderError();
    notification.show();
  }

  private static Notifications createNotification(String title, String message, int duration) {
    Notifications notification = Notifications.create()
            .title(title)
            .text(message)
            .position(Pos.BOTTOM_RIGHT)
            .graphic(null)
            .materiaDesignStyle();
    if (duration > 0)
      notification.hideAfter(Duration.seconds(duration));
    return notification;
  }
}
