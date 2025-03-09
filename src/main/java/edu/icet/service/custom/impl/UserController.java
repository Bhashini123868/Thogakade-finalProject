package edu.icet.service.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.model.User;
import edu.icet.service.custom.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController implements UserService {
    private static UserController instance;
    private String username;

    private UserController(){}
    public static UserController getInstance(){
        return instance==null?instance=new UserController():instance;
    }
    @Override
    public boolean addUser(User user) {
        try {
            String sql="INSERT INTO users values (?,?,?,?)";
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement(sql);
            pstm.setObject(1,user.getId());
            pstm.setObject(2,user.getUserName());
            pstm.setObject(3,user.getEmail());
            pstm.setObject(4,user.getPassword());
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"ERROR"+e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        String sql="UPDATE users SET username=?,email=?,password=? WHERE id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1,user.getId());
            pstm.setObject(2,user.getUserName());
            pstm.setObject(3,user.getEmail());
            pstm.setObject(4,user.getPassword());

            return pstm.executeUpdate()> 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteUser(String id) {
        String sql="DELETE FROM users WHERE id='"+id+"'";
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            return connection.createStatement().executeUpdate(sql)>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User searchUser(String id) {
        String sql="SELECT * FROM users WHERE id='"+username+"'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()){
                return  new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ObservableList<User> getAll() {
        ObservableList<User> CustomerObservableList = FXCollections.observableArrayList();
        try {
            String sql="select * from users";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement PSVM = connection.prepareStatement(sql);
            ResultSet resultSet = PSVM.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString("id"));
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("email"));
                System.out.println(resultSet.getString("password"));

                User user = new User(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
                CustomerObservableList.add(user);
            }
            return CustomerObservableList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<String> getUserId() {
        ObservableList<String> userIds = FXCollections.observableArrayList();
        ObservableList<User> userObservableList = getAll();
        userObservableList.forEach(user ->{
            userIds.add(user.getId());
        });
        return userIds;
    }
}
