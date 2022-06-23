package ru.app.userapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.app.userapp.exception.ValidationException;
import ru.app.userapp.model.User;
import ru.app.userapp.service.UserService;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class UserControllerImpl implements UserController {

    private static final Logger log = LoggerFactory.getLogger(UserControllerImpl.class);

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = Objects.requireNonNull(userService, "userService must be provided");
    }

    @Override
    public long createUser(String userName, Set<String> citiesLived, Set<String> citiesWorked) {
        validate(userName, this::validateUserName,
                "invalid user name length. User name length must be [1-64]");

        validate(citiesLived, this::validateCityLived,
                "invalid list of cities where user lived. " +
                        "List should have at least one city, city name length must be [1-64]");

        validate(citiesWorked, this::validateCityWorked,
                "invalid list of cities where user worked. " +
                        "List should have at least one city, city name length must be [1-64]");

        log.info("validation user data success");

        User user = new User(null, userName, citiesLived, citiesWorked);

        return userService.createUser(user);

    }

    @Override
    public void getAllUsers() {
        userService.getAllUsers();
    }

    @Override
    public void getAllCities() {
        userService.getAllCities();
    }

    @Override
    public Long getUserIdByName(String userName) {
        return userService.getUserIdByName(userName);
    }

    // В РАБОТЕ
    @Override
    public void getAllUserByCityLived(String cityName) {
        validate(cityName, this::validateCityName,
                "invalid city name length. City name length must be [1-64]");
        userService.getAllUserByCityLived(cityName);
    }

    @Override
    public void getAllUserByCityWorked(String cityName) {
        validate(cityName, this::validateCityName,
                "invalid city name length. City name length must be [1-64]");
        userService.getAllUserByCityWorked(cityName);
    }

    @Override
    public void getAllCityWhereUserLived(String userName) {
        validate(userName, this::validateUserName,
                "invalid user name length. User name length must be [1-64]");
        userService.getAllCityWhereUserLived(userName);
    }

    @Override
    public void getAllCityWhereUserWorked(String userName) {
        validate(userName, this::validateUserName,
                "invalid user name length. User name length must be [1-64]");
        userService.getAllCityWhereUserWorked(userName);
    }

    @Override
    public void updateByUserName(String userName) throws IOException {
        validate(userName, this::validateUserName,
                "invalid user name length. User name length must be [1-64]");
        userService.updateByUserName(userName);
    }

    @Override
    public void deleteUserByName(String userName) {
        validate(userName, this::validateUserName,
                "invalid user name length. User name length must be [1-64]");
        userService.deleteUserByName(userName);
    }

    @Override
    public void deleteUserById(long id) {
        userService.deleteUserById(id);
    }

    private boolean validateCityName(String cityName) {
        return cityName != null && cityName.length() >= User.MIN_CITY_NAME_LENGTH
                && cityName.length() <= User.MAX_CITY_NAME_LENGTH;
    }

    private boolean validateUserName(String userName) {
        return userName != null && userName.length() >= User.MIN_USER_NAME_LENGTH
                && userName.length() <= User.MAX_USER_NAME_LENGTH;
    }

    private boolean validateCityLived(Set<String> citiesLived) {
        return citiesLived != null && !citiesLived.isEmpty() &&
                citiesLived.stream().allMatch(cityName -> validateCityName(cityName));
    }

    private boolean validateCityWorked(Set<String> citiesWorked) {
        return citiesWorked != null && !citiesWorked.isEmpty() &&
                citiesWorked.stream().allMatch(cityName -> validateCityName(cityName));
    }

    private <T> void validate(T obj, Function<T, Boolean> validator, String errorMessage) {
        if (!validator.apply(obj)) {
            throw new ValidationException(errorMessage);
        }
    }
}

