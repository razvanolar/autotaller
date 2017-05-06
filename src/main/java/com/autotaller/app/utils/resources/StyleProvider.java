package com.autotaller.app.utils.resources;

/**
 * Created by razvanolar on 11.04.2017
 */
public class StyleProvider {

  public static final String LOADING_TEST_CONNECTION_TEXT_CLASS = "loadingText";
  public static final String LOADING_TEST_CONNECTION_FAILED_TEXT_CLASS = "failedConnectionTest";
  public static final String DEFAULT_TEXT_COLOR_CLASS = "defaultTextColor";
  public static final String ERROR_TEXT_COLOR_CLASS = "errorTextColor";
  public static final String DEFAULT_LINK_HOVER_CLASS = "defaultLinkBackground";
  public static final String DEFAULT_BUTTON_CLASS = "defaultButtonStyle";
  public static final String REMOVE_BUTTON_CLASS = "removeButtonStyle";
  public static final String MASK_VIEW_BACKGROUND_CLASS = "maskViewBackground";
  public static final String APP_MENU_BOX_CLASS = "appMenuBox";
  public static final String BUTTONS_CONTAINER_CLASS = "clearButtonsContainer";
  public static final String ADMIN_TOOLBAR_PANE_CLASS = "adminToolbarPaneBackground";
  public static final String ADMIN_SUB_TOOLBAR_PANE_CLASS = "adminSubToolbarPaneBackground";
  public static final String YEAR_VIEW_UNSELECTED_CLASS = "yearViewUnselectedBackground";
  public static final String YEAR_VIEW_SELECTED_CLASS = "yearViewSelectedBackground";

  public static final String CENTERED_TABLE_CELL_TEXT_CSS = "-fx-alignment: CENTER;";

  public static String getDefaultTheme() {
    String path = StyleProvider.class.getResource("styles/default_theme.css").getPath();
    return path.contains("!") ? path.substring(path.lastIndexOf("!") + 1) : "file:///" + path.replace("\\", "/");
  }
}
