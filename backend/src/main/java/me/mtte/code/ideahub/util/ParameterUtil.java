package me.mtte.code.ideahub.util;

import spark.Request;

public class ParameterUtil {

    public ParameterUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Integer convertToInt(String value) {
        try {
            int id = Integer.parseInt(value);
            return id;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer getQueryParamAsInt(Request request, String param) {
        String value = request.queryParams(param);
        return convertToInt(value);
    }

    public static Integer getParamAsInt(Request request, String param) {
        String value = request.params(param);
        return convertToInt(value);
    }

    public static Integer getRequestId(Request request) {
       return getParamAsInt(request, ":id");
    }

}
