package ru.kpfu.itis.lobanov.model.dao.impl;

import ru.kpfu.itis.lobanov.model.dao.PostLikeDao;
import ru.kpfu.itis.lobanov.model.entity.PostLike;
import ru.kpfu.itis.lobanov.util.DatabaseConnectionProvider;
import ru.kpfu.itis.lobanov.util.exception.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostLikeDaoImpl implements PostLikeDao<PostLike> {
    private final Connection connection = DatabaseConnectionProvider.getConnection();
    @Override
    public PostLike get(int id) {
        try {
            String sql = "SELECT * from likes_for_posts where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException("Can't get post like from DB.", e);
        }
    }


    @Override
    public PostLike get(String nickname, String post) {
        try {
            String sql = "SELECT * from likes_for_posts where nickname = ? and post = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nickname);
            preparedStatement.setString(2, post);

            return getPostByStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DbException("Can't get post like from DB.", e);
        }
    }

    private PostLike getPostByStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return new PostLike(
                        resultSet.getInt("id"),
                        resultSet.getString("nickname"),
                        resultSet.getString("post")
                );
            }
        }
        return null;
    }

    @Override
    public List<PostLike> getAllFromPost(String post) {
        try {
            String sql = "SELECT * from likes_for_posts where post = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, post);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PostLike> postLikes = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    postLikes.add(
                            new PostLike(
                                    resultSet.getInt("id"),
                                    resultSet.getString("nickname"),
                                    resultSet.getString("post")
                            )
                    );
                }
            }
            return postLikes;
        } catch (SQLException e) {
            throw new DbException("Can't get post likes list from DB.", e);
        }
    }

    @Override
    public List<PostLike> getAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from likes_for_posts";
            ResultSet resultSet = statement.executeQuery(sql);
            List<PostLike> postLikes = new ArrayList<>();

            if (resultSet != null) {
                while (resultSet.next()) {
                    postLikes.add(
                            new PostLike(
                                    resultSet.getInt("id"),
                                    resultSet.getString("nickname"),
                                    resultSet.getString("post")
                            )
                    );
                }
            }
            return postLikes;
        } catch (SQLException e) {
            throw new DbException("Can't get post likes list from DB.", e);
        }
    }

    @Override
    public void save(PostLike postLike) {
        String sql = "insert into likes_for_posts (nickname, post) values (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, postLike.getNickname());
            preparedStatement.setString(2, postLike.getPost());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save post like into DB.", e);
        }
    }

    @Override
    public void remove(PostLike postLike) {
        String sql = "delete from likes_for_posts where nickname = ? and post = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, postLike.getNickname());
            preparedStatement.setString(2, postLike.getPost());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save post like into DB.", e);
        }
    }
}
