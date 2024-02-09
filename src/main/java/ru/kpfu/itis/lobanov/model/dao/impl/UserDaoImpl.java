package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.UserDao;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.constants.LogMessages;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final Connection connection = DatabaseConnectionProvider.getConnection();
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String LASTNAME_COLUMN = "lastname";
    public static final String EMAIL_COLUMN = "email";
    public static final String LOGIN_COLUMN = "login";
    public static final String PASSWORD_COLUMN = "password";
    public static final String IMAGE_URL_COLUMN = "image_url";
    public static final String ABOUT_ME_COLUMN = "about_me";
    public static final String SELECT_SINGLE_USER_BY_ID_QUERY = "SELECT * from users where id = ?";
    public static final String SELECT_SINGLE_USER_BY_NICKNAME_QUERY = "SELECT * from users where login = ?";
    public static final String SELECT_SINGLE_USER_BY_NICKNAME_AND_PASSWORD_QUERY = "SELECT * from users where login = ? and password = ?";
    public static final String SELECT_ALL_USERS_QUERY = "SELECT * from users";
    public static final String SELECT_EMAIL_BY_NICKNAME_AND_PASSWORD_QUERY = "SELECT email from users where login = ? and password = ?";
    public static final String SAVE_USER_QUERY = "insert into users (name, lastname, email, login, password) values (?, ?, ?, ?, ?);";
    public static final String UPDATE_USER_QUERY = "update users set name=?, lastname=?, email=?, login=?, about_me=? where id=?;";
    public static final String UPDATE_IMAGE_URL_BY_NICKNAME_QUERY = "update users set image_url=? where login=?;";
    public static final String UPDATE_PASSWORD_BY_NICKNAME_QUERY = "update users set password=? where login=?;";
    public static final String REMOVE_USER_QUERY = "delete from users where id=?;";

    @Override
    public User get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_USER_BY_ID_QUERY);
            preparedStatement.setInt(1, id);

            return getUserByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_USER_DB_EXCEPTION, e);
        }
    }

    @Override
    public User get(String nickname) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_USER_BY_NICKNAME_QUERY);
            preparedStatement.setString(1, nickname);

            return getUserByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_USER_DB_EXCEPTION, e);
        }
    }

    @Override
    public User get(String login, String password) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_USER_BY_NICKNAME_AND_PASSWORD_QUERY);
            preparedStatement.setString(index++, login);
            preparedStatement.setString(index++, password);

            return getUserByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_USER_DB_EXCEPTION, e);
        }
    }

    private User getUserByStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NAME_COLUMN),
                        resultSet.getString(LASTNAME_COLUMN),
                        resultSet.getString(EMAIL_COLUMN),
                        resultSet.getString(LOGIN_COLUMN),
                        resultSet.getString(PASSWORD_COLUMN),
                        resultSet.getString(IMAGE_URL_COLUMN),
                        resultSet.getString(ABOUT_ME_COLUMN)
                );
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS_QUERY);
            List<User> users = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    users.add(
                            new User(
                                    resultSet.getInt(ID_COLUMN),
                                    resultSet.getString(NAME_COLUMN),
                                    resultSet.getString(LASTNAME_COLUMN),
                                    resultSet.getString(EMAIL_COLUMN),
                                    resultSet.getString(LOGIN_COLUMN),
                                    resultSet.getString(PASSWORD_COLUMN),
                                    resultSet.getString(IMAGE_URL_COLUMN),
                                    resultSet.getString(ABOUT_ME_COLUMN)
                            )
                    );
                }
            }
            return users;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_ALL_USERS_DB_EXCEPTION, e);
        }
    }

    @Override
    public String getEmail(String login, String password) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMAIL_BY_NICKNAME_AND_PASSWORD_QUERY);
            preparedStatement.setString(index++, login);
            preparedStatement.setString(index++, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {
                if (resultSet.next()) {
                    return resultSet.getString(EMAIL_COLUMN);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_USER_DB_EXCEPTION, e);
        }
    }

    @Override
    public void save(User user) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_QUERY);
            preparedStatement.setString(index++, user.getName());
            preparedStatement.setString(index++, user.getLastname());
            preparedStatement.setString(index++, user.getEmail());
            preparedStatement.setString(index++, user.getLogin());
            preparedStatement.setString(index++, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.SAVE_USER_DB_EXCEPTION, e);
        }
    }

    @Override
    public void update(User user, int id) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
            preparedStatement.setString(index++, user.getName());
            preparedStatement.setString(index++, user.getLastname());
            preparedStatement.setString(index++, user.getEmail());
            preparedStatement.setString(index++, user.getLogin());
            preparedStatement.setString(index++, user.getAboutMe());
            preparedStatement.setInt(index++, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.UPDATE_USER_DB_EXCEPTION, e);
        }
    }

    @Override
    public void updateImageUrl(String nickName, String imageUrl) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_IMAGE_URL_BY_NICKNAME_QUERY);
            preparedStatement.setString(index++, imageUrl);
            preparedStatement.setString(index++, nickName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.UPDATE_USER_DB_EXCEPTION, e);
        }
    }

    @Override
    public void updatePassword(String nickName, String password) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_BY_NICKNAME_QUERY);
            preparedStatement.setString(index++, password);
            preparedStatement.setString(index++, nickName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.UPDATE_USER_DB_EXCEPTION, e);
        }
    }

    @Override
    public void remove(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_QUERY);
            preparedStatement.setInt(1, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.REMOVE_USER_DB_EXCEPTION, e);
        }
    }
}
