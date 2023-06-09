package service.user;

import model.User;

import java.sql.SQLException;

public interface UserService {

    void register(User user) throws SQLException;

}
