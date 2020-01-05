package me.mtte.code.ideahub.controller;

import me.mtte.code.ideahub.auth.Password;
import me.mtte.code.ideahub.auth.Role;
import me.mtte.code.ideahub.responses.ErrorResponse;
import me.mtte.code.ideahub.responses.ResponseFactory;
import me.mtte.code.ideahub.responses.SuccessResponse;
import me.mtte.code.ideahub.service.UserService;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

import static me.mtte.code.ideahub.util.JsonUtil.json;
import static me.mtte.code.ideahub.util.ParameterUtil.getParameter;
import static me.mtte.code.ideahub.util.ParameterUtil.getRequestId;
import static spark.Spark.*;

public class UserController implements RouteGroup {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addRoutes() {
        // Get all users
        get("", this::getAllUsers, json());

        // Create user
        post("", this::createUser, json());

        // Get single user
        get("/:id", this::getUser, json());

        // Update user
        put("/:id", this::updateUser, json());

        // Delete user
        delete("/:id", this::deleteUser, json());

    }

    private Object createUser(Request request, Response response) {
        String username = getParameter(request, "username");
        String password = getParameter(request, "password");
        String role = getParameter(request, "role");

        if (!Role.validRole(role)) {
            return ResponseFactory.createInvalidParameterError(response, "role", role,
                    "Role does not exist");
        }

        if (!this.userService.isUsernameUnique(username)) {
            return ResponseFactory.createInvalidParameterError(response, "username", username,
                    "Username already exists");
        }

        if (password == null) {
            return ResponseFactory.createInvalidParameterError(response, "password", password);
        }
        String hash = Password.generateHash(password.toCharArray());

        return this.userService.createUser(username, hash, role);
    }

    private Object getAllUsers(Request request, Response response) {
        return this.userService.getAllUsers();
    }

    private Object getUser(Request request, Response response) {
        var id = getRequestId(request);

        if (id != null) {
            var user = this.userService.getUser(id);
            if (user.isPresent()) {
                return user.get();
            }
        }

        return ResponseFactory.createInvalidIdError(request, response);
    }

    private Object updateUser(Request request, Response response) {
        String username = getParameter(request, "username");
        String role = getParameter(request, "role");

        if (username == null || !this.userService.isUsernameUnique(username)) {
            return ResponseFactory.createInvalidParameterError(response, "username", username);
        }

        if (role == null || !Role.validRole(role)) {
            return ResponseFactory.createInvalidParameterError(response, "role", role);
        }

        var user = this.userService.updateUser(getRequestId(request), username, role);

        if (user == null) {
            return ResponseFactory.createInvalidIdError(request, response);
        }

        return user;
    }

    private Object deleteUser(Request request, Response response) {
        var id = getRequestId(request);

        if (id == null) {
            return ResponseFactory.createInvalidIdError(request, response);
        }

        if (!this.userService.deleteUser(id)) {
            response.status(500);
            return new ErrorResponse("Error while deleting user with id %d", id);
        }

        return new SuccessResponse();
    }

}
