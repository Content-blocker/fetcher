package com.kumuluzee.blocker.fetcher.api;

import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Log
@ApplicationScoped
@Path("/api")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class FetcherApi {
    static Optional<WebTarget> aiTarget = CustomDiscovery.discover("ai", "test", "1.0.0");
    static Optional<WebTarget> providerTarget = CustomDiscovery.discover("provider", "test", "1.0.0");
    static Optional<WebTarget> fetcherTarget = CustomDiscovery.discover("fetcher", "test", "1.0.0");
    static String aiString = (aiTarget.isPresent() ? aiTarget.get().getUri().toString() : "Empty");
    static String providerString = (providerTarget.isPresent() ? providerTarget.get().getUri().toString() : "Empty");
    static String fetcherString = (fetcherTarget.isPresent() ? fetcherTarget.get().getUri().toString() : "Empty");

    @Inject
    Fetcher fetcher;

    @GET
    @Timed
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getResources() {
        String links = "";
        if(fetcherTarget.isPresent()){
            links += "<a href='"+ fetcherString + "/fetcher/api/fetched'>fetcher/api/fetched</a><br>";
            links += "<a href='"+ fetcherString + "/fetcher/api/integrations'>fetcher/api/integrations</a><br>";
            links += "<a href='"+ fetcherString + "/fetcher/health'>fetcher/health</a><br>";
            links += "<div>plane</div><br>";
        }
        return "Hellow world! <br> I fetch. <br><br>" + links;
    }

    @GET
    @Timed
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/integrations")
    public String integrations() {
        String out = "<body> <h5> ai integrations </h5>";

        out += "<div> ai: ";
        if (aiTarget.isPresent()) {
            out += "ai: " + aiString + "<br>";
            out += "<a href='"+ aiString + "/ai/api'>ai/api</a><br>";
        }
        else out+= "missing <br>";
        out += "</div>";

        out += "<div> provider: ";
        if (aiTarget.isPresent()) {
            out += "provider: " + providerString + "<br>";
            out += "<a href='"+ providerString + "/provider/api'>provider/api</a><br>";
        }
        else out+= "missing <br>";
        out += "</div>";

        out += "<div> fetcher: ";
        if (aiTarget.isPresent()) {
            out += "fetcher: " + fetcherString + "<br>";
            out += "<a href='"+ fetcherString + "/fetcher/api'>fetcher/api</a><br>";
        }
        else out+= "missing <br>";
        out += "</div>";

        out += "</body>";
        return out;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/fetched")
    public Response fetched() {
        Response fetched = fetcher.getFetchedObject();
        if(fetched == null){
            return Response
                    .status(Response.Status.EXPECTATION_FAILED)
                    .entity("{\"status\":\"failed\"}")
                    .build();
        }
        return Response
                .status(fetched.getStatus())
                .entity(fetched.getEntity())
                .build();
    }
}
