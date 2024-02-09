package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.MessageLikeDao;
import ru.kpfu.itis.lobanov.model.entity.MessageLike;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.constants.LogMessages;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageLikeDaoImpl implements MessageLikeDao {
    private final Connection connection = DatabaseConnectionProvider.getConnection();
    public static final String ID_COLUMN = "id";
    public static final String AUTHOR_COLUMN = "author";
    public static final String MESSAGE_ID_COLUMN = "message_id";
    public static final String SELECT_SINGLE_MESSAGE_LIKE_BY_ID_QUERY = "SELECT * from likes_for_messages where id = ?";
    public static final String SELECT_SINGLE_MESSAGE_LIKE_BY_AUTHOR_AND_MESSAGE_QUERY = "SELECT * from likes_for_messages where author = ? and message_id = ?";
    public static final String SELECT_ALL_MESSAGE_LIKES_BY_MESSAGE_QUERY = "SELECT * from likes_for_messages where message_id = ?";
    public static final String SELECT_ALL_MESSAGE_LIKES_QUERY = "SELECT * from likes_for_messages";
    public static final String SAVE_MESSAGE_LIKE_QUERY = "insert into likes_for_messages (author, message_id) values (?, ?);";
    public static final String UPDATE_MESSAGE_LIKE_QUERY = "update likes_for_messages set author=?, message_id=? where id=?;";
    public static final String REMOVE_MESSAGE_LIKE_QUERY = "delete from likes_for_messages where author = ? and message_id = ?;";

    @Override
    public MessageLike get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_MESSAGE_LIKE_BY_ID_QUERY);
            preparedStatement.setInt(1, id);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_MESSAGE_LIKE_DB_EXCEPTION, e);
        }
    }

    @Override
    public MessageLike get(String author, int messageId) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_MESSAGE_LIKE_BY_AUTHOR_AND_MESSAGE_QUERY);
            preparedStatement.setString(index++, author);
            preparedStatement.setInt(index++, messageId);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_MESSAGE_LIKE_DB_EXCEPTION, e);
        }
    }

    private MessageLike getPostByStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return new MessageLike(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(AUTHOR_COLUMN),
                        resultSet.getInt(MESSAGE_ID_COLUMN)
                );
            }
        }
        return null;
    }

    @Override
    public List<MessageLike> getAllFromMessage(int messageId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MESSAGE_LIKES_BY_MESSAGE_QUERY);
            preparedStatement.setInt(1, messageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MessageLike> messageLikes = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    messageLikes.add(
                            new MessageLike(
                                    resultSet.getInt(ID_COLUMN),
                                    resultSet.getString(AUTHOR_COLUMN),
                                    resultSet.getInt(MESSAGE_ID_COLUMN)
                            )
                    );
                }
            }
            return messageLikes;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_ALL_MESSAGE_LIKES_DB_EXCEPTION, e);
        }
    }

    @Override
    public List<MessageLike> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_MESSAGE_LIKES_QUERY);
            List<MessageLike> messageLikes = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    messageLikes.add(
                            new MessageLike(
                                    resultSet.getInt(ID_COLUMN),
                                    resultSet.getString(AUTHOR_COLUMN),
                                    resultSet.getInt(MESSAGE_ID_COLUMN)
                            )
                    );
                }
            }
            return messageLikes;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_ALL_MESSAGE_LIKES_DB_EXCEPTION, e);
        }
    }

    @Override
    public void save(MessageLike messageLike) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_MESSAGE_LIKE_QUERY);
            preparedStatement.setString(index++, messageLike.getAuthor());
            preparedStatement.setInt(index++, messageLike.getMessageId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.SAVE_MESSAGE_LIKE_DB_EXCEPTION, e);
        }
    }

    @Override
    public void update(MessageLike messageLike, int id) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MESSAGE_LIKE_QUERY);
            preparedStatement.setString(index++, messageLike.getAuthor());
            preparedStatement.setInt(index++, messageLike.getMessageId());
            preparedStatement.setInt(index++, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.UPDATE_MESSAGE_LIKE_DB_EXCEPTION, e);
        }
    }

    @Override
    public void remove(MessageLike messageLike) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_MESSAGE_LIKE_QUERY);
            preparedStatement.setString(index++, messageLike.getAuthor());
            preparedStatement.setInt(index++, messageLike.getMessageId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.REMOVE_MESSAGE_LIKE_DB_EXCEPTION, e);
        }
    }
}
