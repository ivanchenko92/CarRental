package by.ivanchenko.carrental.dao.impl;

import by.ivanchenko.carrental.bean.user.User;
import by.ivanchenko.carrental.dao.UserDAO;
import by.ivanchenko.carrental.dao.DAOException;
import by.ivanchenko.carrental.dao.impl.connection.ConnectionPool;
import by.ivanchenko.carrental.dao.impl.connection.ConnectionPoolException;


import java.sql.*;

public class UserDAOImpl implements UserDAO {
    private static final String ID = "id_user";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String ROLE = "id_role";

    private static final String REGISTER_USER = "INSERT INTO users (name, surname, phone, password, email, id_role) VALUES (?, ?, ?, ?, ?,?)";  // +
    private static final String LOG_IN = "SELECT * FROM users WHERE email = ? and password = ?";
    private static final String UPDATE_INFO = "UPDATE users SET name = ?, surname = ?, phone = ?, password = ?, email = ? WHERE id_user = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE email = ? AND id_user = ?";
    private static final String FIND_EMAIL = "SELECT email FROM users WHERE email = ?";


    @Override
    public User logIn(String email, String password) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

         try {
             connection = ConnectionPool.getInstance().takeConnection();
//             ConnectionPool connectionPool = new ConnectionPool();
//             connection = connectionPool.takeConnection();

             preparedStatement = connection.prepareStatement(LOG_IN);
             preparedStatement.setString(1, email);
             preparedStatement.setString(2, password);
             resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return  new User(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(SURNAME),
                        resultSet.getString(PHONE), resultSet.getString(PASSWORD), resultSet.getString(EMAIL), resultSet.getInt(ROLE));
            } else {
                 throw new DAOException("User with this email doesn't exist");
                //page with error
            }
         } catch (SQLException e) {
             //log.error("some message", e);
             throw new DAOException("Error while authorizing User", e);
         } catch (ConnectionPoolException e) {
             throw new DAOException("Error in Connection Pool while authorizing new User", e);
         }
         finally {
            ConnectionPool.getInstance().closeConnection(connection, preparedStatement, resultSet);     // ?????????????????? ?? ??????????
         }
    }

    @Override
    // ?????????? ?????????????? synhronized, ?????????? ???????????????????????? ??????  ???????????????????? ???????????? ???? ????????????????
    //?????????? ???????????????? ????  ????????????????  ?????????????????????????? ???????????? ?? ????
    public void registration(User user) throws DAOException { //, int idRole ?
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
//            ConnectionPool connectionPool = new ConnectionPool();
//            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(FIND_EMAIL);
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                throw new DAOException("User with this email is already exist");
                //?????? ?????????????????????   page?
            }

            preparedStatement = connection.prepareStatement(REGISTER_USER);   // , Statement.RETURN_GENERATED_KEYS ?????? ?????????????????? id ???? ????
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setInt(6, user.getRole());

            preparedStatement.executeUpdate();

//            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            generatedKeys.next();
//            System.out.println(generatedKeys.getInt(1));  //ok

        } catch (SQLException e) {  // to do      likewise upper 'logIn'
            //log.error("some message", e);
             throw new DAOException("Error while adding new User", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Error in Connection Pool while adding new User", e);
        }
        finally {
            ConnectionPool.getInstance().closeConnection(connection, preparedStatement, resultSet);     // ?????????????????? ?? ??????????
        }
    }

    @Override
    public void updateInfo(User user) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
//            ConnectionPool connectionPool = new ConnectionPool();
//            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(UPDATE_INFO);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setInt(6,user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {  // to do      likewise upper 'logIn'
            //log.error("some message", e);
            throw new DAOException("Error while editing User", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Error in Connection Pool while editing  User", e);
        }
        finally {
            ConnectionPool.getInstance().closeConnection(connection, preparedStatement);     // ?????????????????? ?? ??????????
        }
    }

    @Override
    public void delete(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
          connection = ConnectionPool.getInstance().takeConnection();
//            ConnectionPool connectionPool = new ConnectionPool();
//            connection = connectionPool.takeConnection();

          preparedStatement = connection.prepareStatement(DELETE_USER);
          preparedStatement.setString(1, user.getEmail());
          preparedStatement.setInt(2,user.getId());
          preparedStatement.executeUpdate();
        } catch (SQLException e) {  // to do      likewise upper 'logIn'
            //log.error("some message", e);
            throw new DAOException("Error while deleting User", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Error in Connection Pool while deleting User", e);
        }
        finally {
            ConnectionPool.getInstance().closeConnection(connection, preparedStatement);
        }
    }
}
