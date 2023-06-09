import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void update(User user) {

    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
