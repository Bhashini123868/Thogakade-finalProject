package edu.icet.controller.dashBoard;

import edu.icet.db.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    @FXML
    private Label lblCustomer;

    @FXML
    private Label lblItem;

    @FXML
    private Label lblOrders;

    @FXML
    private AnchorPane lodeFormController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            int customerCount = getCount(connection, "SELECT COUNT(*) FROM customer");
            lblCustomer.setText("    " + customerCount);
            int itemCount = getCount(connection, "SELECT COUNT(*) FROM item");
            lblItem.setText("    " + itemCount);

            int orderCount= getCount(connection,"SELECT COUNT(*) FROM orders");
            lblOrders.setText("    "+ orderCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private int getCount(Connection connection, String query) {
        int count = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}