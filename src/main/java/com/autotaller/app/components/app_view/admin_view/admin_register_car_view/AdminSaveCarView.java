package com.autotaller.app.components.app_view.admin_view.admin_register_car_view;

import com.autotaller.app.components.utils.FillToolItem;
import com.autotaller.app.components.utils.ImageGalleryPane;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.model.CarMakeModel;
import com.autotaller.app.model.CarTypeModel;
import com.autotaller.app.model.FuelModel;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by razvanolar on 04.05.2017
 */
public class AdminSaveCarView extends IterableView implements AdminSaveCarController.IAdminSaveCarView {

  private TableView<CarMakeModel> carMakesTable;
  private TableView<CarTypeModel> carTypesTable;
  private Button continueButton;
  private Text pathText;

  private Text carMakeText;
  private Text carTypeText;
  private TextField carNameField;
  private DatePicker producedFromPicker;
  private DatePicker producedToPicker;
  private DatePicker productionYearPicker;
  private TextField carKmField;
  private Spinner<Integer> carKwSpinner;
  private Spinner<Integer> carCapacitySpinner;
  private Spinner<Integer> carCylindersSpinner;
  private ComboBox<FuelModel> carFuelCombo;
  private TextArea carDescriptionTextArea;
  private ImageGalleryPane imageGalleryPane;

  private Region saveCarFormPane;

  public AdminSaveCarView() {
    init();
  }

  private void init() {
    pathText = NodeProvider.createTextLabel("", 15, false);
    continueButton = NodeProvider.createToolbarButton("Continua", null);

    toolBar.getItems().addAll(new Separator(), pathText, new FillToolItem(), continueButton);

    carMakesTable = NodeProvider.createCarMakeTable();
    borderPane.setCenter(carMakesTable);
  }

  private void initSaveCarFormPane() {
    int width = NodeProvider.DEFAULT_FIELD_WIDTH + 80;
    carMakeText = NodeProvider.createFormTextLabel("");
    carTypeText = NodeProvider.createFormTextLabel("");
    carNameField = NodeProvider.createTextField();
    productionYearPicker = NodeProvider.createDatePicker(width);
    producedFromPicker = NodeProvider.createDatePicker(width);
    producedToPicker = NodeProvider.createDatePicker(width);
    carKmField = NodeProvider.createTextField(width);
    carKwSpinner = NodeProvider.createSpinner(width,0, 1000, 0, 1);
    carCapacitySpinner = NodeProvider.createSpinner(width,0, 10000, 0, 1);
    carCylindersSpinner = NodeProvider.createSpinner(width,0, 100, 0, 1);
    carFuelCombo = NodeProvider.createFuelCombo(width);
    carDescriptionTextArea = NodeProvider.createTextArea(width + 90, 120);
    imageGalleryPane = new ImageGalleryPane();

    GridPane saveCarFormPane = NodeProvider.createGridPane(Pos.TOP_CENTER, 10, 10);
    int row = 0;
    saveCarFormPane.add(NodeProvider.createFormTextLabel("Marca: "), 0, row);
    saveCarFormPane.add(carMakeText, 1, row++);
    saveCarFormPane.add(NodeProvider.createFormTextLabel("Model: "), 0, row);
    saveCarFormPane.add(carTypeText, 1, row++);
    saveCarFormPane.add(NodeProvider.createFormTextLabel("Motorizare: "), 0, row);
    saveCarFormPane.add(carNameField, 1, row++);
    saveCarFormPane.add(NodeProvider.createFormTextLabel("An fabricatie: "), 0, row);
    saveCarFormPane.add(productionYearPicker, 1, row++);
    saveCarFormPane.add(NodeProvider.createFormTextLabel("De la: "), 0, row);
    saveCarFormPane.add(producedFromPicker, 1, row++);
    saveCarFormPane.add(NodeProvider.createFormTextLabel("Pana la: "), 0, row);
    saveCarFormPane.add(producedToPicker, 1, row++);
    saveCarFormPane.add(NodeProvider.createFormTextLabel("Km parcursi: "), 0, row);
    saveCarFormPane.add(carKmField, 1, row++);
    saveCarFormPane.add(NodeProvider.createFormTextLabel("KW: "), 0, row);
    saveCarFormPane.add(carKwSpinner, 1, row++);
    saveCarFormPane.add(NodeProvider.createFormTextLabel("Cap. cil.:"), 0, row);
    saveCarFormPane.add(carCapacitySpinner, 1, row++);
    saveCarFormPane.add(NodeProvider.createFormTextLabel("Cilindrii: "), 0, row);
    saveCarFormPane.add(carCylindersSpinner, 1, row++);
    saveCarFormPane.add(NodeProvider.createFormTextLabel("Combustibil: "), 0, row);
    saveCarFormPane.add(carFuelCombo, 1, row);

    GridPane descriptionPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    descriptionPane.add(carDescriptionTextArea, 0, 0);

    BorderPane imagesPane = NodeProvider.createBorderPane();
    imagesPane.setTop(NodeProvider.createTitlePane("Imagini"));
    imagesPane.setCenter(imageGalleryPane.asNode());

    VBox formVBox = NodeProvider.createVBox(5, NodeProvider.createTitlePane("Informatii"),
            saveCarFormPane, NodeProvider.createTitlePane("Descriere"), descriptionPane);

    SplitPane splitPane = new SplitPane(NodeProvider.createScrollPane(formVBox, true), imagesPane);

    splitPane.setDividerPosition(0, .33);
//    SplitPane.setResizableWithParent(imagesPane, false);

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

  public Region getSaveCarForm() {
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

  public Text getCarMakeText() {
    return carMakeText;
  }

  public Text getCarTypeText() {
    return carTypeText;
  }

  public TextField getCarNameField() {
    return carNameField;
  }

  public DatePicker getProducedFromPicker() {
    return producedFromPicker;
  }

  public DatePicker getProducedToPicker() {
    return producedToPicker;
  }

  public DatePicker getProductionYearPicker() {
    return productionYearPicker;
  }

  public TextField getCarKmField() {
    return carKmField;
  }

  public Spinner<Integer> getCarKwSpinner() {
    return carKwSpinner;
  }

  public Spinner<Integer> getCarCapacitySpinner() {
    return carCapacitySpinner;
  }

  public Spinner<Integer> getCarCylindersSpinner() {
    return carCylindersSpinner;
  }

  public ComboBox<FuelModel> getCarFuelCombo() {
    return carFuelCombo;
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
