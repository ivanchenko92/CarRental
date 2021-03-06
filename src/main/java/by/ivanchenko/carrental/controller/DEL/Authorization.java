package by.ivanchenko.carrental.controller.DEL;

import by.ivanchenko.carrental.controller.command.impl.AuthorizationCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/authorization")
public class Authorization extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        if ("authorization".equals(req.getParameter("command"))) {
            req.getRequestDispatcher("after_registration.jsp").forward(req, resp);
            System.out.println("forward after authorization OK");
            AuthorizationCommand authorizationCommand = new AuthorizationCommand();
            authorizationCommand.execute(req, resp);
        }
    }
}
