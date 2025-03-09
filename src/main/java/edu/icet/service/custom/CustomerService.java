package edu.icet.service.custom;

import edu.icet.model.Customer;
import javafx.collections.ObservableList;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String id);
    Customer searchCustomer(String name);
    ObservableList<Customer> getAll();
    public ObservableList<String> getCustomerId();
}
