package utils;

import webserver.StaticFile;
import webserver.message.HttpStatus;
import webserver.message.response.Response;
import webserver.message.response.ResponseBuilder;

public class DataConverter {
    public static byte[] convertToBytes(final Response response) {
        return response.toBytes();
    }

    public static byte[] convertToBytes(final byte[] bytes) {
        return new ResponseBuilder().body(bytes).build().toBytes();
    }

    public static Response convertTo200Response(final byte[] bytes) {
        return new ResponseBuilder().body(bytes).build();
    }

    public static Response convertTo200Response(final StaticFile file) {
        return new ResponseBuilder().body(file).build();
    }

    public static Response convertTo404Response(final StaticFile file) {
        return new ResponseBuilder().body(file).httpStatus(HttpStatus.NOT_FOUND).build();
    }

    public static Response convertTo500Response(final StaticFile file) {
        return new ResponseBuilder().body(file).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
