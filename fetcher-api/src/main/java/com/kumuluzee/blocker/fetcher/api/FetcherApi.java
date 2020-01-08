package com.kumuluzee.blocker.fetcher.api;

import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Log
@RequestScoped
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

    @GET
    @Timed
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getResources() {
        String links = "";
        if(fetcherTarget.isPresent()){
            links += "<a href='"+ fetcherString + "/fetcher/api/integrations'>fetcher/api/integrations</a><br>";
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
}
