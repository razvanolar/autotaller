package com.autotaller.app.components.utils;

import com.autotaller.app.utils.View;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.StyleProvider;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 02.06.2017
 */
public class ChipSet implements View {

  private HBox container;
  private String text;

  public ChipSet(String text) {
    this.text = text;
    init();
  }

  private void init() {
    container = new HBox(10, new Text(text), new ImageView(ImageProvider.chipSetCloseIcon()));
    container.setPadding(new Insets(5, 5, 5, 10));
    container.getStyleClass().add(StyleProvider.CHIP_SET_STYLE_CLASS);
  }

  @Override
  public Node asNode() {
    return container;
  }
}
