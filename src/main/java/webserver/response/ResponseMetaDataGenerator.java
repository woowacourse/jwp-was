package webserver.response;

import webserver.request.HttpRequest;

public class ResponseMetaDataGenerator {

    private ResponseMetaDataGenerator() {
    }

    public static ResponseMetaData buildDefaultOkMetaData(HttpRequest httpRequest) {
        return ResponseMetaData.Builder
                .builder(httpRequest, HttpStatus.OK)
                .contentType(httpRequest.findContentType())
                .build();
    }

    public static ResponseMetaData buildDefaultFoundMetaData(HttpRequest httpRequest, String location) {
        return ResponseMetaData.Builder
                .builder(httpRequest, HttpStatus.FOUND)
                .contentType(httpRequest.findContentType())
                .location(location)
                .build();
    }
}
