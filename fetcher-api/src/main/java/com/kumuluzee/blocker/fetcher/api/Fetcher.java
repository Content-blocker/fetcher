package com.kumuluzee.blocker.fetcher.api;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Optional;

@ApplicationScoped
public class Fetcher {
    final Logger LOG = LogManager.getLogger(CustomDiscovery.class.getName());

    public Response getFetchedObject() {
        Response fetchedObject = null;
        Client client = ClientBuilder.newBuilder()
                .property("connection.timeout", 20)
                .register(JacksonJsonProvider.class)
                .build();
        WebTarget target = client.target("http://jsonplaceholder.typicode.com");
        target = target.path("/todos");
        try {
            fetchedObject = target.request().get();
        } catch (Exception e) {
            LOG.debug(e.toString());
            return null;
        }
        return fetchedObject;
    }
}