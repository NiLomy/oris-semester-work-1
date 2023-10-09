package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.Dao;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements Dao<User> {

    private final Connection connection = DatabaseConnectionProvider.getConnection();

    @Override
    public User get(int id) {
        try {
            //Statement statement = connection.createStatement();
            String sql = "SELECT * from users where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            return getUserByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException("Can't get user from DB.", e);
        }
    }

    @Override
    public User get(String login, String password) {
        try {
            //Statement statement = connection.createStatement();
            String sql = "SELECT * from users where login = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            return getUserByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException("Can't get user from DB.", e);
        }
    }

    private User getUserByStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from users";
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    users.add(
                            new User(
                                    resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("lastname"),
                                    resultSet.getString("email"),
                                    resultSet.getString("login"),
                                    resultSet.getString("password")
                            )
                    );
                }
            }
            return users;
        } catch (SQLException e) {
            throw new DbException("Can't get user list from DB.", e);
        }
    }

    @Override
    public String getEmail(String login, String password) {
        try {
            String sql = "SELECT email from users where login = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {
                if (resultSet.next()) {
                    return resultSet.getString("email");
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Can't get user from DB.", e);
        }
    }

    @Override
    public void save(User user) {
        String sql = "insert into users (name, lastname, email, login, password) values (?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save user into DB.", e);
        }
    }

    @Override
    public void update(User user, String oldLogin) {
        String sql = "update users set name=?, lastname=?, email=?, login=? where login=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, oldLogin);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save user into DB.", e);
        }
    }
}
