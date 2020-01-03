package me.mtte.code.ideahub;

import me.mtte.code.ideahub.auth.Role;
import me.mtte.code.ideahub.controller.LoginController;
import me.mtte.code.ideahub.controller.UserController;
import me.mtte.code.ideahub.database.Database;
import me.mtte.code.ideahub.service.UserService;
import org.pac4j.core.config.Config;
import org.pac4j.sparkjava.SecurityFilter;
import spark.RouteGroup;

import static me.mtte.code.ideahub.util.JsonUtil.json;
import static spark.Spark.*;

/**
 * Defines all routes for the API.
 */
public class Api implements RouteGroup {

    private final SecurityFilter authorFilter;
    private final SecurityFilter adminFilter;

    private final LoginController loginController;
    private final UserController userController;

    public Api(Database database, Config securityConfig) {
        this.authorFilter = new SecurityFilter(securityConfig, "HeaderClient", Role.AUTHOR.toString());
        this.adminFilter = new SecurityFilter(securityConfig, "HeaderClient", Role.ADMIN.toString());

        UserService userService = new UserService(database);
        this.loginController = new LoginController(userService);
        this.userController = new UserController(userService);
    }

    @Override
    public void addRoutes() {
        post("/login", this.loginController::handleLogin, json());

        before("/users", this.adminFilter);
        before("/users/*", this.adminFilter);
        path("/users", this.userController);
    }

}
