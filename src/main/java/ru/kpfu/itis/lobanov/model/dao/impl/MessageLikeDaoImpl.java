package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.MessageLikeDao;
import ru.kpfu.itis.lobanov.model.entity.MessageLike;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageLikeDaoImpl implements MessageLikeDao<MessageLike> {
    private final Connection connection = DatabaseConnectionProvider.getConnection();
    @Override
    public MessageLike get(int id) {
        try {
            String sql = "SELECT * from likes_for_messages where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException("Can't get post like from DB.", e);
        }
    }

    @Override
    public MessageLike get(String author, int messageId) {
        try {
            String sql = "SELECT * from likes_for_messages where author = ? and message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, author);
            preparedStatement.setInt(2, messageId);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException("Can't get post like from DB.", e);
        }
    }

    private MessageLike getPostByStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return new MessageLike(
                        resultSet.getInt("id"),
                        resultSet.getString("author"),
                        resultSet.getInt("message_id")
                );
            }
        }
        return null;
    }

    @Override
    public List<MessageLike> getAllFromMessage(int messageId) {
        try {
            String sql = "SELECT * from likes_for_messages where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MessageLike> messageLikes = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    messageLikes.add(
                            new MessageLike(
                                    resultSet.getInt("id"),
                                    resultSet.getString("author"),
                                    resultSet.getInt("message_id")
                            )
                    );
                }
            }
            return messageLikes;
        } catch (SQLException e) {
            throw new DbException("Can't get post likes list from DB.", e);
        }
    }

    @Override
    public List<MessageLike> getAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from likes_for_messages";
            ResultSet resultSet = statement.executeQuery(sql);
            List<MessageLike> messageLikes = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    messageLikes.add(
                            new MessageLike(
                                    resultSet.getInt("id"),
                                    resultSet.getString("author"),
                                    resultSet.getInt("message_id")
                            )
                    );
                }
            }
            return messageLikes;
        } catch (SQLException e) {
            throw new DbException("Can't get post likes list from DB.", e);
        }
    }

    @Override
    public void save(MessageLike messageLike) {
        String sql = "insert into likes_for_messages (author, message_id) values (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, messageLike.getAuthor());
            preparedStatement.setInt(2, messageLike.getMessageId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save post like into DB.", e);
        }
    }

    @Override
    public void remove(MessageLike messageLike) {
        String sql = "delete from likes_for_messages where author = ? and message_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, messageLike.getAuthor());
            preparedStatement.setInt(2, messageLike.getMessageId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save post like into DB.", e);
        }
    }
}
