package com.kumuluzee.blocker.fetcher.api;

import com.kumuluz.ee.discovery.annotations.RegisterService;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.*;

@RegisterService(value = "fetcher", ttl = 40, pingInterval = 20, environment = "test", version = "1.0.0")
@ApplicationPath("/fetcher")
public class RestApplication extends Application {
}