package com.github.theimplementer.lucenefacetedsearch.users;

import java.util.HashMap;
import java.util.Map;

import static com.github.theimplementer.lucenefacetedsearch.users.UserDefinition.REAL_NAME;
import static com.github.theimplementer.lucenefacetedsearch.users.UserDefinition.USERNAME;
import static com.github.theimplementer.lucenefacetedsearch.users.UserDefinition.USERS;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class UserQueryBuilder {

    private UserQueryBuilder() {
    }

    public static String createUserTable() {
        return format("create table %s (" +
                "%s varchar(20) not null primary key," +
                "%s varchar(50) not null" +
                ")", USERS, USERNAME, REAL_NAME);
    }

    public static UserInsertQueryBuilder insert(User user) {
        return new UserInsertQueryBuilder(user);
    }

    public static UserSelectQueryBuilder select(String select) {
        return new UserSelectQueryBuilder(select);
    }

    public static UserDeleteQueryBuilder delete(String username) {
        return new UserDeleteQueryBuilder(username);
    }

    public static class UserInsertQueryBuilder {

        private static final String INSERT_QUERY_TEMPLATE = format("insert into %s(%s, %s) values ('%%s', '%%s')", USERS, USERNAME, REAL_NAME);

        private final User user;

        private UserInsertQueryBuilder(User user) {
            this.user = user;
        }

        public String build() {
            return format(INSERT_QUERY_TEMPLATE, user.getUsername(), user.getRealName());
        }
    }

    public static class UserSelectQueryBuilder {

        private static final String SELECT_QUERY_TEMPLATE = format("select %%s from %s", USERS);

        private String select;
        private Map<String, String> whereClauses;

        private UserSelectQueryBuilder(String select) {
            this.select = select;
            this.whereClauses = new HashMap<>();
        }

        public UserSelectQueryBuilder where(String column, String value) {
            whereClauses.put(column, value);
            return this;
        }

        public String build() {
            final StringBuilder query = new StringBuilder(format(SELECT_QUERY_TEMPLATE, select));
            if (whereClauses.isEmpty()) return query.toString();

            query.append(" where ");
            final String where = whereClauses.entrySet().stream()
                    .map((Map.Entry<String, String> entry) -> format("%s = %s", entry.getKey(), entry.getValue()))
                    .collect(joining(" AND "));
            query.append(where);
            return query.toString();
        }
    }

    public static class UserDeleteQueryBuilder {

        private static final String DELETE_QUERY_TEMPLATE = format("delete from %s where %s = '%%s'", USERS, USERNAME);

        private final String username;

        private UserDeleteQueryBuilder(String username) {
            this.username = username;
        }

        public String build() {
            return format(DELETE_QUERY_TEMPLATE, username);
        }
    }
}
