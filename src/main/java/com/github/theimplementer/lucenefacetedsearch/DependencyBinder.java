package com.github.theimplementer.lucenefacetedsearch;

import com.github.theimplementer.lucenefacetedsearch.users.DatabaseUsersRepository;
import com.github.theimplementer.lucenefacetedsearch.users.UsersRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.sql.Connection;
import java.util.Properties;

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(PropertiesFactory.class).
                to(Properties.class);
        bindFactory(ConnectionFactory.class).
                to(Connection.class);
        bindAsContract(DatabaseUsersRepository.class).
                to(UsersRepository.class);
    }
}
