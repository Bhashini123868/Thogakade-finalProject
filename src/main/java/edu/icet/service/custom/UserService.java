package edu.icet.service.custom;

import edu.icet.model.User;
import javafx.collections.ObservableList;

public interface UserService {
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(String id);
    User searchUser(String id);
    ObservableList<User> getAll();
    public ObservableList<String> getUserId();

}
