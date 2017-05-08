package com.autotaller.app.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by razvanolar on 20.04.2017
 */
public class CarTypeModel {

  private int id;
  private CarMakeModel carMake;
  private String name;
  private LocalDate from;
  private LocalDate to;
  private List<String> frameNames;

  private String frames;

  public CarTypeModel(int id, CarMakeModel carMake, String name, LocalDate from, LocalDate to, List<String> frameNames) {
    this.id = id;
    this.carMake = carMake;
    this.name = name;
    this.from = from;
    this.to = to;
    this.frameNames = frameNames;
    computeFramesString();
  }

  public int getId() {
    return id;
  }

  public CarMakeModel getCarMake() {
    return carMake;
  }

  public String getName() {
    return name;
  }

  public LocalDate getFrom() {
    return from;
  }

  public LocalDate getTo() {
    return to;
  }

  public List<String> getFrameNames() {
    return frameNames;
  }

  public String getFrames() {
    return frames;
  }

  public void setCarMake(CarMakeModel carMake) {
    this.carMake = carMake;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setFrom(LocalDate from) {
    this.from = from;
  }

  public void setTo(LocalDate to) {
    this.to = to;
  }

  public void setFrameNames(List<String> frameNames) {
    this.frameNames = frameNames;
    computeFramesString();
  }

  private void computeFramesString() {
    if (frameNames == null)
      return;
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < frameNames.size(); i++) {
      builder.append(frameNames.get(i));
      if (i < frameNames.size() - 1)
        builder.append(", ");
    }
    frames = builder.toString();
  }

  @Override
  public String toString() {
    return name + (frames != null && !frames.isEmpty() ? " (" + frames + ")" : "");
  }
}
