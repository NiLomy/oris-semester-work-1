package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.MessageDao;
import ru.kpfu.itis.lobanov.model.entity.Message;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.constants.LogMessages;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    private final Connection connection = DatabaseConnectionProvider.getConnection();
    public static final String ID_COLUMN = "id";
    public static final String AUTHOR_ID_COLUMN = "author_id";
    public static final String CONTENT_COLUMN = "content";
    public static final String POST_COLUMN = "post";
    public static final String DATE_COLUMN = "date";
    public static final String LIKES_COLUMN = "likes";
    public static final String SELECT_SINGLE_MESSAGE_BY_ID_QUERY = "SELECT * from messages where id = ?";
    public static final String SELECT_SINGLE_MESSAGE_BY_PARAMS_QUERY = "SELECT * from messages where author_id = ? and content = ? and post = ? and date = ? and likes = ?";
    public static final String SELECT_ALL_MESSAGES_BY_POST_QUERY = "SELECT * from messages where post = ?";
    public static final String SELECT_ALL_MESSAGES_QUERY = "SELECT * from messages";
    public static final String SAVE_MESSAGE_QUERY = "insert into messages (author_id, content, post, date, likes) values (?, ?, ?, ?, ?);";
    public static final String UPDATE_MESSAGE_QUERY = "update messages set author_id=?, content=?, post=?, date=?, likes=? where id=?;";
    public static final String REMOVE_MESSAGE_QUERY = "delete from messages where id=?;";
    public static final String UPDATE_MESSAGE_LIKES_QUERY = "update messages set likes = ? where id = ?";
    public static final String SELECT_MOST_FREQUENT_AUTHOR_QUERY = "SELECT author_id FROM messages GROUP BY author_id ORDER BY count(*) DESC LIMIT 1";

    @Override
    public Message get(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_MESSAGE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);

            return getMessageByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_MESSAGE_DB_EXCEPTION, e);
        }
    }

    @Override
    public Message get(int authorId, String content, String post, Timestamp date, int likes) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_MESSAGE_BY_PARAMS_QUERY);
            preparedStatement.setInt(index++, authorId);
            preparedStatement.setString(index++, content);
            preparedStatement.setString(index++, post);
            preparedStatement.setTimestamp(index++, date);
            preparedStatement.setInt(index++, likes);

            return getMessageByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_MESSAGE_DB_EXCEPTION, e);
        }
    }

    private Message getMessageByStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return new Message(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getInt(AUTHOR_ID_COLUMN),
                        resultSet.getString(CONTENT_COLUMN),
                        resultSet.getString(POST_COLUMN),
                        resultSet.getTimestamp(DATE_COLUMN),
                        resultSet.getInt(LIKES_COLUMN)
                );
            }
        }
        return null;
    }

    @Override
    public List<Message> getAllFromPost(String post) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MESSAGES_BY_POST_QUERY);
            preparedStatement.setString(1, post);
            List<Message> messages = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {
                while (resultSet.next()) {
                    messages.add(
                            new Message(
                                    resultSet.getInt(ID_COLUMN),
                                    resultSet.getInt(AUTHOR_ID_COLUMN),
                                    resultSet.getString(CONTENT_COLUMN),
                                    resultSet.getString(POST_COLUMN),
                                    resultSet.getTimestamp(DATE_COLUMN),
                                    resultSet.getInt(LIKES_COLUMN)
                            )
                    );
                }
            }
            return messages;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_MESSAGE_DB_EXCEPTION, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_MESSAGES_QUERY);
            List<Message> messages = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    messages.add(
                            new Message(
                                    resultSet.getInt(ID_COLUMN),
                                    resultSet.getInt(AUTHOR_ID_COLUMN),
                                    resultSet.getString(CONTENT_COLUMN),
                                    resultSet.getString(POST_COLUMN),
                                    resultSet.getTimestamp(DATE_COLUMN),
                                    resultSet.getInt(LIKES_COLUMN)
                            )
                    );
                }
            }
            return messages;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_ALL_MESSAGES_DB_EXCEPTION, e);
        }
    }

    @Override
    public void save(Message message) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_MESSAGE_QUERY);
            preparedStatement.setInt(index++, message.getAuthorId());
            preparedStatement.setString(index++, message.getContent());
            preparedStatement.setString(index++, message.getPost());
            preparedStatement.setTimestamp(index++, message.getDate());
            preparedStatement.setInt(index++, message.getLikes());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.SAVE_MESSAGE_DB_EXCEPTION, e);
        }
    }

    @Override
    public void update(Message message, int id) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MESSAGE_QUERY);
            preparedStatement.setInt(index++, message.getAuthorId());
            preparedStatement.setString(index++, message.getContent());
            preparedStatement.setString(index++, message.getPost());
            preparedStatement.setTimestamp(index++, message.getDate());
            preparedStatement.setInt(index++, message.getLikes());
            preparedStatement.setInt(index++, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.UPDATE_MESSAGE_DB_EXCEPTION, e);
        }
    }

    @Override
    public void remove(Message message) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_MESSAGE_QUERY);
            preparedStatement.setInt(1, message.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.REMOVE_MESSAGE_DB_EXCEPTION, e);
        }
    }

    @Override
    public void updateLikes(int id, int likes) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MESSAGE_LIKES_QUERY);
            preparedStatement.setInt(index++, likes);
            preparedStatement.setInt(index++, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.UPDATE_MESSAGE_DB_EXCEPTION, e);
        }
    }

    @Override
    public int getMostFrequentUserId() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MOST_FREQUENT_AUTHOR_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            int userId = 0;
            if (resultSet != null) {
                while (resultSet.next()) {
                    userId = resultSet.getInt(AUTHOR_ID_COLUMN);
                }
            }
            return userId;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_USER_DB_EXCEPTION, e);
        }
    }
}
