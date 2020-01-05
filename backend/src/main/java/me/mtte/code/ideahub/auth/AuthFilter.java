package me.mtte.code.ideahub.auth;

import org.pac4j.core.config.Config;
import org.pac4j.sparkjava.SecurityFilter;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The auth filter authenticates the user and checks if he is authroized.
 *
 * This does not apply for OPTIONS requests.
 */
public class AuthFilter extends SecurityFilter {

    public AuthFilter(Config config, Role... roles) {
        super(config, "HeaderClient", Arrays.stream(roles)
                .map(Role::toString)
                .collect(Collectors.joining(",")));
    }

    @Override
    public void handle(Request request, Response response) {
        if (request.requestMethod().equals("OPTIONS")) {
            LoggerFactory.getLogger(getClass()).debug("Received OPTIONS request -> skipping auth");
            return;
        }

        super.handle(request, response);
    }
}
