package utils;

import webserver.message.HttpStatus;
import webserver.message.response.Response;
import webserver.StaticFile;

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
