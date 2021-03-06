package com.autotaller.app.components.app_view.admin_view.admin_components_view;

import com.autotaller.app.EventBus;
import com.autotaller.app.components.app_view.admin_view.admin_components_view.utils.AdminEditComponentView;
import com.autotaller.app.components.utils.NodeDialog;
import com.autotaller.app.components.utils.NotificationsUtil;
import com.autotaller.app.components.utils.SimpleDialog;
import com.autotaller.app.components.utils.YesNoDialog;
import com.autotaller.app.events.app_view.BindLastViewEvent;
import com.autotaller.app.events.app_view.BindLastViewEventHandler;
import com.autotaller.app.events.app_view.ShowDialogEvent;
import com.autotaller.app.events.app_view.admin_view.GetCarComponentsEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEvent;
import com.autotaller.app.events.app_view.admin_view.InjectRepoToAdminEventHandler;
import com.autotaller.app.events.app_view.admin_view.admin_car_components.*;
import com.autotaller.app.events.mask_view.MaskViewEvent;
import com.autotaller.app.events.mask_view.UnmaskViewEvent;
import com.autotaller.app.events.view_stack.AddViewToStackEvent;
import com.autotaller.app.events.view_stack.BackToPreviousViewEvent;
import com.autotaller.app.model.CarComponentModel;
import com.autotaller.app.repository.Repository;
import com.autotaller.app.utils.*;
import com.autotaller.app.utils.callbacks.EmptyCallback;
import com.autotaller.app.utils.factories.ComponentFactory;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Created by razvanolar on 13.05.2017
 */
public class AdminComponentsController implements Controller<AdminComponentsController.IAdminComponentsView> {

  public interface IAdminComponentsView extends View {
    Button getAddComponentButton();
    Button getEditComponentButton();
    Button getDeleteComponentButton();
    ToggleButton getFilterComponentsButton();
    Button getDetailsButton();
    TableView<CarComponentModel> getCarComponentsTable();
    Text getPagesCounterLabel();
    Button getPreviousPageButton();
    Button getNextPageButton();
    void hidePaginatingComponents();
  }

  private static int ENTRIES_PER_PAGE = 100;

  private Repository repository;
  private int injectedCarId = -1;
  private IAdminComponentsView view;

  private List<CarComponentModel> carComponents;
  private int componentsCount;
  private int totalPages;
  private int currentPage;

  @Override
  public void bind(IAdminComponentsView view) {
    this.view = view;

    view.getAddComponentButton().setOnAction(event -> EventBus.fireEvent(new ShowSaveCarComponentsEvent()));

    view.getEditComponentButton().setOnAction(event -> onEditComponentEvent(view.getCarComponentsTable().getSelectionModel().getSelectedItem()));

    view.getDeleteComponentButton().setOnAction(event -> onDeleteComponentEvent(view.getCarComponentsTable().getSelectionModel().getSelectedItem()));

    view.getPreviousPageButton().setOnAction(event -> onPreviousPageEvent());

    view.getNextPageButton().setOnAction(event -> onNextPageEvent());


    EventBus.addHandler(InjectRepoToAdminEvent.TYPE, (InjectRepoToAdminEventHandler) event -> {
      this.repository = event.getRepository();
      initHandlers();
    }, true);

    EventBus.addHandler(ShowSaveCarComponentsEvent.TYPE, (ShowSaveCarComponentsEventHandler) event -> {
      Component component = ComponentFactory.createComponent(ComponentType.ADMIN_SAVE_COMPONENTS_VIEW);
      if (component != null) {
        EventBus.fireEvent(new AddViewToStackEvent(component.getView(), ComponentType.ADMIN_SAVE_COMPONENTS_VIEW.getTitle()));
        EventBus.fireEvent(new InjectCarInformationEvent(injectedCarId));
        EventBus.fireEvent(new BindLastViewEvent());
      }
    }, true);

    EventBus.addHandler(InjectCarInformationEvent.TYPE, (InjectCarInformationEventHandler) event -> this.injectedCarId = event.getCarId(), true);

    EventBus.addHandler(BindLastViewEvent.TYPE, (BindLastViewEventHandler) event -> load(event.getCallback(), true), true);
  }

  private void onEditComponentEvent(CarComponentModel carComponent) {
    if (carComponent == null) {
      EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Te rugam selecteaza o componenta")));
      return;
    }

    AdminEditComponentView editView = new AdminEditComponentView(carComponent.duplicate());
    NodeDialog dialog = new NodeDialog("Editare Componenta", "Editeaza", editView.asNode());
    EventBus.fireEvent(new ShowDialogEvent(dialog));
    dialog.getConfirmationButton().setOnAction(event -> {
      YesNoDialog yesNoDialog = new YesNoDialog("Confirmare", "Sigur doresti sa editezi aceasta componenta?");
      EventBus.fireEvent(new ShowDialogEvent(yesNoDialog));
      yesNoDialog.getYesButton().setOnAction(event1 -> {
        CarComponentModel component = editView.getCarComponent();
        if (!ModelValidator.isValidCarComponent(component)) {
          EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Unele campuri nu sunt completate corect")));
          return;
        }
        EventBus.fireEvent(new EditCarComponentEvent(component));
        yesNoDialog.close();
        dialog.close();
      });
    });
  }

  private void onDeleteComponentEvent(CarComponentModel carComponent) {
    if (carComponent == null) {
      EventBus.fireEvent(new ShowDialogEvent(new SimpleDialog("Atentie", "Ok", "Te rugam selecteaza o componenta")));
      return;
    }

    YesNoDialog dialog = new YesNoDialog("Atentie", "Esti sigur ca vrei sa stergi componenta: " + carComponent.getName() + "?");
    EventBus.fireEvent(new ShowDialogEvent(dialog));
    dialog.getYesButton().setOnAction(event -> {
      EventBus.fireEvent(new DeleteCarComponentEvent(carComponent));
      dialog.close();
    });
  }

  private void onPreviousPageEvent() {
    if (currentPage <= 1) {
      return;
    }
    currentPage--;
    updatePageCounter();
    load(null, false);
  }

  private void onNextPageEvent() {
    if (currentPage >= totalPages) {
      return;
    }
    currentPage++;
    updatePageCounter();
    load(null, false);
  }

  private void initHandlers() {
    EventBus.addHandler(AddCarComponentsEvent.TYPE, (AddCarComponentsEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Adaugare componente..."));
        Thread thread = new Thread(() -> {
          try {
            repository.addComponents(event.getCarComponents());
            Platform.runLater(() -> {
              NotificationsUtil.showInfoNotification("Info", "Componentele au fost adaugate cu succes", 5);
              EventBus.fireEvent(new UnmaskViewEvent());
              EventBus.fireEvent(new BackToPreviousViewEvent());
              load(null, true);
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
        NotificationsUtil.showErrorNotification("Eroare", "A aparut o eroare la adaugarea componentelor", -1);
      }
    }, true);

    EventBus.addHandler(EditCarComponentEvent.TYPE, (EditCarComponentEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Editare componenta..."));
        Thread thread = new Thread(() -> {
          try {
            repository.editComponent(event.getCarComponent());
            Platform.runLater(() -> {
              NotificationsUtil.showInfoNotification("Info", "Componenta a fost editata cu succes", 5);
              EventBus.fireEvent(new UnmaskViewEvent());
              load(null, false);
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
        NotificationsUtil.showErrorNotification("Eroare", "A aparut o eroare la editarea componentei", -1);
      }
    }, true);

    EventBus.addHandler(DeleteCarComponentEvent.TYPE, (DeleteCarComponentEventHandler) event -> {
      try {
        EventBus.fireEvent(new MaskViewEvent("Stergere componenta..."));
        Thread thread = new Thread(() -> {
          try {
            repository.deleteComponent(event.getCarComponent());
            Platform.runLater(() -> {
              NotificationsUtil.showInfoNotification("Info", "Componenta a fost stearsa cu succes", 5);
              EventBus.fireEvent(new UnmaskViewEvent());
              load(null, true);
            });
          } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
            Platform.runLater(() -> EventBus.fireEvent(new UnmaskViewEvent()));
          }
        });
        thread.start();
      } catch (Exception e) {
        //TODO handle exception
        e.printStackTrace();
        EventBus.fireEvent(new UnmaskViewEvent());
        NotificationsUtil.showErrorNotification("Eroare", "A aparut o eroare la stergerea componentei", -1);
      }
    }, true);
  }

  private void load(EmptyCallback callback, boolean resetPageCounter) {
    if (injectedCarId <= 0) {
      if (resetPageCounter) {
        EventBus.fireEvent(new GetCarComponentsCountEvent(componentsCount -> {
          if (componentsCount == 0) {
            return;
          }
          this.componentsCount = componentsCount;
          totalPages = computePagesNumber(componentsCount);
          currentPage = 1;
          updatePageCounter();
          loadComponents(callback);
        }));
      } else {
        loadComponents(callback);
      }
    } else {
      view.hidePaginatingComponents();
      EventBus.fireEvent(new GetCarComponentsByCarIdEvent(injectedCarId, components -> {
        loadComponents(components);
        if (callback != null)
          callback.call();
      }));
    }
  }

  private void loadComponents(EmptyCallback callback) {
    EventBus.fireEvent(new GetCarComponentsEvent(components -> {
      loadComponents(components);
      if (callback != null)
        callback.call();
    }, ENTRIES_PER_PAGE, currentPage > 0 ? (currentPage - 1) * ENTRIES_PER_PAGE : -1));
  }

  private void loadComponents(List<CarComponentModel> carComponents) {
    this.carComponents = carComponents;
    ObservableList<CarComponentModel> items = view.getCarComponentsTable().getItems();
    items.clear();
    items.addAll(carComponents);
  }

  private void updatePageCounter() {
    view.getPagesCounterLabel().setText("Pagina: " + currentPage + "/" + totalPages);
  }

  private int computePagesNumber(int totalEntries) {
    int pages = totalEntries / ENTRIES_PER_PAGE;
    return totalEntries % ENTRIES_PER_PAGE > 0 ? pages + 1 : pages;
  }
}
