package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.PostLikeDao;
import ru.kpfu.itis.lobanov.model.entity.PostLike;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.constants.LogMessages;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostLikeDaoImpl implements PostLikeDao {
    private final Connection connection = DatabaseConnectionProvider.getConnection();
    public static final String ID_COLUMN = "id";
    public static final String NICKNAME_COLUMN = "nickname";
    public static final String POST_COLUMN = "post";
    public static final String SELECT_SINGLE_POST_LIKE_BY_ID_QUERY = "SELECT * from likes_for_posts where id = ?";
    public static final String SELECT_SINGLE_POST_LIKE_BY_NICKNAME_AND_POST_QUERY = "SELECT * from likes_for_posts where nickname = ? and post = ?";
    public static final String SELECT_ALL_POST_LIKES_BY_POST_QUERY = "SELECT * from likes_for_posts where post = ?";
    public static final String SELECT_ALL_POST_LIKES_QUERY = "SELECT * from likes_for_posts";
    public static final String SAVE_POST_LIKE_QUERY = "insert into likes_for_posts (nickname, post) values (?, ?);";
    public static final String UPDATE_POST_LIKE_QUERY = "update likes_for_posts set nickname=?, post=? where id=?;";
    public static final String REMOVE_POST_LIKE_QUERY = "delete from likes_for_posts where id = ?;";

    @Override
    public PostLike get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_POST_LIKE_BY_ID_QUERY);
            preparedStatement.setInt(1, id);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_POST_LIKE_DB_EXCEPTION, e);
        }
    }


    @Override
    public PostLike get(String nickname, String post) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_POST_LIKE_BY_NICKNAME_AND_POST_QUERY);
            preparedStatement.setString(index++, nickname);
            preparedStatement.setString(index++, post);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_POST_LIKE_DB_EXCEPTION, e);
        }
    }

    private PostLike getPostByStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return new PostLike(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NICKNAME_COLUMN),
                        resultSet.getString(POST_COLUMN)
                );
            }
        }
        return null;
    }

    @Override
    public List<PostLike> getAllFromPost(String post) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_POST_LIKES_BY_POST_QUERY);
            preparedStatement.setString(1, post);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PostLike> postLikes = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    postLikes.add(
                            new PostLike(
                                    resultSet.getInt(ID_COLUMN),
                                    resultSet.getString(NICKNAME_COLUMN),
                                    resultSet.getString(POST_COLUMN)
                            )
                    );
                }
            }
            return postLikes;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_ALL_POST_LIKES_DB_EXCEPTION, e);
        }
    }

    @Override
    public List<PostLike> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_POST_LIKES_QUERY);
            List<PostLike> postLikes = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    postLikes.add(
                            new PostLike(
                                    resultSet.getInt(ID_COLUMN),
                                    resultSet.getString(NICKNAME_COLUMN),
                                    resultSet.getString(POST_COLUMN)
                            )
                    );
                }
            }
            return postLikes;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_ALL_POST_LIKES_DB_EXCEPTION, e);
        }
    }

    @Override
    public void save(PostLike postLike) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_POST_LIKE_QUERY);
            preparedStatement.setString(index++, postLike.getNickname());
            preparedStatement.setString(index++, postLike.getPost());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.SAVE_POST_LIKE_DB_EXCEPTION, e);
        }
    }

    @Override
    public void update(PostLike postLike, int id) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_POST_LIKE_QUERY);
            preparedStatement.setString(index++, postLike.getNickname());
            preparedStatement.setString(index++, postLike.getPost());
            preparedStatement.setInt(index++, postLike.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.UPDATE_POST_LIKE_DB_EXCEPTION, e);
        }
    }

    @Override
    public void remove(PostLike postLike) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_POST_LIKE_QUERY);
            preparedStatement.setInt(1, postLike.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.REMOVE_POST_LIKE_DB_EXCEPTION, e);
        }
    }
}
