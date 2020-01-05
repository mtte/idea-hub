package me.mtte.code.ideahub.controller;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import me.mtte.code.ideahub.responses.ErrorResponse;
import me.mtte.code.ideahub.responses.SuccessResponse;
import me.mtte.code.ideahub.service.UserService;
import me.mtte.code.ideahub.util.SparkUtil;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

import static spark.Spark.*;


public class TwoFactorAuthController implements RouteGroup {

    private final UserService userService;

    public TwoFactorAuthController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addRoutes() {
        // Enable
        post("",  this::enableTwoFactorAuthentication);

        // Disable
        delete("", this::disableTwoFactorAuthentication);

        // Check
        get("", this::checkIfTwoFactorAuthenticationIsEnabled);
    }

    private Object enableTwoFactorAuthentication(Request request, Response response) {
        var user = SparkUtil.getUser(request, response, this.userService);
        if (user.isPresent()) {
            if (this.userService.isTwoFactorAuthEnabled(user.get().getId())) {
                response.status(400);
                return new ErrorResponse("Two factor authentication is already enabled");
            }
            var secret = TimeBasedOneTimePasswordUtil.generateBase32Secret();
            if (this.userService.enableTwoFactorAuth(user.get().getId(), secret)) {
                return TimeBasedOneTimePasswordUtil.qrImageUrl(user.get().getUsername(), secret);
            }
        }
        response.status(500);
        return new ErrorResponse("Error while enabling two factor authentication");
    }

    private Object disableTwoFactorAuthentication(Request request, Response response) {
        var user = SparkUtil.getUser(request, response, this.userService);
        if (user.isPresent()) {
            if (!this.userService.isTwoFactorAuthEnabled(user.get().getId())) {
                response.status(400);
                return new ErrorResponse("Two factor authentication is already disabled");
            }
            if (this.userService.disableTwoFactroAuth(user.get().getId())) {
                return new SuccessResponse();
            }
        }
        response.status(500);
        return new ErrorResponse("Error while enabling two factor authentication");
    }

    private Object checkIfTwoFactorAuthenticationIsEnabled(Request request, Response response) {
        var userId = SparkUtil.getUserId(request, response);
        if (userId == null) {
            response.status(500);
            return new ErrorResponse("Error while enabling two factor authentication");
        }
        return this.userService.isTwoFactorAuthEnabled(userId);
    }

}
