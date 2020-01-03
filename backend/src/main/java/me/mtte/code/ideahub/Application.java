package me.mtte.code.ideahub;

import me.mtte.code.ideahub.auth.SecurityConfigFactory;
import me.mtte.code.ideahub.database.Database;
import me.mtte.code.ideahub.database.PostgresDatabase;
import org.pac4j.core.config.Config;

import static spark.Spark.*;

public class Application {

    private static Database database;
    private static Config securityConfig;

    public static void main(String[] args) {
        setupDatabase();
        setupSecurityConfig();
        setupApi();
    }

    private static void setupDatabase() {
        database = new PostgresDatabase();
    }

    private static void setupSecurityConfig() {
        securityConfig = new SecurityConfigFactory(System.getenv("JWT_SALT")).build();
    }

    private static void setupApi() {
        port(80);

        before((request, response) -> {
            // Remove trailing slashes of requests
            String path = request.pathInfo();
            if (path.endsWith("/")) {
                response.redirect(path.substring(0, path.length() - 1));
            }
        });

        path("/api", new Api(database, securityConfig));

        after((req, res) -> {
            res.header("Content-Encoding", "gzip");
            res.type("application/json");
        });
    }

}
