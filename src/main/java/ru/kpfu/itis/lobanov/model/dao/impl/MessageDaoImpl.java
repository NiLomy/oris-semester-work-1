package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.MessageDao;
import ru.kpfu.itis.lobanov.model.entity.Message;
import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao<Message> {
    private final Connection connection = DatabaseConnectionProvider.getConnection();

    @Override
    public Message get(int id) {
        try {
            String sql = "SELECT * from messages where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            return getMessageByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException("Can't get message from DB.", e);
        }
    }

    public Message get(int authorId, String content, String post, Date date, int likes) {
        try {
            String sql = "SELECT * from messages where author_id = ? and content = ? and post = ? and date = ? and likes = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, authorId);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, post);
            preparedStatement.setDate(4, date);
            preparedStatement.setInt(5, likes);

            return getMessageByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException("Can't get message from DB.", e);
        }
    }

    private Message getMessageByStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return new Message(
                        resultSet.getInt("id"),
                        resultSet.getInt("author_id"),
                        resultSet.getString("content"),
                        resultSet.getString("post"),
                        resultSet.getDate("date"),
                        resultSet.getInt("likes")
                );
            }
        }
        return null;
    }

    @Override
    public List<Message> getAllFromPost(String post) {
        try {
            String sql = "SELECT * from messages where post = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, post);
            List<Message> messages = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {
                while (resultSet.next()) {
                    messages.add(
                            new Message(
                                    resultSet.getInt("id"),
                                    resultSet.getInt("author_id"),
                                    resultSet.getString("content"),
                                    resultSet.getString("post"),
                                    resultSet.getDate("date"),
                                    resultSet.getInt("likes")
                            )
                    );
                }
            }
            return messages;
        } catch (SQLException e) {
            throw new DbException("Can't get message from DB.", e);
        }
    }

    @Override
    public List<Message> getAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from messages";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Message> messages = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    messages.add(
                            new Message(
                                    resultSet.getInt("id"),
                                    resultSet.getInt("author_id"),
                                    resultSet.getString("content"),
                                    resultSet.getString("post"),
                                    resultSet.getDate("date"),
                                    resultSet.getInt("likes")
                            )
                    );
                }
            }
            return messages;
        } catch (SQLException e) {
            throw new DbException("Can't get message list from DB.", e);
        }
    }

    @Override
    public void save(Message message) {
        String sql = "insert into messages (author_id, content, post, date, likes) values (?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getAuthorId());
            preparedStatement.setString(2, message.getContent());
            preparedStatement.setString(3, message.getPost());
            preparedStatement.setDate(4, message.getDate());
            preparedStatement.setInt(5, message.getLikes());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save message into DB.", e);
        }
    }

    @Override
    public void updateLikes(int id, int likes) {
        String sql = "update messages set likes = ? where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, likes);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save post into DB.", e);
        }
    }

    @Override
    public int getMostFrequentUserId() {
        try {
            String sql = "SELECT author_id FROM messages GROUP BY author_id ORDER BY count(*) DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            int userId = 0;
            if (resultSet != null) {
                while (resultSet.next()) {
                    userId = resultSet.getInt("author_id");
                }
            }
            return userId;
        } catch (SQLException e) {
            throw new DbException("Can't save post into DB.", e);
        }
    }
}
