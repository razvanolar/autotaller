package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.utils.event.EventHandler;

/**
 * Created by razvanolar on 20.04.2017
 */
public interface InjectRepoToAdminEventHandler extends EventHandler {
  void onInjectRepoToAdminEvent(InjectRepoToAdminEvent event);
}
