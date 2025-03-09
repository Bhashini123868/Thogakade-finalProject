package edu.icet.service.custom.impl;

import edu.icet.model.Customer;
import edu.icet.db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import edu.icet.service.custom.CustomerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerServiceImpl implements CustomerService {

    private static CustomerServiceImpl instance;

    private CustomerServiceImpl(){}

    public static CustomerServiceImpl getInstance() {
        return instance==null?instance= new  CustomerServiceImpl():instance;
    }


    @Override
    public boolean addCustomer(Customer customer) {
        try {
            String sql="INSERT INTO customer values (?,?,?,?)";
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement(sql);
            pstm.setObject(1,customer.getId());
            pstm.setObject(2,customer.getName());
            pstm.setObject(3,customer.getAddress());
            pstm.setDouble(4,customer.getSalary());
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"ERROR"+e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String sql="UPDATE customer SET name=?,address=?,salary=? WHERE id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1,customer.getName());
            pstm.setObject(2,customer.getAddress());
            pstm.setObject(3,customer.getSalary());
            pstm.setObject(4,customer.getId());

            return pstm.executeUpdate()> 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        String sql="DELETE FROM customer WHERE id='"+id+"'";
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            return connection.createStatement().executeUpdate(sql)>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String name) {
        String sql="SELECT * FROM customer WHERE id='"+name+"'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()){
                return  new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> CustomerObservableList = FXCollections.observableArrayList();
        try {
            String sql="select * from customer";

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement PSVM = connection.prepareStatement(sql);
            ResultSet resultSet = PSVM.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString("id"));
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("address"));
                System.out.println(resultSet.getString("salary"));

                Customer customer = new Customer(
                        resultSet.getString("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Address"),
                        resultSet.getDouble("Salary")
                );
                CustomerObservableList.add(customer);
            }
            return CustomerObservableList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ObservableList<String> getCustomerId(){
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        ObservableList<Customer> customerObservableList = getAll();
        customerObservableList.forEach(customer ->{
            customerIds.add(customer.getId());
        });
        return customerIds;
    }

    public ObservableList<String> getCustomerName(){
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        ObservableList<Customer> customerObservableList = getAll();
        customerObservableList.forEach(customer ->{
            customerIds.add(customer.getId());
        });
        return customerIds;
    }
}
