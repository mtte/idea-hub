package me.mtte.code.ideahub.util;

import me.mtte.code.ideahub.model.User;
import me.mtte.code.ideahub.service.UserService;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.sparkjava.SparkWebContext;
import spark.Request;
import spark.Response;

import java.util.Optional;

public class SparkUtil {

    public SparkUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Integer getUserId(Request request, Response response) {
        var profile = getProfile(request, response);
        return profile.map(commonProfile -> convertToInt(commonProfile.getId())).orElse(null);
    }

    public static Optional<User> getUser(Request request, Response response, UserService userService) {
        Optional<CommonProfile> profile = getProfile(request, response);
        if (profile.isEmpty()) {
            return Optional.empty();
        }
        CommonProfile theProfile = profile.get();
        var id = convertToInt(theProfile.getId());
        if (id == null) {
            return Optional.empty();
        }
        return userService.getUser(id);
    }

    private static Optional<CommonProfile> getProfile(Request request, Response response) {
        WebContext context = new SparkWebContext(request, response);
        ProfileManager<CommonProfile> manager = new ProfileManager<>(context);
        return manager.get(false);
    }

    public static String getParameter(Request request, String parameter) {
        var param = request.params(parameter);
        if (param != null && !param.isEmpty()) {
            return param;
        }

        var queryParam = request.queryParams(parameter);
        if (queryParam != null && !queryParam.isEmpty()) {
            return queryParam;
        }

        var attribute = request.attribute(parameter);
        if (attribute instanceof String && !((String) attribute).isEmpty()) {
            return (String) attribute;
        }

        return null;
    }

    public static Integer getParameterAsInt(Request request, String parameter) {
        var value = getParameter(request, parameter);
        return convertToInt(value);
    }

    public static Integer convertToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer getRequestId(Request request) {
       return getParameterAsInt(request, ":id");
    }

}
