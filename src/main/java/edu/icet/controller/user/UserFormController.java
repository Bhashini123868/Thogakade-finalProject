package edu.icet.controller.user;

import edu.icet.model.User;
import edu.icet.service.custom.UserService;
import edu.icet.service.custom.impl.UserController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> clmEmail;

    @FXML
    private TableColumn<?, ?> clmName;

    @FXML
    private TableView<User> tblUsers;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    UserService service = UserController.getInstance();

    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        User user = new User(
                txtId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtPassword.getText()
        );
        if(service.addUser(user)){
            new Alert(Alert.AlertType.INFORMATION,"Added Successfully!");
            lodeTabel();
        }else{
            new Alert(Alert.AlertType.ERROR,"Added Not Successfully ?");
        }
    }

    @FXML
    void btnDeleteUserOnAction(ActionEvent event) {
        if(service.deleteUser(txtId.getText())){
            new Alert(Alert.AlertType.INFORMATION,"User Deleted !").show();
            lodeTabel();
        }else{
            new Alert(Alert.AlertType.ERROR,"User Not Deleted !").show();
        }
    }

    @FXML
    void btnSearchUserOnAction(ActionEvent event) {
        User user=service.searchUser(txtId.getText());
        setTextToValues(user);
    }

    @FXML
    void btnUpdateUserOnAction(ActionEvent event) {
        User user = new User(
                txtId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtPassword.getText()
        );
        if (service.updateUser(user)){
            new Alert(Alert.AlertType.INFORMATION,"Update Successfully !");
        }else{
            new Alert(Alert.AlertType.ERROR,"Update NOT Successfully !");
        }
        lodeTabel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblUsers.getSelectionModel().selectedItemProperty().addListener((((observableValue, o, newValue) ->{
            setTextToValues(newValue);
        })));

        clmName.setCellValueFactory(new PropertyValueFactory<>("username"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        lodeTabel();
    }

    private void lodeTabel() {
        ObservableList<User> UserObserverList =service.getAll();
        tblUsers.setItems(UserObserverList);
    }
    private void setTextToValues(User newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getUserName());
        txtEmail.setText(newValue.getEmail());
        txtPassword.setText(newValue.getPassword());
    }
}
