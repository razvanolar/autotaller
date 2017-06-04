package com.autotaller.app.events.login_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 03.06.2017
 */
public interface GetActiveUserEventHandler extends EventHandler {
  void onGetActiveUserEvent(GetActiveUserEvent event);
}
