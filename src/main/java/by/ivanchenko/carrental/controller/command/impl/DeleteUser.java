package by.ivanchenko.carrental.controller.command.impl;

import by.ivanchenko.carrental.bean.user.User;
import by.ivanchenko.carrental.controller.PageParameter;
import by.ivanchenko.carrental.controller.PageResourseManager;
import by.ivanchenko.carrental.controller.command.Command;
import by.ivanchenko.carrental.service.ServiceException;
import by.ivanchenko.carrental.service.ServiceFactory;
import by.ivanchenko.carrental.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DeleteUser implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            userService.delete(user);
            session.removeAttribute("user");
            return PageResourseManager.getValue(PageParameter.MAIN);
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            return PageResourseManager.getValue(PageParameter.ERROR_PAGE);
        }
}
}
