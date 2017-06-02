package com.autotaller.app.utils;

/**
 * Created by razvanolar on 11.04.2017
 */
public enum ComponentType {
  LOGIN_VIEW("Login"),
  SIGN_UP_VIEW("Inregistrare"),
  APP_MENU_BAR("Meniu"),
  ADMIN_VIEW("Admin"),
  ADMIN_DEFINE_CONTEXT_VIEW("Definire Context"),
  ADMIN_CAR_MAKE_VIEW("Marci"),
  ADD_CAR_MAKE_VIEW("Adaugare Marca"),
  ADMIN_CAR_MODEL_VIEW("Modele"),
  ADMIN_CAR_KIT_VIEW("Ansamble"),
  ADMIN_CAR_SUBKIT_VIEW("Sub-Ansamble"),
  ADMIN_REGISTER_CAR_VIEW("Administrare Masini"),
  ADMIN_SAVE_CAR_VIEW("Adaugare Masina"),
  ADMIN_COMPONENTS_VIEW("Administrare Componente"),
  ADMIN_SAVE_COMPONENTS_VIEW("Adaugare Componente"),
  ADMIN_SAVE_COMPONENTS_VALIDATOR_VIEW("Validare Componente"),
  ADMIN_STATISTICS_VIEW("Statistici"),
  SEARCH_CAR_MAKE_VIEW("Cautare Marca"),
  SEARCH_CAR_TYPE_VIEW("Cautare Model"),
  SEARCH_CAR_VIEW("Cautare Masina"),
  SHOW_CAR_KITS_VIEW("Prezentare Ansamble"),
  SEARCH_CAR_COMPONENTS_VIEW("Cautare Componente");

  String title;

  ComponentType(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
