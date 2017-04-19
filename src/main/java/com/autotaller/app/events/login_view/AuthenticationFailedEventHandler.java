package com.autotaller.app.events.login_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 14.04.2017
 */
public interface AuthenticationFailedEventHandler extends EventHandler {
  void onAuthenticationFailedEvent(AuthenticationFailedEvent event);
}
