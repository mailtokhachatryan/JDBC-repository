package service.user.impl;

import model.User;
import repository.user.UserRepository;
import service.user.UserService;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) throws SQLException {
        registerValidation(user);
        userRepository.create(user);
    }

    private void registerValidation(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RuntimeException("Email must not be blank");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException("Password must not be blank");
        }
        if (user.getPassword().length() >= 8) {
            throw new RuntimeException("Password must be less then 8 symbols");
        }
        if (!Pattern.compile("\"^(.+)@(\\\\S+)$\"").matcher(user.getEmail()).matches()) {
            throw new RuntimeException("Invalid email");
        }
    }
}