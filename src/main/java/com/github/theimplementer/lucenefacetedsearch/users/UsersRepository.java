package com.github.theimplementer.lucenefacetedsearch.users;

import java.util.Optional;

public interface UsersRepository {
    void add(User user) throws Exception;

    Optional<User> findByUsername(String username) throws Exception;

    int count() throws Exception;

    void delete(String username) throws Exception;
}
