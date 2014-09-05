package com.github.theimplementer.lucenefacetedsearch.users;

import com.insightfullogic.lambdabehave.JunitSuiteRunner;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.theimplementer.lucenefacetedsearch.users.User.user;
import static com.github.theimplementer.lucenefacetedsearch.users.UserDefinition.REAL_NAME;
import static com.github.theimplementer.lucenefacetedsearch.users.UserDefinition.USERNAME;
import static com.github.theimplementer.lucenefacetedsearch.users.UserDefinition.USERS;
import static com.github.theimplementer.lucenefacetedsearch.users.UserQueryBuilder.createUserTable;
import static com.github.theimplementer.lucenefacetedsearch.users.UserQueryBuilder.select;
import static com.insightfullogic.lambdabehave.Suite.describe;
import static java.lang.String.format;
import static java.util.Optional.empty;

@RunWith(JunitSuiteRunner.class)
public class DatabaseUsersRepositoryTest {

    {
        describe("the database users repository", it -> {
            final Connection connection = getTestConnection();
            final UsersRepository repository = new DatabaseUsersRepository(connection);

            it.isSetupWith(() -> connection.createStatement().executeUpdate(format("delete from %s", USERS)));

            it.should("allow adding a new user", expect -> {
                repository.add(user("username", "real name"));

                final List<User> users = getUsers(connection);
                expect.that(users.size()).is(1);
                expect.that(users.get(0).getUsername()).is("username");
                expect.that(users.get(0).getRealName()).is("real name");
            });

            it.should("not allow add another user with the same username", expect -> {
                repository.add(user("username", "real name"));
                expect.exception(DuplicateUserException.class, () -> repository.add(user("username", "real name")));
            });

            it.should("return an empty optional when searching for a non-existent user", expect -> {
                final Optional<User> user = repository.findByUsername("username");

                expect.that(user).is(empty());
            });

            it.should("return the matching user if it exists", expect -> {
                repository.add(user("username", "real name"));
                final Optional<User> userOptional = repository.findByUsername("username");

                expect.that(userOptional.isPresent()).is(true);
                final User user = userOptional.get();
                expect.that(user.getUsername()).is("username");
                expect.that(user.getRealName()).is("real name");
            });

            it.should("return the user count", expect -> {
                expect.that(repository.count()).is(0);

                repository.add(user("username", "real name"));
                expect.that(repository.count()).is(1);
            });

            it.should("allow deleting users", expect -> {
                repository.add(user("username", "real name"));
                repository.delete("username");

                expect.that(repository.count()).is(0);
            });
        });
    }

    private static Connection getTestConnection() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            final Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:testDb", "SA", "");
            final Statement statement = connection.createStatement();
            statement.executeUpdate(createUserTable());
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<User> getUsers(Connection connection) throws Exception {
        final Statement statement = connection.createStatement();
        final ResultSet resultSet = statement.executeQuery(select("*").build());
        final List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            final String username = resultSet.getString(USERNAME);
            final String realName = resultSet.getString(REAL_NAME);
            users.add(user(username, realName));
        }

        return users;
    }
}
