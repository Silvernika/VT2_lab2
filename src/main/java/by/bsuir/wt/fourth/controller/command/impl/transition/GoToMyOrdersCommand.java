package by.bsuir.wt.fourth.controller.command.impl.transition;

import by.bsuir.wt.fourth.controller.command.Command;
import by.bsuir.wt.fourth.controller.command.CommandResult;
import by.bsuir.wt.fourth.controller.command.CommandResultType;
import by.bsuir.wt.fourth.controller.context.RequestContext;
import by.bsuir.wt.fourth.controller.context.RequestContextHelper;
import by.bsuir.wt.fourth.entity.Apartment;
import by.bsuir.wt.fourth.entity.User;
import by.bsuir.wt.fourth.entity.UserOrder;
import by.bsuir.wt.fourth.exeptions.ServiceException;
import by.bsuir.wt.fourth.service.ServiceFactory;
import by.bsuir.wt.fourth.service.api.ApartmentService;
import by.bsuir.wt.fourth.service.api.UserOrderService;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class GoToMyOrdersCommand implements Command {
    private static final String PAGE = "myOrders.jsp";
    private static final String ERROR_PAGE = "error.jsp";
    private static final String USER_ORDERS = "userOrders";
    private static final String APARTMENTS = "apartments";
    private static final String USER = "user";

    @Override
    public CommandResult execute(RequestContextHelper helper, HttpServletResponse response) {
        RequestContext requestContext = helper.createContext();

        User user = (User) requestContext.getSessionAttribute(USER);
        if (user == null) {
            helper.updateRequest(requestContext);
            return new CommandResult(PAGE, CommandResultType.FORWARD);
        }
        try {
            UserOrderService userOrderService=ServiceFactory.getInstance().getUserOrderService();
            List<UserOrder> userOrders=userOrderService.retrieveUserOrderByUserId(user.getId());
            requestContext.addRequestAttribute(USER_ORDERS, userOrders);
            ApartmentService apartmentService=ServiceFactory.getInstance().getApartmentService();
            List<Apartment> apartments=apartmentService.retrieveApartamentsByUserId(user.getId());
            requestContext.addRequestAttribute(APARTMENTS, apartments);

        } catch (ServiceException e) {
            return new CommandResult(ERROR_PAGE, CommandResultType.FORWARD);
        }

        helper.updateRequest(requestContext);
        return new CommandResult(PAGE, CommandResultType.FORWARD);
    }
}
