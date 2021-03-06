package by.ivanchenko.carrental.controller.DEL;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import by.ivanchenko.carrental.bean.car.Car;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@WebServlet(name = "by.ivanchenko.carrental.controller.DEL.Control", value = "/by.ivanchenko.carrental.controller.DEL.Control")
//@WebServlet("/adress");
public class Control extends HttpServlet {
    private  static  final  long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connect = null;
            Statement statement = null;
            ResultSet resultset = null;

            connect = DriverManager.getConnection("jdbc:mysql://localhost/car_rental", "root", "admin");
            statement = connect.createStatement();
            resultset = statement.executeQuery("SELECT * FROM cars");

            //          PrintWriter print = response.getWriter();
            List<Car> cars = new ArrayList<Car>();

            while (resultset.next()) {
                Car car = new Car();

                car.setId(resultset.getInt(1));
                // request.setAttribute("id",car.id);
                car.setName(resultset.getString(2));
                //request.setAttribute("name",car.name);  ...
                car.setEngineCapacity(resultset.getDouble(3));
                car.setTransmission(resultset.getString(4));
                car.setYear(resultset.getInt(5));
                car.setDrive(resultset.getString(6));
                car.setTank(resultset.getInt(7));
                car.setConsumption(resultset.getDouble(8));
                car.setFuel(resultset.getString(9));
                car.setBodyType(resultset.getString(10));
                car.setPrice(resultset.getInt(11));
                car.setMileage(resultset.getInt(12));

                cars.add(car);
//
//                request.setAttribute("id",id);
//                request.setAttribute("name", name);
//                request.setAttribute("engineCapacity", engineCapacity);
//                request.setAttribute("transmission", tranmission);
//                request.setAttribute("year", year);
//                request.setAttribute("drive", drive);
//                request.setAttribute("tank", tank);
//                request.setAttribute("consumption", consumption);
//                request.setAttribute("fuel", fuel);
//                request.setAttribute("bodyType", bodyType);
//                request.setAttribute("price", price);
//                request.setAttribute("mileage", mileage);
            }

            /* ????.jd2 les 10 Apr 24, 2020     1:14
            ?????? ?????????? ??????????????????????: */
            String email;
            String password;
            String commandName;

            email = request.getParameter("email");
            password = request.getParameter("password");
            commandName = request.getParameter("command");

            System.out.println("command: " + commandName);
            System.out.println(email +" - " + password);

               //servletContext  ?????????? ?? session
//request.getSession().setAttribute("car ", car);

            HttpSession session = request.getSession();
            session.setAttribute("cars",cars);

            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);

        } catch (ClassNotFoundException exception) {
            //
        } catch (SQLException exception) {
            //
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).setAttribute("local", request.getParameter("local"));
        request.getRequestDispatcher("index.jsp").forward(request,response);


        System.out.println("req.attr - " + request.getAttribute("local"));
        System.out.println("session.attr - " + request.getSession().getAttribute("local"));
        System.out.println("req.param - " + request.getParameter("local"));
        System.out.println("---------------");
    }

}
