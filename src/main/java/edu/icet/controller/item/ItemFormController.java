package edu.icet.controller.item;

import edu.icet.model.Item;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.ItemService;
import edu.icet.util.ServiceType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> clmCode;

    @FXML
    private TableColumn<?, ?> clmDescription;

    @FXML
    private TableColumn<?, ?> clmQtyOnHand;

    @FXML
    private TableColumn<?, ?> clmUnitPrice;

    @FXML
    private TableView<Item> tblItem;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;
    ItemService service = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        Item item = new Item(
                txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())

        );

        if(service.addItem(item)){
            new Alert(Alert.AlertType.INFORMATION,"Added Successfully!");
            lodeTabel();
        }else{
            new Alert(Alert.AlertType.ERROR,"Added Not Successfully ?");
        }
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        if(service.deleteItem(txtCode.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Item Deleted !").show();
            lodeTabel();
        }else{
            new Alert(Alert.AlertType.ERROR,"Item Not Deleted !").show();
        }
    }

    @FXML
    void btnSearchItemOnAction(ActionEvent event) {
        Item item=service.searchItem(txtCode.getText());
        setTextToValues(item);
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        Item item = new Item(
                txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );
        if (service.updateItem(item)){
            new Alert(Alert.AlertType.INFORMATION,"Update Successfully !");
        }else{
            new Alert(Alert.AlertType.ERROR,"Update NOT Successfully !");

        }
        lodeTabel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblItem.getSelectionModel().selectedItemProperty().addListener((((observableValue, oldValue, newValue) ->{
            if (newValue!=null){
                setTextToValues(newValue);
            }
        })));

        clmCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clmQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        lodeTabel();
    }

    private void lodeTabel() {
        ObservableList<Item> ItemObservableList =service.getAll();
        tblItem.setItems(ItemObservableList);
    }

    private void setTextToValues(Item newValue) {
        txtCode.setText(newValue.getCode());
        txtDescription.setText(newValue.getDescription());
        txtPrice.setText(String.valueOf(newValue.getPrice()));
        txtQty.setText(String.valueOf(newValue.getQtyOnHand()));
    }
}
