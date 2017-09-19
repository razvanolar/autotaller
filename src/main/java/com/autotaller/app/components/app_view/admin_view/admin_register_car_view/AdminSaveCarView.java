package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.components.app_view.admin_view.admin_register_car_view.utils.CarFormView;
import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.ImageGalleryPane;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarBodyTypeModel;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.FuelModel;
import com.autotaller.app.utils.CarWheelSideType;
import com.autotaller.app.utils.resources.ImageProvider;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminSaveCarView extends IterableView implements AdminSaveCarController.IAdminSaveCarView {

  private CarFormView carFormView;

  private TableView<CarMakeModel> carMakesTable;
  private TableView<CarTypeModel> carTypesTable;
  private Button continueButton;
  private Text pathText;

  private TextArea carDescriptionTextArea;
  private ImageGalleryPane imageGalleryPane;

  private Region saveCarFormPane;

  public AdminSaveCarView() {
    init();
  }

  private void init() {
    carFormView = new CarFormView();
    pathText = NodeProvider.createTextLabel("", 15, false);
    continueButton = NodeProvider.createToolbarButton("Continua", null);

    toolBar.getItems().addAll(new Separator(), pathText, new FillToolItem(), continueButton);

    carMakesTable = NodeProvider.createCarMakeTable();
    borderPane.setCenter(carMakesTable);
  }

  private void initSaveCarFormPane() {
    int width = NodeProvider.DEFAULT_FIELD_WIDTH + 80;
    carDescriptionTextArea = NodeProvider.createTextArea(width + 190, 120);
    imageGalleryPane = new ImageGalleryPane();

    GridPane descriptionGridPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    descriptionGridPane.add(carDescriptionTextArea, 0, 0);

    VBox descriptionPane = NodeProvider.createVBox(Pos.CENTER, 10,
            NodeProvider.createTitlePane("Descriere", ImageProvider.descriptionIcon()), descriptionGridPane);

    BorderPane imagesPane = NodeProvider.createBorderPane();
    imagesPane.setTop(NodeProvider.createTitlePane("Imagini", ImageProvider.imageIcon()));
    imagesPane.setCenter(imageGalleryPane.asNode());

    BorderPane rightPane = NodeProvider.createBorderPane();
    rightPane.setCenter(imagesPane);
    rightPane.setTop(descriptionPane);

    VBox formVBox = NodeProvider.createVBox(5, NodeProvider.createTitlePane("Informatii", ImageProvider.infoIcon()),
            carFormView.asNode());

    SplitPane splitPane = new SplitPane(NodeProvider.createScrollPane(formVBox, true), rightPane);

    splitPane.setDividerPosition(0, .33);
    SplitPane.setResizableWithParent(formVBox, false);

    this.saveCarFormPane = splitPane;
  }

  public TableView<CarMakeModel> getCarMakesTable() {
    return carMakesTable;
  }

  public TableView<CarTypeModel> getCarTypesTable() {
    if (carTypesTable == null)
      carTypesTable = NodeProvider.createCarModelTable();
    return carTypesTable;
  }

  public CarFormView getCarFormView() {
    return carFormView;
  }

  public Region getSaveCarRegion() {
    if (saveCarFormPane == null)
      initSaveCarFormPane();
    return saveCarFormPane;
  }

  public Button getContinueButton() {
    return continueButton;
  }

  public Button getBackButton() {
    return backButton;
  }

  public void setActiveNode(Region node) {
    borderPane.setCenter(node);
  }

  public Region getActiveNode() {
    return (Region) borderPane.getCenter();
  }

  public Text getPathText() {
    return pathText;
  }

  public TextArea getCarDescriptionTextArea() {
    return carDescriptionTextArea;
  }

  public ImageGalleryPane getImageGalleryPane() {
    return imageGalleryPane;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
