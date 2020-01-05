package me.mtte.code.ideahub;

import me.mtte.code.ideahub.auth.AuthFilter;
import me.mtte.code.ideahub.auth.Role;
import me.mtte.code.ideahub.controller.LoginController;
import me.mtte.code.ideahub.controller.NoteController;
import me.mtte.code.ideahub.controller.UserController;
import me.mtte.code.ideahub.database.Database;
import me.mtte.code.ideahub.service.NoteService;
import me.mtte.code.ideahub.service.UserService;
import org.pac4j.core.config.Config;
import spark.RouteGroup;

import static me.mtte.code.ideahub.util.JsonUtil.json;
import static spark.Spark.*;

/**
 * Defines all routes for the API.
 */
public class Api implements RouteGroup {

    private final AuthFilter loggedInUser;
    private final AuthFilter adminFilter;

    private final LoginController loginController;
    private final UserController userController;
    private final NoteController noteController;

    public Api(Database database, Config securityConfig) {
        this.loggedInUser = new AuthFilter(securityConfig);
        this.adminFilter = new AuthFilter(securityConfig, Role.ADMIN);

        UserService userService = new UserService(database);
        NoteService noteService = new NoteService(database);
        this.loginController = new LoginController(userService);
        this.userController = new UserController(userService);
        this.noteController = new NoteController(noteService);
    }

    @Override
    public void addRoutes() {
        post("/login", this.loginController::handleLogin, json());

        before("/users", this.adminFilter);
        before("/users/*", this.adminFilter);
        path("/users", this.userController);

        before("/notes", this.loggedInUser);
        before("/notes/*", this.loggedInUser);
        path("/notes", this.noteController);
    }

}
