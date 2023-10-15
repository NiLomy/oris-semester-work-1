package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.PostDao;
import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDaoImpl implements PostDao<Post> {
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
                        resultSet.getDate("add_date"),
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
                                    resultSet.getDate("add_date"),
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
    public void save(Post post) {
        String sql = "insert into posts (name, category, content, author_id, add_date, likes) values (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, post.getName());
            preparedStatement.setString(2, post.getCategory());
            preparedStatement.setString(3, post.getContent());
            preparedStatement.setInt(4, post.getAuthorId());
            preparedStatement.setDate(5, post.getDate());
            preparedStatement.setInt(6, post.getLikes());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save user into DB.", e);
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
            throw new DbException("Can't save post into DB.", e);
        }
    }
}
