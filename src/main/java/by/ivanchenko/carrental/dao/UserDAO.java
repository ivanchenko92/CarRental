package by.ivanchenko.carrental.dao;
import by.ivanchenko.carrental.bean.user.User;

public interface UserDAO {
     public User logIn(String email, String password);    // эти данные надо в сессию ложить + id, role.    не надо возвращать  User
     public  void registration(String name, String surname, String phone, String password, String email);
     public void delete(int idUser);
}
