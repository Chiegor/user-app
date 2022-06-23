package ru.app.userapp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.app.userapp.exception.ApplicationException;
import ru.app.userapp.model.DBConstants;
import ru.app.userapp.model.User;
import ru.app.userapp.sql.PostgreSQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    PostgreSQLConnection worker;

    public UserDaoImpl() {
        this.worker = new PostgreSQLConnection();
    }

    // #1
    @Override
    public void getAllUsers() {
        try {
            String query = "select * from " + DBConstants.U_TABLE + " order by " + DBConstants.U_USER_NAME;
            PreparedStatement pst = worker.getConnection().prepareStatement(query);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(DBConstants.USER_NAME));
            }
            pst.close();
        } catch (SQLException e) {
            throw new ApplicationException("Get all users failed");
        }
    }

    // #2
    @Override
    public void getAllCities() {
        try {
            String query = "select * from " + DBConstants.C_TABLE + " order by " + DBConstants.C_CITY_ID;
            PreparedStatement pst = worker.getConnection().prepareStatement(query);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(DBConstants.CITY_NAME));
            }
            pst.close();
        } catch (SQLException e) {
            throw new ApplicationException("Get all cities failed");
        }
    }

    // #3
    @Override
    public void getAllCityWhereUserLived(String userName) {
        try {
            String query = "select distinct " + DBConstants.C_CITY_NAME +
                    " from " + DBConstants.U_TABLE + ", " + DBConstants.UCL_TABLE + ", " + DBConstants.C_TABLE +
                    " where " + DBConstants.U_USER_ID + " = " + DBConstants.UCL_USER_ID +
                    " and " + DBConstants.C_CITY_ID + " = " + DBConstants.UCL_CITY_ID +
                    " and " + DBConstants.U_USER_NAME + " = '" + userName + "'";

            PreparedStatement statement = worker.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(DBConstants.CITY_NAME));
            }
            statement.close();
        } catch (SQLException e) {
            throw new ApplicationException("Get all city where user lived failed");
        }
    }

    // #4
    @Override
    public void getAllCityWhereUserWorked(String userName) {
        try {
            String query = "select distinct " + DBConstants.C_CITY_NAME +
                    " from " + DBConstants.U_TABLE + ", " + DBConstants.UCW_TABLE + ", " + DBConstants.C_TABLE +
                    " where " + DBConstants.U_USER_ID + " = " + DBConstants.UCW_USER_ID +
                    " and " + DBConstants.C_CITY_ID + " = " + DBConstants.UCW_CITY_ID +
                    " and " + DBConstants.U_USER_NAME + " = '" + userName + "'";
            PreparedStatement statement = worker.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(DBConstants.CITY_NAME));
            }
            statement.close();
        } catch (SQLException e) {
            throw new ApplicationException("Get all city where user worked failed");
        }
    }

    // #5
    @Override
    public Long createUser(User user) throws ApplicationException {
        String userName = user.getName();
        Set<String> cityLived = user.getCitiesLived();
        Set<String> cityWorked = user.getCitiesWorked();
        long userId;

        try {
            String query = "INSERT INTO " + DBConstants.U_TABLE + " (" + DBConstants.USER_NAME + ") Values ('" + userName + "')";
            PreparedStatement statement = worker.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            log.debug("creating new user {}", user);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new ApplicationException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getLong(1);
                    log.debug("created user id=" + userId);
                    System.out.println("User id is: " + userId);
                } else {
                    throw new ApplicationException("Creating user failed, no ID obtained.");
                }
            }
            statement.close();

            for (String city : cityLived) {
                long cityId = getCityId(city);
                if (cityId != 0L) {
                    putCityIdUserIdInTable(userId, cityId, DBConstants.UCL_TABLE);
                } else {
                    long cityID = createCity(city);
                    putCityIdUserIdInTable(userId, cityID, DBConstants.UCL_TABLE);
                }
            }

            for (String city : cityWorked) {
                long cityId = getCityId(city);
                if (cityId != 0L) {
                    putCityIdUserIdInTable(userId, cityId, DBConstants.UCW_TABLE);
                } else {
                    long cityID = createCity(city);
                    putCityIdUserIdInTable(userId, cityID, DBConstants.UCW_TABLE);
                }
            }
            System.out.println("User " + userName + " add");

        } catch (SQLException e) {
            throw new ApplicationException("error creating user in db", e);
        }
        return userId;

    }

    public Long createCity(String cityName) {
        long cityId;
        try {
            String query = "insert into " + DBConstants.C_TABLE + " (" + DBConstants.CITY_NAME + ") values ('" + cityName + "')";
            PreparedStatement statement = worker.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            log.debug("creating new city {}", cityName);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new ApplicationException("Creating city failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cityId = generatedKeys.getLong(1);
                    log.debug("created city id=" + cityId);
                    System.out.println(cityName + " id is: " + cityId);
                } else {
                    throw new ApplicationException("Creating city failed, no ID obtained.");
                }
            }
            statement.close();
        } catch (SQLException e) {
            throw new ApplicationException("error creating city in db", e);
        }
        return cityId;
    }

    @Override
    public void putCityIdUserIdInTable(long userId, long cityId, String tableName) {
        try {
            String query = "insert into " + tableName + " (" +
                    DBConstants.USER_ID + ", " + DBConstants.CITY_ID + ") " +
                    "values (" + userId + ", " + cityId + ")";
            PreparedStatement statement = worker.getConnection().prepareStatement(query);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new ApplicationException("putCityIdUserIdInTable error");
            }
            statement.close();
        } catch (SQLException e) {
            throw new ApplicationException("ERROR put City Id User Id In Table", e);
        }
    }

    @Override
    public Long getCityId(String cityName) {
        try {
            long id = 0L;
            String query = "select " + DBConstants.CITY_ID + " from " + DBConstants.C_TABLE + " where " + DBConstants.CITY_NAME + " = '" + cityName + "'";
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                id = resultSet.getLong(DBConstants.C_CITY_ID);
            }
            statement.close();
            return id;
        } catch (SQLException e) {
            throw new ApplicationException("");
        }
    }

    // #6
    @Override
    public Long getUserIdByName(String userName) {
        try {
            long id = 0L;
            String query = "select " + DBConstants.USER_ID + " from " + DBConstants.U_TABLE + " where " + DBConstants.USER_NAME + " = '" + userName + "'";
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                id = resultSet.getLong(DBConstants.U_USER_ID);
            }
            statement.close();

            if (id == 0L) {
                System.out.println("User " + userName + " not found ");
                System.exit(123);
                return null;
            } else {
                return id;
            }
        } catch (SQLException e) {
            throw new ApplicationException("");
        }
    }

    @Override
    public void getAllUserByCityLived(String cityName) {
        try {
            String query = "select distinct " + DBConstants.U_USER_NAME +
                    " from " + DBConstants.C_TABLE +
                    " join " + DBConstants.UCL_TABLE +
                    " on " + DBConstants.C_CITY_ID + " = " + DBConstants.UCL_CITY_ID +
                    " and " + DBConstants.C_CITY_NAME + " = '" + cityName + "' " +
                    " join " + DBConstants.U_TABLE +
                    " on " + DBConstants.U_USER_ID + " = " + DBConstants.UCL_USER_ID;
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(DBConstants.U_USER_NAME));
            }
            statement.close();
        } catch (SQLException e) {
            throw new ApplicationException("");
        }
    }

    @Override
    public void getAllUserByCityWorked(String cityName) {
        try {
            String query = "select distinct " + DBConstants.U_USER_NAME +
                    " from " + DBConstants.C_TABLE +
                    " join " + DBConstants.UCW_TABLE +
                    " on " + DBConstants.C_CITY_ID + " = " + DBConstants.UCW_CITY_ID +
                    " and " + DBConstants.C_CITY_NAME + " = '" + cityName + "' " +
                    " join " + DBConstants.U_TABLE +
                    " on " + DBConstants.U_USER_ID + " = " + DBConstants.UCW_USER_ID;
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(DBConstants.U_USER_NAME));
            }
            statement.close();
        } catch (SQLException e) {
            throw new ApplicationException("");
        }
    }

    @Override
    public void deleteUser(long id) {
        try {
            String query = "delete from " + DBConstants.U_TABLE + " where " + DBConstants.U_USER_ID + " = " + id + "";
            Statement statement = worker.getConnection().createStatement();
            int affectedRows = statement.executeUpdate(query);
            if (affectedRows == 0) {
                throw new ApplicationException("Delete user failed, no rows affected.");
            } else {
                System.out.println("User deletion was successful");
            }
            statement.close();
        } catch (SQLException e) {
            throw new ApplicationException("");
        }
    }

    @Override
    public void addCityWhereUserLived(long userId, Set<String> cityLived) {
        long cityId;
        for (String city : cityLived) {
            cityId = getCityId(city);
            if (cityId == 0L) {
                cityId = createCity(city);
            }
            putCityIdUserIdInTable(userId, cityId, DBConstants.UCL_TABLE);
        }
    }

    @Override
    public void addCityWhereUserWorked(long userId, Set<String> cityWorked) {
        long cityId;
        for (String city : cityWorked) {
            cityId = getCityId(city);
            if (cityId == 0L) {
                cityId = createCity(city);
            }
            putCityIdUserIdInTable(userId, cityId, DBConstants.UCW_TABLE);
        }
    }
}
