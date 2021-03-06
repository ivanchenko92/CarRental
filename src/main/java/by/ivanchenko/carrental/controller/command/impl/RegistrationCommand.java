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


public class RegistrationCommand implements Command {

   // private static final String ID = "id_user";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String ROLE = "role";
    private static final String MANAGER_REG= "Manager registration completed successfully!";
 // ;

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
                User user  = new User(req.getParameter(NAME), req.getParameter(SURNAME), req.getParameter(PHONE),
                        req.getParameter(PASSWORD),req.getParameter(EMAIL), Integer.parseInt(req.getParameter(ROLE)));

//            if (req.getParameter("role") != null){
//                user.setRole(Integer.parseInt(req.getParameter("role")));
//            }
            userService.register(user);
            //*
            HttpSession session = req.getSession(true);


            if (session.getAttribute("user") == null) {
//                session.setAttribute("user", user);
                return PageResourseManager.getValue(PageParameter.AFTER_REGISTRATION);
            } else {
                req.setAttribute("message",MANAGER_REG);
                return PageResourseManager.getValue(PageParameter.USER_HOME);
            }
        } catch (ServiceException e) {
            req.setAttribute("message", e.getMessage());
            return PageResourseManager.getValue(PageParameter.ERROR_PAGE);
        }
    }

    //????????????
}
