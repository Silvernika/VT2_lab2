package by.bsuir.wt.fourth.controller.command;

import by.bsuir.wt.fourth.controller.context.RequestContextHelper;

import jakarta.servlet.http.*;

public interface Command {
    CommandResult execute(RequestContextHelper helper, HttpServletResponse response);
}

