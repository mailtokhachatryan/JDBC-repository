package repository.user.impl;

import model.User;
import repository.user.UserRepository;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final Connection connection;

    public UserRepositoryImpl(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
        try {
            this.connection.createStatement().executeUpdate(
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

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users (name,lastname,username,password,age) VALUES (?,?,?,?,?)"
        );
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLastname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setInt(5, user.getAge());

        int i = preparedStatement.executeUpdate();
        System.out.println(i);

        preparedStatement.close();
    }

    @Override
    public void update(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE users SET name = ?, lastname = ?, username = ?, password = ?, age = ? WHERE id = ?"
        );

        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLastname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setInt(5, user.getAge());
        preparedStatement.setLong(6, user.getId());
    }

    @Override
    public User get(Long id) throws SQLException {
        User user = new User();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from users WHERE id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            setUserFields(user, resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> usersList = new ArrayList<>();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from users");
        addUserToListFromResultSet(usersList, resultSet);
        return usersList;
    }


    @Override
    public List<User> findUsersByName(String name) throws SQLException {

        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM users WHERE lower(name) LIKE lower(concat('%',?,'%'))"
        );
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        addUserToListFromResultSet(users, resultSet);
        return users;
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE from users WHERE id = ?");
        preparedStatement.setLong(1, id);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    private void setUserFields(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setLastname(resultSet.getString("lastname"));
        user.setEmail(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setAge(resultSet.getInt("age"));
    }

    private void addUserToListFromResultSet(List<User> usersList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            User user = new User();
            setUserFields(user, resultSet);
            usersList.add(user);
        }
    }
}
