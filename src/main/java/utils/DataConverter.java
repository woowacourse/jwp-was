package utils;

import webserver.domain.response.Response;
import webserver.domain.response.StaticFile;

public class DataConverter {
    public static byte[] convertToBytes(final Response response) {
        return response.toBytes();
    }

    public static byte[] convertToBytes(final byte[] bytes) {
        return new Response.Builder().body(bytes).build().toBytes();
    }

    public static Response convertToResponse(final StaticFile file) {
        return new Response.Builder().body(file).build();
    }
}
