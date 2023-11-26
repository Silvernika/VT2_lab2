package by.bsuir.wt.fourth.controller.command.impl;

import by.bsuir.wt.fourth.controller.command.Command;
import by.bsuir.wt.fourth.controller.command.CommandResult;
import by.bsuir.wt.fourth.controller.command.CommandResultType;
import by.bsuir.wt.fourth.controller.context.RequestContext;
import by.bsuir.wt.fourth.controller.context.RequestContextHelper;
import by.bsuir.wt.fourth.exeptions.ServiceException;
import by.bsuir.wt.fourth.service.ServiceFactory;
import by.bsuir.wt.fourth.service.api.ApartmentService;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ConfirmChangingApartmentStatusCommand  implements Command {
    private static final String PAGE = "command=catalog";
    private static final String ERROR_PAGE = "error.jsp";
    private static final String STATUS = "status";
    private static final String APATRMENT_ID = "apartmentId";
    private static final String MESSAGE_PARAMETER = "&message=";
    private static final String ERROR = "error";
    private static final String OK = "ok";

    @Override
    public CommandResult execute(RequestContextHelper helper, HttpServletResponse response) {

        RequestContext requestContext = helper.createContext();
        String message = ERROR;

        Optional<String> status = Optional.ofNullable(requestContext.getRequestParameter(STATUS));

        int id=Integer.parseInt(requestContext.getRequestParameter(APATRMENT_ID));


        try {
            if (status.isPresent()) {
                ApartmentService apartmentService = ServiceFactory.getInstance().getApartmentService();
                apartmentService.updateApartmentStatusById(id,status.get());
                message = OK;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return new CommandResult(ERROR_PAGE, CommandResultType.FORWARD);
        }

        helper.updateRequest(requestContext);
        return new CommandResult(PAGE + MESSAGE_PARAMETER + message, CommandResultType.REDIRECT);
    }
}
