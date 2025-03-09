package edu.icet.controller.register;

import edu.icet.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFormController {
    public TextField txtname;
    public PasswordField txtPassword;
    public TextField txtemail;
    public PasswordField txtConformePassword;

    public void btnRegisterFormAction(ActionEvent actionEvent) throws SQLException {
        String key = "12345";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);
        if (txtPassword.getText().equals(txtConformePassword.getText())) {
            System.out.println(true);
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users WHERE email=" + "'" + txtemail.getText() + "'");
            if (!resultSet.next()) {
                System.out.println(false);

                String SQL = "INSERT INTO users (username,email,password) VALUES (?,?,?)";
                PreparedStatement psTm = connection.prepareStatement(SQL);
                psTm.setString(1, txtname.getText());
                psTm.setString(2, txtemail.getText());
                psTm.setString(3, basicTextEncryptor.encrypt(txtPassword.getText()));
                psTm.executeUpdate();
            } else {
                System.out.println(true);
            }
        } else {
            System.out.println(false);
        }
    }

    public void btnLogInOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginpage_form.fxml"))));
            stage.show();
            ((Stage) txtemail.getScene().getWindow()).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
