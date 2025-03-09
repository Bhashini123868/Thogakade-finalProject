package edu.icet.controller.order;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.db.DBConnection;
import edu.icet.model.Customer;
import edu.icet.model.Item;
import edu.icet.model.Order;
import edu.icet.model.OrderDetail;
import edu.icet.service.custom.impl.CustomerServiceImpl;
import edu.icet.service.custom.impl.ItemServiceImpl;
import edu.icet.service.custom.impl.OrderController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> clmDescription;

    @FXML
    private TableColumn<?, ?> clmItemCode;

    @FXML
    private TableColumn<?, ?> clmPrice;

    @FXML
    private TableColumn<?, ?> clmQty;

    @FXML
    private TableColumn<?, ?> clmtotal;

    @FXML
    private JFXComboBox<?> cmbCustId;

    @FXML
    private JFXComboBox<?> cmbItemCode;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblnettotal;

    @FXML
    private Label lblnettotal1;

    @FXML
    private TableView<?> tblOrders;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtHandOnStock;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        clmItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmtotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        String itemcode = cmbItemCode.getValue();
        String description =txtDescription.getText();
        Integer qty = Integer.parseInt(txtQty.getText());
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Double total = unitPrice*qty;

        if (Integer.parseInt(txtHandOnStock.getText())<qty){
            new Alert(Alert.AlertType.WARNING,"OutOfStock").show();
        }else{
            cartTms.add(new dto.Cart(itemcode,description,qty,unitPrice,total));
            calcNetTotal();
        }

        tblOrders.setItems(cartTms);
    }

    @FXML
    void btnCommitOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPlaceOrderAction(ActionEvent event) {
        String orderId = txtOrderId.getText();
        LocalDate orderDate = LocalDate.now();
        String customerId = cmbCustId.getValue();

        List<OrderDetail> orderDetails = new ArrayList<>();

        cartTms.forEach(obj->{
            orderDetails.add(new OrderDetail(orderId,obj.getItemCode(),obj.getQty(),0.0));
        });
        Order order = new Order(orderId, orderDate, customerId, orderDetails);
        try {
            new OrderController().placeOrder(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lodeTimeAndDate();
        loadCustomerIds();
        loadItemCodes();

        cmbCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newVal) -> {
            if (newVal!=null){
                searchCustomer(newVal);
            }
        });
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newVal) -> {
            if (newVal!=null){
                SearchItem(newVal);
            }
        });
    }
    private void calcNetTotal(){
        Double total=0.0;
        for (dto.CartTM cartTM: cartTms){
            total+=cartTM.getTotal();
        }
        lblnettotal.setText(total.toString()+"/=");
    }

    private void loadItemCodes() {
        cmbCustId.setItems(CustomerServiceImpl.getInstance().getCustomerId());
    }

    private void loadCustomerIds() {
        cmbItemCode.setItems(CustomerServiceImpl.getInstance().getCustomerId());
    }

    private void lodeTimeAndDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String DateFormet= simpleDateFormat.format(date);
        lblDate.setText(DateFormet);

        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour()+":"+now.getMinute()+":"+now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void searchCustomer(String customerId){
        Customer customer = CustomerServiceImpl.getInstance().searchCustomer(customerId);
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
    }
    ObservableList<dto.CartTM> cartTms = FXCollections.observableArrayList();

    private void SearchItem(String itemcode){
        Item item = ItemServiceImpl.getInstance().searchItem(itemcode);
        txtDescription.setText(item.getDescription());
        txtHandOnStock.setText(String.valueOf(item.getQtyOnHand()));
        txtUnitPrice.setText(String.valueOf(item.getPrice()));
    }
}
