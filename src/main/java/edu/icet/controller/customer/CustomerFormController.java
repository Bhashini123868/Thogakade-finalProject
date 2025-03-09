package edu.icet.controller.customer;

import edu.icet.model.Customer;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CustomerService;
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

public class CustomerFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> clmAddress;

    @FXML
    private TableColumn<?, ?> clmId;

    @FXML
    private TableColumn<?, ?> clmName;

    @FXML
    private TableColumn<?, ?> clmSalary;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;
    CustomerService service = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );

        if(service.addCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Added Successfully!");
            lodeTabel();
        }else{
            new Alert(Alert.AlertType.ERROR,"Added Not Successfully ?");
        }
    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event) {
        if(service.deleteCustomer(txtId.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Customer Deleted !").show();
            lodeTabel();
        }else{
            new Alert(Alert.AlertType.ERROR,"Customer Not Deleted !").show();
        }
    }

    @FXML
    void btnSearchCustomerOnAction(ActionEvent event) {
        Customer customer=service.searchCustomer(txtId.getText());
        setTextToValues(customer);
    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );
        if (service.updateCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Update Successfully !");
        }else{
            new Alert(Alert.AlertType.ERROR,"Update NOT Successfully !");

        }
        lodeTabel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((((observableValue, o, newValue) ->{
            setTextToValues(newValue);
        })));

        clmId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        clmSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));

        lodeTabel();
    }

    private void lodeTabel() {
        ObservableList<Customer> CustomerObservableList =service.getAll();
        tblCustomer.setItems(CustomerObservableList);
    }

    private void setTextToValues(Customer newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
    }
}
