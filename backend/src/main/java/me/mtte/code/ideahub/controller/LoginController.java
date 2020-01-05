package me.mtte.code.ideahub.controller;

import me.mtte.code.ideahub.auth.Password;
import me.mtte.code.ideahub.model.User;
import me.mtte.code.ideahub.responses.LoginResponse;
import me.mtte.code.ideahub.responses.ResponseFactory;
import me.mtte.code.ideahub.service.UserService;
import me.mtte.code.ideahub.validation.Validation;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import spark.Request;
import spark.Response;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static me.mtte.code.ideahub.util.ParameterUtil.getParameter;
import static me.mtte.code.ideahub.validation.StandardValidators.*;
import static spark.Spark.halt;

public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    public Object handleLogin(Request request, Response response) {
        String username = getParameter(request, "username");
        String password = getParameter(request, "password");

        // Validation
        var usernameValidation = new Validation<>(username, nonNull()
                .and(notEmpty()));
        if (usernameValidation.failed()) {
            return ResponseFactory.createInvalidParameterError(response, "username", username,
                    usernameValidation.getError().getMessage());
        }
        var passwordValidation = new Validation<>(password, nonNull()
                .and(notEmpty()));
        if (passwordValidation.failed()) {
            return ResponseFactory.createInvalidParameterError(response, "password", password,
                    passwordValidation.getError().getMessage());
        }

        // Authenticate
        Optional<User> user = authenticate(username, password);
        if (user.isEmpty()) {
            halt(401, "Login credentials invalid");
        }

        // Create profile and token and respond
        CommonProfile profile = createProfile(user.get());
        String token = generateJwtToken(profile);
        return new LoginResponse(token, user.get().getUsername(), user.get().getRole());
    }

    private Optional<User> authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return Optional.empty();
        }

        Optional<User> user = this.userService.getByUsernameWithPassword(username);

        return user.filter(u -> Password.verifyPassword(password.toCharArray(), u.getPassword().toCharArray()));
    }

    private CommonProfile createProfile(User user) {
        CommonProfile profile = new CommonProfile();

        profile.setId(String.valueOf(user.getId()));
        profile.setClientName(user.getUsername());
        profile.setRemembered(true);
        profile.setRoles(Set.of(user.getRole().toString()));

        return profile;
    }

    private String generateJwtToken(CommonProfile profile) {
        JwtGenerator<CommonProfile> generator = new JwtGenerator<>(new SecretSignatureConfiguration(System.getenv("JWT_SALT")));
        generator.setExpirationTime(Date.from(Instant.now().plus(30, ChronoUnit.HOURS)));
        return generator.generate(profile);
    }

}
