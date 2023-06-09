import java.sql.SQLException;
import java.util.List;

public interface UserService {

    void create(User user) throws SQLException;

    void update(User user);

    User get(Long id);

    List<User> getAll();

    void delete(Long id);
}
