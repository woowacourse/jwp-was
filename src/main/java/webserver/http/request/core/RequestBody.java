package webserver.http.request.core;

import webserver.http.exception.CanNotParseDataException;

public class RequestBody extends RequestData {
    private static final String BODY_REGEX = "&";
    private final String bodyData;

    public RequestBody(String bodyData) {
        super();
        this.bodyData = bodyData;
        parse();
    }

    private void parse() {
        String[] params = bodyData.split(BODY_REGEX);
        if (params.length == 0) {
            throw new CanNotParseDataException();
        }
        extractParameter(params);
    }
}
