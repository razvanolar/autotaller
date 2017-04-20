package com.autotaller.app.events.app_view.admin_view;

import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.event.Event;
import com.autotaller.app.utils.event.EventType;

/**
 * Created by razvanolar on 20.04.2017
 */
public class InjectRepoToAdminEvent extends Event<InjectRepoToAdminEventHandler> {

  public static EventType<InjectRepoToAdminEventHandler> TYPE = new EventType<>();

  private Repository repository;

  public InjectRepoToAdminEvent(Repository repository) {
    this.repository = repository;
  }

  public Repository getRepository() {
    return repository;
  }

  @Override
  public EventType getAssociatedType() {
    return TYPE;
  }

  @Override
  public void dispatch(InjectRepoToAdminEventHandler handler) {
    handler.onInjectRepoToAdminEvent(this);
  }
}
