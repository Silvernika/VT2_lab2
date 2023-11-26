package by.bsuir.wt.fourth.controller.filter;

import by.bsuir.wt.fourth.controller.context.RequestContext;
import by.bsuir.wt.fourth.controller.context.RequestContextHelper;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebFilter ("/hotel")
public class LanguageFilter implements Filter {
    private static final String ATTRIBUTE = "language";
    private static final String RU = "ru";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterchain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        RequestContextHelper requestHelper = new RequestContextHelper(request);
        RequestContext requestContext = requestHelper.createContext();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String sessionLanguage = (String) requestContext.getSessionAttribute(ATTRIBUTE);
        if (sessionLanguage == null) {
            requestContext.addSessionAttribute(ATTRIBUTE, RU);
            requestHelper.updateRequest(requestContext);
        }

        String requestLanguage = request.getParameter(ATTRIBUTE);
        if (requestLanguage != null) {
            requestContext.addSessionAttribute(ATTRIBUTE, requestLanguage);
            String requestString = removeLanguageParameter(request);
            requestHelper.updateRequest(requestContext);
            response.sendRedirect(requestString);
            return;
        }

        filterchain.doFilter(request, response);
    }

    private String removeLanguageParameter(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder requestString = new StringBuilder(request.getContextPath() + "/hotel?");
        parameterMap.entrySet().stream()
                .filter(e -> !ATTRIBUTE.equals(e.getKey()))
                .forEach(e -> requestString.append(e.getKey()).append("=").append(e.getValue()[0]).append("&"));
        requestString.deleteCharAt(requestString.length() - 1);
        return requestString.toString();
    }

    @Override
    public void destroy() {

    }
}
