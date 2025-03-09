package edu.icet.service.custom;

import edu.icet.model.Item;
import edu.icet.model.OrderDetail;
import javafx.collections.ObservableList;

import java.util.List;

public interface ItemService {
    boolean addItem(Item item);
    boolean updateItem(Item item);
    boolean deleteItem(String code);
    Item searchItem(String code);
    ObservableList<Item> getAll();
    public  ObservableList<String> getItemId();
    boolean updateStock(List<OrderDetail> orderDetails);
}
