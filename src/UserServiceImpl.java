import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final DatabaseConnection databaseConnection;

    public UserServiceImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        try {
            this.databaseConnection.getConnection().createStatement().executeUpdate(
                    """
                            CREATE TABLE IF NOT EXISTS users (
                            id bigserial primary key,
                            name varchar(255) not null,
                            lastname varchar(255) not null,
                            username varchar(255) not null unique,
                            password varchar(255) not null,
                            age integer not null
                            )
                            """);
        } catch (SQLException e) {
            System.out.println("Connection exception");
        }

    }

    @Override
    public void create(User user) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        String query = String.format("""
                INSERT INTO users (
                id,
                name,
                lastname,
                username,
                password,
                age) VALUES (%d,'%s','%s','%s','%s',%d)
                """, user.getId(), user.getName(), user.getLastname(), user.getUsername(), user.getPassword(), user.getAge());

        Statement statement = connection.createStatement();
        int i = statement.executeUpdate(query);
        System.out.println(i);

        statement.close();
    }

    @Override
    public void update(User user) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        String newName = "ChangedName";
        String newLastName = "ChangedLastName";
        String newUsername = "ChangedUsername";
        String newPassword = "ChangedPassword";
        int newAge = 0;
        String format = String.format("""
                    UPDATE users 
                    SET 
                        name = '%s',
                        lastname = '%s',
                        username = '%s',
                        password = '%s',
                        age = %d
                    where id = %d
                """, newName, newLastName, newUsername, newPassword, newAge, user.getId());

        connection.createStatement().executeUpdate(format);
    }

    @Override
    public User get(long id) throws SQLException {
        User user = new User();

        Connection connection = databaseConnection.getConnection();

        String format = String.format("""  
                    SELECT * from users 
                    WHERE id = %d
                """, id);


        ResultSet resultSet = connection.createStatement().executeQuery(format);

        while (resultSet.next()) {
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setLastname(resultSet.getString("lastname"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setAge(resultSet.getInt("age"));
        }

        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> usersList = new ArrayList<>();

        Connection connection = databaseConnection.getConnection();

        String format = String.format("""
                    SELECT * from users
                """);

        ResultSet resultSet = connection.createStatement().executeQuery(format);


        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setLastname(resultSet.getString("lastname"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setAge(resultSet.getInt("age"));

            usersList.add(user);
        }

        return usersList;
    }


    @Override
    public void delete(Long id) throws SQLException {

        Connection connection = databaseConnection.getConnection();

        String format = String.format("""
                    DELETE from users
                    WHERE id = %d
                """, id);

        connection.createStatement().executeUpdate(format);
    }
}
