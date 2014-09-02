package com.github.theimplementer.lucenefacetedsearch;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.glassfish.jersey.jetty.JettyHttpContainer;
import org.glassfish.jersey.server.ResourceConfig;

import static org.eclipse.jetty.util.resource.Resource.newClassPathResource;
import static org.glassfish.jersey.server.ContainerFactory.createContainer;

public class MainApplication extends ResourceConfig {

    public static void main(String[] args) throws Exception {
        final Server server = new Server(9999);

        final JettyHttpContainer jerseyContainer = createContainer(JettyHttpContainer.class, new ApplicationResourceConfig());

        final ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setBaseResource(newClassPathResource("."));
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        final HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, jerseyContainer});

        server.setHandler(handlerList);
        server.start();
        server.join();
    }
}
