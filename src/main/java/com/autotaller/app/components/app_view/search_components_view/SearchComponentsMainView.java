package com.autotaller.app.components.app_view.search_components_view;

import com.autotaller.app.components.app_view.utils.extensions.StockTypeCheckBox;
import com.autotaller.app.components.app_view.utils.extensions.UsageTypeCheckBox;
import com.autotaller.app.components.utils.IterableView;
import com.autotaller.app.utils.StockType;
import com.autotaller.app.utils.UsageStateType;
import com.autotaller.app.utils.resources.NodeProvider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchComponentsMainView extends IterableView implements SearchComponentsMainController.ISearchComponentsMainView {

  private Node subkitSearchNode;
  private TextField componentNameTextField;
  private TextField componentCodeTextField;
  private TextField componentMinPriceTextField;
  private TextField componentMaxPriceTextField;
  private Node searchComponentsButton;
  private List<UsageTypeCheckBox> usageCheckBoxList;
  private List<StockTypeCheckBox> stockCheckBoxList;

  public SearchComponentsMainView() {
    init();
  }

  private void init() {
    Text title = NodeProvider.createTextLabel("Cautare Componente", 25, false);
    subkitSearchNode = NodeProvider.createAdminAppMenu("Cautare pe subansamble", null);
    componentNameTextField = NodeProvider.createTextField(300);
    componentCodeTextField = NodeProvider.createTextField(300);
    componentMinPriceTextField = NodeProvider.createTextField();
    componentMaxPriceTextField = NodeProvider.createTextField();
    searchComponentsButton = NodeProvider.createAdminAppMenu("Cauta", null);
    usageCheckBoxList = createUsageCheckBoxList();
    stockCheckBoxList = createStockCheckBoxList();

    GridPane searchByAttributesGridPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    int attributesRow = 0;
    int attributesCol = 1;
    int colSpan = Math.max(usageCheckBoxList.size(), stockCheckBoxList.size());
    searchByAttributesGridPane.add(NodeProvider.createFormTextLabel("Nume Componenta: "), 0, attributesRow);
    searchByAttributesGridPane.add(componentNameTextField, 1, attributesRow++, colSpan, 1);
    searchByAttributesGridPane.add(NodeProvider.createFormTextLabel("Cod Componenta: "), 0, attributesRow);
    searchByAttributesGridPane.add(componentCodeTextField, 1, attributesRow++, colSpan, 1);
    searchByAttributesGridPane.add(NodeProvider.createFormTextLabel("Grad de uzura: "), 0, attributesRow);
    for (UsageTypeCheckBox checkBox : usageCheckBoxList) {
      searchByAttributesGridPane.add(checkBox, attributesCol++, attributesRow);
    }
    attributesRow++;
    attributesCol = 1;
    searchByAttributesGridPane.add(NodeProvider.createFormTextLabel("Stoc: "), 0, attributesRow);
    for (StockTypeCheckBox checkBox : stockCheckBoxList) {
      searchByAttributesGridPane.add(checkBox, attributesCol++, attributesRow);
    }
    attributesRow++;
    searchByAttributesGridPane.add(NodeProvider.createFormTextLabel("Pret Minim: "), 0, attributesRow);
    searchByAttributesGridPane.add(componentMinPriceTextField, 1, attributesRow++, colSpan, 1);
    searchByAttributesGridPane.add(NodeProvider.createFormTextLabel("Pret Maxim: "), 0, attributesRow);
    searchByAttributesGridPane.add(componentMaxPriceTextField, 1, attributesRow, colSpan, 1);

    GridPane gridPane = NodeProvider.createGridPane(Pos.CENTER, 10, 10);
    int row = 0;
    gridPane.add(NodeProvider.createHBox(Pos.CENTER, 10, title), 0, row++);
    gridPane.add(NodeProvider.createTitlePane("Cautare pe subansamble", null), 0, row++);
    gridPane.add(subkitSearchNode, 0, row++);
    gridPane.add(NodeProvider.createTitlePane("Cautare atribute", null), 0, row++);
    gridPane.add(searchByAttributesGridPane, 0, row++);
    gridPane.add(searchComponentsButton, 0, row, 2, 1);

    borderPane.setCenter(NodeProvider.createScrollPane(gridPane, true));
  }

  private List<UsageTypeCheckBox> createUsageCheckBoxList() {
    List<UsageTypeCheckBox> list = new ArrayList<>();
    for (UsageStateType usageStateType : UsageStateType.values()) {
      UsageTypeCheckBox checkBox = new UsageTypeCheckBox(usageStateType);
      checkBox.setSelected(true);
      list.add(checkBox);
    }
    return list;
  }

  private List<StockTypeCheckBox> createStockCheckBoxList() {
    List<StockTypeCheckBox> list = new ArrayList<>();
    for (StockType stockType : StockType.values()) {
      StockTypeCheckBox checkBox = new StockTypeCheckBox(stockType);
      checkBox.setSelected(true);
      list.add(checkBox);
    }
    return list;
  }

  public Node getSubkitSearchNode() {
    return subkitSearchNode;
  }

  @Override
  public Node asNode() {
    return mainContainer;
  }
}
