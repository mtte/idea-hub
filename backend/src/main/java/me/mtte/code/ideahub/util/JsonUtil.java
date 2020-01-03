package me.mtte.code.ideahub.util;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Utility class for json handling.
 */
public class JsonUtil {

    public JsonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    /**
     * Returns a {@link ResponseTransformer} that converts the response object into a json string.
     * @return Converts the response to json.
     */
    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

}
