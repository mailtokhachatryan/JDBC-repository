import java.sql.SQLException;
import java.util.List;

public interface UserService {

    void create(User user) throws SQLException;

    void update(User user) throws SQLException;

    User get(long id) throws SQLException;

    List<User> getAll() throws SQLException;

    void delete(Long id) throws SQLException;
}
