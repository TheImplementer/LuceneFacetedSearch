package com.github.theimplementer.lucenefacetedsearch;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.util.Properties;

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(PropertiesFactory.class).
                to(Properties.class);

    }
}
