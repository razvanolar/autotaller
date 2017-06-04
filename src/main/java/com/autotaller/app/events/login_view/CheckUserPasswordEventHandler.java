package com.autotaller.app.events.login_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 04.06.2017
 */
public interface CheckUserPasswordEventHandler extends EventHandler {
  void onCheckUserPasswordEvent(CheckUserPasswordEvent event);
}
