package edu.icet.service;

import edu.icet.service.custom.impl.CustomerServiceImpl;
import edu.icet.service.custom.impl.ItemServiceImpl;
import edu.icet.util.ServiceType;

public class ServiceFactory {
    public static ServiceFactory instance;
    private ServiceFactory(){

    }
    public static ServiceFactory getInstance(){
        return instance == null ? instance = new ServiceFactory() : instance;
    }
    public <T extends SuperService> T getServiceType(ServiceType serviceType){
        switch (ServiceType){
            case CUSTOMER:return (T) CustomerServiceImpl.getInstance();
            case ITEM:return (T)  new ItemServiceImpl();
        }
        return null;
    }
}
