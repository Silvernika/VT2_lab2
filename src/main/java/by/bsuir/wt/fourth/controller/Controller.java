package by.bsuir.wt.fourth.controller;

import by.bsuir.wt.fourth.controller.command.Command;
import by.bsuir.wt.fourth.controller.command.CommandFactory;
import by.bsuir.wt.fourth.controller.command.CommandResult;
import by.bsuir.wt.fourth.controller.context.RequestContextHelper;
import by.bsuir.wt.fourth.dao.connection.ConnectionPool;
import by.bsuir.wt.fourth.exeptions.ConnectionException;


import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/hotel")
public class Controller extends HttpServlet {

    private static final String COMMAND = "command";
    private static final String PATH = "/hotel?";
    private static final String MAIN_COMMAND = "command=main";

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool.getInstance().initialize();
        } catch (ConnectionException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void destroy() {
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName = request.getParameter(COMMAND);
        if (commandName == null || "".equals(commandName)) {
            response.sendRedirect(PATH + MAIN_COMMAND);
        } else {
            Command command = CommandFactory.getInstance().getCommand(commandName);
            RequestContextHelper contextHelper = new RequestContextHelper(request);
            CommandResult result = command.execute(contextHelper, response);

            dispatch(result, request, response);
        }
    }

    private void dispatch(CommandResult result, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (result.isRedirect()) {
            response.sendRedirect(PATH + result.getPage());
        } else {
            request.getRequestDispatcher(result.getPage()).forward(request, response);
        }
    }
}
