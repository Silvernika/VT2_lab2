package by.bsuir.wt.fourth.controller.command.impl;

import by.bsuir.wt.fourth.controller.command.Command;
import by.bsuir.wt.fourth.controller.command.CommandResult;
import by.bsuir.wt.fourth.controller.command.CommandResultType;
import by.bsuir.wt.fourth.controller.context.RequestContext;
import by.bsuir.wt.fourth.controller.context.RequestContextHelper;
import by.bsuir.wt.fourth.exeptions.ServiceException;
import by.bsuir.wt.fourth.service.ServiceFactory;
import by.bsuir.wt.fourth.service.api.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

public class LogUpCommand implements Command {
    private static final String LOG_UP_PAGE = "logup.jsp";
    private static final String ERROR_PAGE = "error.jsp";
    private static final String EMAIL = "email";
    private static final String PASSWORD_FIRST = "password-first";
    private static final String PASSWORD_SECOND = "password-second";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PHONE = "phone";
    private static final String MESSAGE = "message";
    private static final String ERROR = "error";
    private static final String OK = "ok";

    @Override
    public CommandResult execute(RequestContextHelper helper, HttpServletResponse response) {
        RequestContext requestContext = helper.createContext();
        String message = ERROR;

        Optional<String> email = Optional.ofNullable(requestContext.getRequestParameter(EMAIL));
        Optional<String> passwordFirst = Optional.ofNullable(requestContext.getRequestParameter(PASSWORD_FIRST));
        Optional<String> passwordSecond = Optional.ofNullable(requestContext.getRequestParameter(PASSWORD_SECOND));
        Optional<String> name = Optional.ofNullable(requestContext.getRequestParameter(NAME));
        Optional<String> surname = Optional.ofNullable(requestContext.getRequestParameter(SURNAME));
        Optional<String> phone = Optional.ofNullable(requestContext.getRequestParameter(PHONE));

        try {
            if (email.isPresent() && passwordFirst.isPresent() && passwordSecond.isPresent() &&
                    name.isPresent() && surname.isPresent() && phone.isPresent()) {
                if (passwordFirst.get().equals(passwordSecond.get())) {
                    UserService userService = ServiceFactory.getInstance().getUserService();
                    boolean result = userService.register(name.get(), surname.get(), email.get(), phone.get(), DigestUtils.sha1Hex(passwordFirst.get()));
                    if (result) message = OK;
                }
            }
        } catch (ServiceException e) {
            return new CommandResult(ERROR_PAGE, CommandResultType.FORWARD);
        }

        requestContext.addRequestAttribute(MESSAGE, message);
        helper.updateRequest(requestContext);
        return new CommandResult(LOG_UP_PAGE, CommandResultType.FORWARD);
    }
}
