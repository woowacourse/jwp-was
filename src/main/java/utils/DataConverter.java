package utils;

import webserver.file.File;
import webserver.message.HttpStatus;
import webserver.message.HttpVersion;
import webserver.message.response.Response;

import java.util.Objects;

public class DataConverter {
    public static byte[] convertToBytes(final Response response) {
        return response.toBytes();
    }

    public static byte[] convertToBytes(final byte[] bytes) {
        return new Response(HttpVersion.HTTP_1_1).toBytes();
    }

    public static Response convertTo500Response(final String httpVersion, final File file) {
        Response response = new Response(Objects.nonNull(httpVersion) ? HttpVersion.valueOf(httpVersion) : HttpVersion.HTTP_1_1);
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.body(file);

        return response;
    }
}
