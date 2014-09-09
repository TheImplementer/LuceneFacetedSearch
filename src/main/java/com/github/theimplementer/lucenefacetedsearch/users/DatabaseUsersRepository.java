package com.github.theimplementer.lucenefacetedsearch.users;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import static com.github.theimplementer.lucenefacetedsearch.users.User.user;
import static com.github.theimplementer.lucenefacetedsearch.users.UserDefinition.REAL_NAME;
import static com.github.theimplementer.lucenefacetedsearch.users.UserDefinition.USERNAME;
import static com.github.theimplementer.lucenefacetedsearch.users.UserQueryBuilder.insert;
import static com.github.theimplementer.lucenefacetedsearch.users.UserQueryBuilder.select;

public class DatabaseUsersRepository implements UsersRepository {

    private final Connection connection;

    @Inject
    public DatabaseUsersRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(User user) throws Exception {
        if (userExists(user)) {
            throw new DuplicateUserException(user.getUsername());
        }
        final Statement statement = connection.createStatement();
        statement.executeUpdate(insert(user).build());
    }

    @Override
    public Optional<User> findByUsername(String username) throws Exception {
        final Statement statement = connection.createStatement();
        final ResultSet queryResult = statement.executeQuery(select("*").where(USERNAME, username).build());
        if (queryResult.next()) {
            return Optional.of(user(queryResult.getString(USERNAME), queryResult.getString(REAL_NAME)));
        }
        return Optional.empty();
    }

    @Override
    public int count() throws Exception {
        final Statement statement = connection.createStatement();
        final ResultSet resultSet = statement.executeQuery(select("count(*)").build());
        resultSet.next();
        return resultSet.getInt(1);
    }

    @Override
    public void delete(String username) throws Exception {
        final Statement statement = connection.createStatement();
        statement.executeUpdate(UserQueryBuilder.delete(username).build());
    }

    private boolean userExists(User user) throws Exception {
        final Statement statement = connection.createStatement();
        final ResultSet userResultSet = statement.executeQuery(select("*").where(USERNAME, user.getUsername()).build());
        return userResultSet.next();
    }
}
