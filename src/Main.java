import model.User;
import repository.user.UserRepository;
import repository.user.impl.UserRepositoryImpl;
import util.DatabaseConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        UserRepository userRepo = new UserRepositoryImpl(databaseConnection);

        User user = new User(1L, "Aghasi", "Khachatryan", "username1", "pass", 27);
        User user2 = new User(2L, "Arayik", "Harutyunyan", "username2", "pass", 29);
        User user3 = new User(3L, "Eduard", "Parunakyan", "username3", "pass", 23);

//        userRepo.create(user);
        userRepo.create(user2);
        userRepo.create(user3);

        User user1 = userRepo.get(1L);
        System.out.println(user1.getName());
    }
}