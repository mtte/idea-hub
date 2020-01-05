package me.mtte.code.ideahub.util;

import spark.Request;

public class ParameterUtil {

    public ParameterUtil() {
        throw new IllegalStateException("Utility class");
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
