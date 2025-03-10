package edu.icet.controller.login;

import edu.icet.db.DBConnection;
import edu.icet.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public PasswordField txtPassword;
    public TextField txtEmail;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String key ="12345";

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        String SQL = "SELECT * FROM users WHERE email=" + "'" + txtEmail.getText() + "'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);
            resultSet.next();
            User user = new User(resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(4)
            );
            System.out.println(user);
            if (user != null) {
                if (basicTextEncryptor.decrypt(user.getPassword()).equals(txtPassword.getText())) {
                    System.out.println("Login!");
                    Stage stage = new Stage();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashBoardForm.fxml"))));
                    stage.show();
                    ((Stage) txtEmail.getScene().getWindow()).close();
                } else {
                    new Alert(Alert.AlertType.ERROR,"Check your Password").show();
                    System.out.println("Check your password");
                }
            } else {
                new Alert(Alert.AlertType.ERROR,"User Not Found").show();
                System.out.println("user Not found!");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnSinginOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"))));
            stage.show();
            ((Stage) txtEmail.getScene().getWindow()).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
