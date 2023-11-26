package by.bsuir.wt.fourth.controller.command.impl;

import by.bsuir.wt.fourth.controller.command.Command;
import by.bsuir.wt.fourth.controller.command.CommandResult;
import by.bsuir.wt.fourth.controller.command.CommandResultType;
import by.bsuir.wt.fourth.controller.context.RequestContext;
import by.bsuir.wt.fourth.controller.context.RequestContextHelper;
import by.bsuir.wt.fourth.entity.User;
import by.bsuir.wt.fourth.exeptions.ServiceException;
import by.bsuir.wt.fourth.service.ServiceFactory;
import by.bsuir.wt.fourth.service.api.UserOrderService;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ConfirmUserOrderCommand implements Command {
    private static final String ADD_ORDER_PAGE = "addUserOrder.jsp";
    private static final String MY_ORDERS_PAGE = "command=myOrders";
    private static final String ERROR_PAGE = "error.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";

    private static final String LEASE_DURATION= "lease_duration";
    private static final String MONTH = "month";
    private static final String YEAR = "year";
    private static final String DAY = "day";
    private static final String HOUR = "hour";
    private static final String MINUTE="minute";

    private static final String USER = "user";
    private static final String APARTMENT_ID = "apartment_id";




    @Override
    public CommandResult execute(RequestContextHelper helper, HttpServletResponse response) {
        RequestContext requestContext = helper.createContext();


        Optional<String> leaseDuration = Optional.ofNullable(requestContext.getRequestParameter(LEASE_DURATION));
        Optional<String> month = Optional.ofNullable(requestContext.getRequestParameter(MONTH));
        Optional<String> year = Optional.ofNullable(requestContext.getRequestParameter(YEAR));
        Optional<String> day = Optional.ofNullable(requestContext.getRequestParameter(DAY));
        Optional<String> minute = Optional.ofNullable(requestContext.getRequestParameter(MINUTE));
        Optional<String> hour = Optional.ofNullable(requestContext.getRequestParameter(HOUR));


        try {
            User user = (User) requestContext.getSessionAttribute(USER);
            int apartmentId=Integer.parseInt(requestContext.getRequestParameter(APARTMENT_ID));
            int userId = user.getId();


            if ( leaseDuration.isPresent() && month.isPresent() && year.isPresent() && day.isPresent()
            && minute.isPresent() && hour.isPresent()) {
                UserOrderService userOrderService = ServiceFactory.getInstance().getUserOrderService();
               boolean result = userOrderService.addNewUserOrder(year.get(), month.get(), day.get(), hour.get(), minute.get(),
                       leaseDuration.get(), String.valueOf(userId),String.valueOf(apartmentId));
                if (result) {
                    helper.updateRequest(requestContext);

                    return new CommandResult(MY_ORDERS_PAGE, CommandResultType.REDIRECT);
                }

            }

            requestContext.addRequestAttribute(ERROR_MESSAGE, true);
            helper.updateRequest(requestContext);
            return new CommandResult(ADD_ORDER_PAGE, CommandResultType.FORWARD);
        } catch (ServiceException e) {
            e.printStackTrace();
            return new CommandResult(ERROR_PAGE, CommandResultType.FORWARD);
        }
    }


}
