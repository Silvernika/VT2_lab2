package by.bsuir.wt.fourth.controller.command.impl.transition;

import by.bsuir.wt.fourth.controller.command.Command;
import by.bsuir.wt.fourth.controller.command.CommandResult;
import by.bsuir.wt.fourth.controller.command.CommandResultType;
import by.bsuir.wt.fourth.controller.context.RequestContext;
import by.bsuir.wt.fourth.controller.context.RequestContextHelper;
import by.bsuir.wt.fourth.entity.Apartment;
import by.bsuir.wt.fourth.entity.User;
import by.bsuir.wt.fourth.exeptions.ServiceException;
import by.bsuir.wt.fourth.service.ServiceFactory;
import by.bsuir.wt.fourth.service.api.ApartmentService;
import by.bsuir.wt.fourth.service.api.RoleService;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class GoToCatalogCommand implements Command {
    private static final String PAGE = "catalog.jsp";
    private static final String ERROR_PAGE = "error.jsp";
    private static final String APARTMENTS = "apartments";
    private static final String STATUS = "доступно";
    private static final String USER = "user";
    private static final String ADMIN_ROLE = "admin";



    @Override
    public CommandResult execute(RequestContextHelper helper, HttpServletResponse response) {
        RequestContext requestContext = helper.createContext();

        User user = (User) requestContext.getSessionAttribute(USER);
        if (user == null) {
            helper.updateRequest(requestContext);
            ApartmentService apartmentService=ServiceFactory.getInstance().getApartmentService();
            try {
                List<Apartment> apartment=apartmentService.retrieveApartamentByStatus(STATUS);
                requestContext.addRequestAttribute(APARTMENTS,apartment);
                helper.updateRequest(requestContext);
            } catch (ServiceException e) {
                return new CommandResult(ERROR_PAGE, CommandResultType.FORWARD);
            }
            return new CommandResult(PAGE, CommandResultType.FORWARD);
        }

        try {
            RoleService roleService=ServiceFactory.getInstance().getRoleService();
            ApartmentService apartmentService=ServiceFactory.getInstance().getApartmentService();
            List<Apartment> apartment;
            if (roleService.retrieveRoleById(user.getRoleId()).get().getName().equals(ADMIN_ROLE)){
                apartment=apartmentService.retrieveALLApartaments();
            }else {
                apartment=apartmentService.retrieveApartamentByStatus(STATUS);
            }
            requestContext.addRequestAttribute(APARTMENTS,apartment);

        } catch (ServiceException e) {
            return new CommandResult(ERROR_PAGE, CommandResultType.FORWARD);
        }

        helper.updateRequest(requestContext);
        return new CommandResult(PAGE, CommandResultType.FORWARD);
    }
}

