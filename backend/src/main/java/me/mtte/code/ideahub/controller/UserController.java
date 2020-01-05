package me.mtte.code.ideahub.controller;

import me.mtte.code.ideahub.auth.Password;
import me.mtte.code.ideahub.auth.Role;
import me.mtte.code.ideahub.responses.ErrorResponse;
import me.mtte.code.ideahub.responses.ResponseFactory;
import me.mtte.code.ideahub.responses.SuccessResponse;
import me.mtte.code.ideahub.service.UserService;
import me.mtte.code.ideahub.util.SparkUtil;
import me.mtte.code.ideahub.validation.BooleanValidator;
import me.mtte.code.ideahub.validation.PasswordDiversityValidator;
import me.mtte.code.ideahub.validation.Validation;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

import static me.mtte.code.ideahub.util.JsonUtil.json;
import static me.mtte.code.ideahub.util.SparkUtil.getParameter;
import static me.mtte.code.ideahub.util.SparkUtil.getRequestId;
import static me.mtte.code.ideahub.validation.StandardValidators.*;
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

        var usernameValidation = usernameValidation(username);
        if (usernameValidation.failed()) {
            return ResponseFactory.createInvalidParameterError(response, "username", username, usernameValidation);
        }

        var passwordValidation = new Validation<>(password, nonNull()
                .and(minLength(12))
                        .and(maxLength(50))
                                .and(new PasswordDiversityValidator()));
        if (passwordValidation.failed()) {
            return ResponseFactory.createInvalidParameterError(response, "password", "*****", passwordValidation);
        }

        var roleValidation = roleValidation(role);
        if (roleValidation.failed()) {
            return ResponseFactory.createInvalidParameterError(response, "role", role, roleValidation);
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

        if (role != null) {
            var roleValidation = roleValidation(role);
            if (roleValidation.failed()) {
                return ResponseFactory.createInvalidParameterError(response, "role", role, roleValidation);
            }
        }

        var currentUser = this.userService.getUser(getRequestId(request));
        if (username != null && !(currentUser.isPresent() && username.equals(currentUser.get().getUsername()))) {
            var usernameValidation = usernameValidation(username);
            if (usernameValidation.failed()) {
                return ResponseFactory.createInvalidParameterError(response, "username", username, usernameValidation);
            }
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


    private Validation<String> usernameValidation(String username) {
        var isUsernameUnique = new BooleanValidator<>(this.userService::isUsernameUnique, "Username already exists");
        var validator = nonNull().and(notEmpty()).and(maxLength(50)).and(isUsernameUnique);
        return new Validation<>(username, validator);
    }


    private Validation<String> roleValidation(String role) {
        var roleExists = new BooleanValidator<>(Role::validRole, "Role does not exist");
        var validator = nonNull().and(notEmpty()).and(roleExists);
        return new Validation<>(role, validator);
    }

}
