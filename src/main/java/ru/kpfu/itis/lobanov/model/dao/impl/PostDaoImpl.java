package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.PostDao;
import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDaoImpl implements PostDao {
    private final Connection connection = DatabaseConnectionProvider.getConnection();

    @Override
    public Post get(int id) {
        try {
            String sql = "SELECT * from posts where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException("Can't get post from DB.", e);
        }
    }

    @Override
    public Post get(String name, int authorId) {
        try {
            String sql = "SELECT * from posts where name = ? and author_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, authorId);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException("Can't get post from DB.", e);
        }
    }

    private Post getPostByStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return new Post(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getString("content"),
                        resultSet.getInt("author_id"),
                        resultSet.getTimestamp("add_date"),
                        resultSet.getInt("likes")
                );
            }
        }
        return null;
    }

    @Override
    public List<Post> getAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from posts";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Post> posts = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    posts.add(
                            new Post(
                                    resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("category"),
                                    resultSet.getString("content"),
                                    resultSet.getInt("author_id"),
                                    resultSet.getTimestamp("add_date"),
                                    resultSet.getInt("likes")
                            )
                    );
                }
            }
            return posts;
        } catch (SQLException e) {
            throw new DbException("Can't get post list from DB.", e);
        }
    }

    @Override
    public List<Post> getAllFromUser(int authorId) {
        try {
            String sql = "SELECT * from posts where author_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    posts.add(
                            new Post(
                                    resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("category"),
                                    resultSet.getString("content"),
                                    resultSet.getInt("author_id"),
                                    resultSet.getTimestamp("add_date"),
                                    resultSet.getInt("likes")
                            )
                    );
                }
            }
            return posts;
        } catch (SQLException e) {
            throw new DbException("Can't get post list from DB.", e);
        }
    }

    @Override
    public List<Post> getAllFavouritesFromUser(int userId) {
        try {
            String sql = "SELECT * from users_posts where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    posts.add(
                            get(resultSet.getInt("post_id"))
                    );
                }
            }
            return posts;
        } catch (SQLException e) {
            throw new DbException("Can't get post list from DB.", e);
        }
    }

    @Override
    public void save(Post post) {
        String sql = "insert into posts (name, category, content, author_id, add_date, likes) values (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, post.getName());
            preparedStatement.setString(2, post.getCategory());
            preparedStatement.setString(3, post.getContent());
            preparedStatement.setInt(4, post.getAuthorId());
            preparedStatement.setTimestamp(5, post.getDate());
            preparedStatement.setInt(6, post.getLikes());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save user into DB.", e);
        }
    }

    @Override
    public void update(Post post, int id) {
        String sql = "update posts set name=?, category=?, content=?, author_id=?, add_date=?, likes=? where id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, post.getName());
            preparedStatement.setString(2, post.getCategory());
            preparedStatement.setString(3, post.getContent());
            preparedStatement.setInt(4, post.getAuthorId());
            preparedStatement.setTimestamp(5, post.getDate());
            preparedStatement.setInt(6, post.getLikes());
            preparedStatement.setInt(7, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save post into DB.", e);
        }
    }

    @Override
    public void remove(Post post) {
        String sql = "delete from posts where id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, post.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't delete post from DB.", e);
        }
    }

    @Override
    public void saveToFavourites(int user_id, int post_id) {
        String sql = "insert into users_posts (user_id, post_id) values (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, post_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save user into DB.", e);
        }
    }

    @Override
    public void removeFromFavourites(int user_id, int post_id) {
        String sql = "delete from users_posts where user_id = ? and post_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, post_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save post like into DB.", e);
        }
    }

    @Override
    public void updateLikes(String name, int likes) {
        String sql = "update posts set likes = ? where name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, likes);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't update post into DB.", e);
        }
    }
}
