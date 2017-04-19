package com.autotaller.app.utils.event;

/**
 * Created by razvanolar on 29.12.2016
 */
public abstract class Event<T extends EventHandler> {

  public abstract EventType getAssociatedType();
  public abstract void dispatch(T handler);
}
