package by.bsuir.wt.fourth.controller.command.impl.transition;

import by.bsuir.wt.fourth.controller.command.Command;
import by.bsuir.wt.fourth.controller.command.CommandResult;
import by.bsuir.wt.fourth.controller.command.CommandResultType;
import by.bsuir.wt.fourth.controller.context.RequestContext;
import by.bsuir.wt.fourth.controller.context.RequestContextHelper;

import jakarta.servlet.http.HttpServletResponse;


public class GoToAddApartmentCommand implements Command {
    private static final String PAGE = "addApartment.jsp";


    @Override
    public CommandResult execute(RequestContextHelper helper, HttpServletResponse response) {
        RequestContext requestContext = helper.createContext();


        helper.updateRequest(requestContext);
        return new CommandResult(PAGE, CommandResultType.FORWARD);
    }
}