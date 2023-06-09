import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        UserService userService = new UserServiceImpl(databaseConnection);

        User user = new User(1L, "Aghasi", "Khachatryan", "username1", "pass", 27);



        userService.create(user);


        //
//        Connection connection = databaseConnection.getConnection();
//
//        Statement statement = connection.createStatement();
//
//        statement.executeUpdate(
//                """
//                        CREATE TABLE IF NOT EXISTS test (
//                            id serial primary key
//                        )
//                        """);
//
//        ResultSet resultSet = statement.executeQuery(
//                """
//                        SELECT * FROM students
//                        """);
//
//        while (resultSet.next()){
//            System.out.println(resultSet.getLong("id"));
//            System.out.println(resultSet.getString("name"));
//            System.out.println(resultSet.getInt("mog"));
//        }


    }
}