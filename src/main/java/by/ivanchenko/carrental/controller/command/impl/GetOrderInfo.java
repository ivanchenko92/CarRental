package by.ivanchenko.carrental.controller.command.impl;

import by.ivanchenko.carrental.bean.car.Car;
import by.ivanchenko.carrental.bean.order.Order;
import by.ivanchenko.carrental.bean.user.User;
import by.ivanchenko.carrental.controller.PageParameter;
import by.ivanchenko.carrental.controller.PageResourseManager;
import by.ivanchenko.carrental.controller.command.Command;
import by.ivanchenko.carrental.service.OrderService;
import by.ivanchenko.carrental.service.ServiceException;
import by.ivanchenko.carrental.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Date;
import java.util.List;

public class GetOrderInfo implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            OrderService orderService= ServiceFactory.getInstance().getOrderService();
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute("user");
            int role = user.getRole();
            List<Order> orders;
            if ((role == 3) ^ (role == 4)) {
               orders = orderService.getInfoAll();
            } else {
                int id = user.getId();
                orders = orderService.getInfo(id);
            }
          session.setAttribute("orders", orders);
          return PageResourseManager.getValue(PageParameter.USER_HOME);
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            return PageResourseManager.getValue(PageParameter.USER_HOME);
        }
    }
}
