package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.PostDao;
import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.constants.LogMessages;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDaoImpl implements PostDao {
    private final Connection connection = DatabaseConnectionProvider.getConnection();
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String CATEGORY_COLUMN = "category";
    public static final String CONTENT_COLUMN = "content";
    public static final String AUTHOR_ID_COLUMN = "author_id";
    public static final String ADD_DATE_COLUMN = "add_date";
    public static final String LIKES_COLUMN = "likes";
    public static final String POST_ID_COLUMN = "post_id";
    public static final String SELECT_SINGLE_POST_BY_ID_QUERY = "SELECT * from posts where id = ?";
    public static final String SELECT_SINGLE_POST_BY_NAME_AND_AUTHOR_QUERY = "SELECT * from posts where name = ? and author_id = ?";
    public static final String SELECT_ALL_POSTS_QUERY = "SELECT * from posts";
    public static final String SELECT_ALL_POSTS_BY_AUTHOR_QUERY = "SELECT * from posts where author_id = ?";
    public static final String SELECT_ALL_FAVOURITES_BY_USER = "SELECT * from users_posts where user_id = ?";
    public static final String SAVE_POST_QUERY = "insert into posts (name, category, content, author_id, add_date, likes) values (?, ?, ?, ?, ?, ?);";
    public static final String UPDATE_POST_QUERY = "update posts set name=?, category=?, content=?, author_id=?, add_date=?, likes=? where id=?;";
    public static final String REMOVE_POST_QUERY = "delete from posts where id=?;";
    public static final String SAVE_POST_TO_FAVOURITES_QUERY = "insert into users_posts (user_id, post_id) values (?, ?);";
    public static final String REMOVE_POST_FROM_FAVOURITES_QUERY = "delete from users_posts where user_id = ? and post_id = ?;";
    public static final String UPDATE_POST_LIKES_QUERY = "update posts set likes = ? where name = ?";

    @Override
    public Post get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_POST_BY_ID_QUERY);
            preparedStatement.setInt(1, id);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_POST_DB_EXCEPTION, e);
        }
    }

    @Override
    public Post get(String name, int authorId) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_POST_BY_NAME_AND_AUTHOR_QUERY);
            preparedStatement.setString(index++, name);
            preparedStatement.setInt(index++, authorId);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_SINGLE_POST_DB_EXCEPTION, e);
        }
    }

    private Post getPostByStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return new Post(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NAME_COLUMN),
                        resultSet.getString(CATEGORY_COLUMN),
                        resultSet.getString(CONTENT_COLUMN),
                        resultSet.getInt(AUTHOR_ID_COLUMN),
                        resultSet.getTimestamp(ADD_DATE_COLUMN),
                        resultSet.getInt(LIKES_COLUMN)
                );
            }
        }
        return null;
    }

    @Override
    public List<Post> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_POSTS_QUERY);
            List<Post> posts = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    posts.add(
                            new Post(
                                    resultSet.getInt(ID_COLUMN),
                                    resultSet.getString(NAME_COLUMN),
                                    resultSet.getString(CATEGORY_COLUMN),
                                    resultSet.getString(CONTENT_COLUMN),
                                    resultSet.getInt(AUTHOR_ID_COLUMN),
                                    resultSet.getTimestamp(ADD_DATE_COLUMN),
                                    resultSet.getInt(LIKES_COLUMN)
                            )
                    );
                }
            }
            return posts;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_ALL_POSTS_DB_EXCEPTION, e);
        }
    }

    @Override
    public List<Post> getAllFromUser(int authorId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_POSTS_BY_AUTHOR_QUERY);
            preparedStatement.setInt(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    posts.add(
                            new Post(
                                    resultSet.getInt(ID_COLUMN),
                                    resultSet.getString(NAME_COLUMN),
                                    resultSet.getString(CATEGORY_COLUMN),
                                    resultSet.getString(CONTENT_COLUMN),
                                    resultSet.getInt(AUTHOR_ID_COLUMN),
                                    resultSet.getTimestamp(ADD_DATE_COLUMN),
                                    resultSet.getInt(LIKES_COLUMN)
                            )
                    );
                }
            }
            return posts;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_ALL_POSTS_DB_EXCEPTION, e);
        }
    }

    @Override
    public List<Post> getAllFavouritesFromUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FAVOURITES_BY_USER);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    posts.add(
                            get(resultSet.getInt(POST_ID_COLUMN))
                    );
                }
            }
            return posts;
        } catch (SQLException e) {
            throw new DbException(LogMessages.GET_ALL_POSTS_DB_EXCEPTION, e);
        }
    }

    @Override
    public void save(Post post) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_POST_QUERY);
            preparedStatement.setString(index++, post.getName());
            preparedStatement.setString(index++, post.getCategory());
            preparedStatement.setString(index++, post.getContent());
            preparedStatement.setInt(index++, post.getAuthorId());
            preparedStatement.setTimestamp(index++, post.getDate());
            preparedStatement.setInt(index++, post.getLikes());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.SAVE_POST_DB_EXCEPTION, e);
        }
    }

    @Override
    public void update(Post post, int id) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_POST_QUERY);
            preparedStatement.setString(index++, post.getName());
            preparedStatement.setString(index++, post.getCategory());
            preparedStatement.setString(index++, post.getContent());
            preparedStatement.setInt(index++, post.getAuthorId());
            preparedStatement.setTimestamp(index++, post.getDate());
            preparedStatement.setInt(index++, post.getLikes());
            preparedStatement.setInt(index++, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.UPDATE_POST_DB_EXCEPTION, e);
        }
    }

    @Override
    public void remove(Post post) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_POST_QUERY);
            preparedStatement.setInt(1, post.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.REMOVE_POST_DB_EXCEPTION, e);
        }
    }

    @Override
    public void saveToFavourites(int user_id, int post_id) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_POST_TO_FAVOURITES_QUERY);
            preparedStatement.setInt(index++, user_id);
            preparedStatement.setInt(index++, post_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.SAVE_POST_DB_EXCEPTION, e);
        }
    }

    @Override
    public void removeFromFavourites(int user_id, int post_id) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_POST_FROM_FAVOURITES_QUERY);
            preparedStatement.setInt(index++, user_id);
            preparedStatement.setInt(index++, post_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.REMOVE_POST_DB_EXCEPTION, e);
        }
    }

    @Override
    public void updateLikes(String name, int likes) {
        try {
            int index = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_POST_LIKES_QUERY);
            preparedStatement.setInt(index++, likes);
            preparedStatement.setString(index++, name);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(LogMessages.UPDATE_POST_DB_EXCEPTION, e);
        }
    }
}
