package edu.icet.service.custom.impl;

import edu.icet.model.OrderDetail;
import edu.icet.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    public boolean addOrderDetail(List<OrderDetail> orderDetails){
        for (OrderDetail orderDetail:orderDetails){
            boolean isAdd = addOrderDetail((List<OrderDetail>) orderDetail);
            if (!isAdd){
                return false;
            }
        }
        return true;
    }
    public boolean addOrderDetail(OrderDetail orderDetails){
        String SQL = "INSERT INTO orderdetail VALUES(?,?,?,?)";
        try {
            return CrudUtil.execute(SQL,
                    orderDetails.getOrderId(),
                    orderDetails.getItemCode(),
                    orderDetails.getQty(),
                    orderDetails.getUnitPrice()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
