package by.ivanchenko.carrental.dao.impl;

import by.ivanchenko.carrental.bean.user.User;
import by.ivanchenko.carrental.dao.UserDAO;
import by.ivanchenko.carrental.dao.DAOException;
import by.ivanchenko.carrental.dao.impl.connection.ConnectionPool;
import by.ivanchenko.carrental.dao.impl.connection.ConnectionPoolException;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    //public final static String     SQL команды для PreparedStatement
    private static final String ID = "id_user";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String ROLE = "id_role";

    private static final String REGISTER_USER = "INSERT INTO users ('name', 'surname', 'phone', 'password', 'email') VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE 'id_user' = ?";  // или  по email
    private static final String LOG_IN = "SELECT * FROM users WHERE email = ? and password = ?";

    private  static final  ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public User logIn(String email, String password) throws DAOException {
          // private final static?
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

         try {
             connection = connectionPool.takeConnection();
             preparedStatement = connection.prepareStatement(LOG_IN);
             preparedStatement.setString(1, email);
             preparedStatement.setString(2, password);
             preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            resultSet.last();                        // required?

//            if (resultSet.getRow() == 1) {
//                return new User(.......)            required ?
//            }

            return  new User(resultSet.getString(NAME), resultSet.getString(SURNAME),
                    resultSet.getString(EMAIL));
            // надо ли полные данные передавать в юзера ?

            //  to do    con.pool close    код с примера моего   с  finally
         } catch (SQLException e) {
             //log.error("some message", e);
             throw new DAOException("Error while authorizing User", e);
         } catch (ConnectionPoolException e) {
             throw new DAOException("Error in Connection Pool while authorizing new User", e);
         }
         finally {
             connectionPool.closeConnection(connection, preparedStatement, resultSet);     // проверить в конце
         }
    }

    @Override
    // метод сделать synhronized, чтобы одновременно два  одинаковых логина не зарегать
    //метод добавить на  проверку  существующего логина в БД
    public void registration(String name, String surname, String phone, String password, String email) throws DAOException { //, int idRole ?

    // private DaoFactory  daoFactory = DaoFactory.getInstance();    ?
            User user = new User();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(REGISTER_USER, Statement.RETURN_GENERATED_KEYS);   //  для получения id из БД
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, email);
            preparedStatement.setInt(6, 2);  // уст роли customer

            preparedStatement.executeUpdate();

// role  не передаем, а уcтанавливаем setUserRole (email, 2);   или в запросе устанавливаем по стандарту 2 ?

            preparedStatement.executeUpdate();

            // надо ли юзера получать?
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();  // while?
            //int role = resultSet.getInt("id_role");    Role  or int      или 1 вместо id_role
            user = new User(resultSet.getString(NAME), resultSet.getString(SURNAME),
                    resultSet.getString(PHONE), resultSet.getString(PASSWORD),
                    resultSet.getString(EMAIL), resultSet.getInt(ID), resultSet.getInt(ROLE));
        } catch (SQLException e) {  // to do      likewise upper 'logIn'
            //log.error("some message", e);
             throw new DAOException("Error while adding new User", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Error in Connection Pool while adding new User", e);
        }
        finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);     // проверить в конце
        }
    }



    @Override
    public void delete(int idUser) {

            Connection connection = null;
            Statement statement = null;
            // ConnectionPool connecctionPool = null;  git
            ResultSet resultSet = null;
            PreparedStatement preparedStatement = null;
        try {
          preparedStatement = connection.prepareStatement(DELETE_USER);
          preparedStatement.setInt(1, idUser);
          preparedStatement.executeUpdate();

        } catch (SQLException e) {
         //   throw new DaoException(e);
        }
    }
}
