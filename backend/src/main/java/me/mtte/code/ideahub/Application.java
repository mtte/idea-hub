package me.mtte.code.ideahub;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import me.mtte.code.ideahub.auth.SecurityConfigFactory;
import me.mtte.code.ideahub.database.Database;
import me.mtte.code.ideahub.database.PostgresDatabase;
import me.mtte.code.ideahub.responses.ErrorResponse;
import me.mtte.code.ideahub.util.JsonUtil;
import org.pac4j.core.config.Config;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class Application {

    private static Database database;
    private static Config securityConfig;

    public static void main(String[] args) {
        setupDatabase();
        setupSecurityConfig();
        setupSpark();
    }

    private static void setupDatabase() {
        database = new PostgresDatabase();
    }

    private static void setupSecurityConfig() {
        securityConfig = new SecurityConfigFactory(System.getenv("JWT_SALT")).build();
    }

    private static void setupSpark() {
        removeTrailingSlashes();

        enableCORS(System.getenv("CORS_ALLOWED_ORIGINS"), System.getenv("CORS_ALLOWED_METHODS"),
                System.getenv("CORS_ALLOWED_HEADERS"), System.getenv("CORS_ALLOW_CREDENTIALS"));

        handleJsonPayload();

        logExceptions();

        get("/test", (request, response) -> {
            throw new IllegalStateException("dasf");
        });

        path("/api", new Api(database, securityConfig));

        after((req, res) -> {
            res.header("Content-Encoding", "gzip");
            res.type("application/json");
        });
    }

    private static void removeTrailingSlashes() {
        before((request, response) -> {
            String path = request.pathInfo();
            if (path.endsWith("/")) {
                response.redirect(path.substring(0, path.length() - 1));
            }
        });
    }

    private static void enableCORS(final String origin, final String methods, final String headers, final String credentials) {
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            response.header("Access-Control-Allow-Credentials", credentials);
        });
    }

    private static void handleJsonPayload() {
        before((request, response) -> {
            String body = request.body();
            try {
                var parsed = JsonParser.parseString(body);
                if (parsed.isJsonObject()) {
                    var payload = parsed.getAsJsonObject();
                    for (String key : payload.keySet()) {
                        JsonElement jsonElement = payload.get(key);
                        if (jsonElement instanceof JsonPrimitive) {
                            request.attribute(key, jsonElement.getAsString());
                        }
                    }
                }
            } catch (JsonParseException e) {
                // Ignore, probably not a json payload
            }
        });
    }

    private static void logExceptions() {
        exception(Exception.class, (exception, request, response) -> {
            LoggerFactory.getLogger(Application.class).error("Spark request caused exception", exception);
            response.status(500);
            response.body(JsonUtil.toJson(new ErrorResponse("Internal server error")));
        });
    }

}
