package com.autotaller.app.components.app_view.admin_view.admin_register_car_view.utils;

import com.autotaller.app.utils.resources.ImageProvider;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;

/**
 * Created by razvanolar on 28.05.2017
 */
public class AdminCarTableViewContextMenu {

  private ContextMenu contextMenu;
  private MenuItem editMenuItem;
  private MenuItem deleteMenuItem;
  private MenuItem detailsMenuItem;
  private MenuItem componentsMenuItem;
  private MenuItem galleryMenuItem;

  public AdminCarTableViewContextMenu() {
    init();
  }

  private void init() {
    editMenuItem = new MenuItem("Editeaza", new ImageView(ImageProvider.editIcon()));
    deleteMenuItem = new MenuItem("Sterge", new ImageView(ImageProvider.deleteIcon()));
    detailsMenuItem = new MenuItem("Detalii", new ImageView(ImageProvider.detailsIcon()));
    componentsMenuItem = new MenuItem("Componente", new ImageView(ImageProvider.componentsIcon()));
    galleryMenuItem = new MenuItem("Galerie", new ImageView(ImageProvider.galleryIcon()));

    contextMenu = new ContextMenu();
    contextMenu.getItems().add(editMenuItem);
    contextMenu.getItems().add(deleteMenuItem);
    contextMenu.getItems().add(new SeparatorMenuItem());
    contextMenu.getItems().add(detailsMenuItem);
    contextMenu.getItems().add(componentsMenuItem);
    contextMenu.getItems().add(new SeparatorMenuItem());
    contextMenu.getItems().add(galleryMenuItem);
  }

  public MenuItem getEditMenuItem() {
    return editMenuItem;
  }

  public MenuItem getDeleteMenuItem() {
    return deleteMenuItem;
  }

  public MenuItem getDetailsMenuItem() {
    return detailsMenuItem;
  }

  public MenuItem getComponentsMenuItem() {
    return componentsMenuItem;
  }

  public MenuItem getGalleryMenuItem() {
    return galleryMenuItem;
  }

  public ContextMenu getContextMenu() {
    return contextMenu;
  }
}
