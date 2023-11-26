package by.bsuir.wt.fourth.service;

import by.bsuir.wt.fourth.service.impl.ApartmentServiceImpl;
import by.bsuir.wt.fourth.service.impl.RoleServiceImpl;
import by.bsuir.wt.fourth.service.impl.UserOrderServiceImpl;
import by.bsuir.wt.fourth.service.impl.UserServiceImpl;
import by.bsuir.wt.fourth.service.api.*;
import by.bsuir.wt.fourth.service.impl.*;
public class ServiceFactory {

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return Holder.INSTANCE;
    }



    private final UserOrderService userOrderService=new UserOrderServiceImpl();
    private final UserInformationService userInformationService=new UserInformationServiceImpl();
    private final ApartmentService apartmentService=new ApartmentServiceImpl();
    private final UserService userService=new UserServiceImpl();
    private final RoleService roleService=new RoleServiceImpl();


    public ApartmentService getApartmentService() {
        return apartmentService;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserOrderService getUserOrderService() {
        return userOrderService;
    }

    public UserInformationService getUserInformationService() {
        return userInformationService;
    }

    private static class Holder {
        static final ServiceFactory INSTANCE = new ServiceFactory();
    }
}
