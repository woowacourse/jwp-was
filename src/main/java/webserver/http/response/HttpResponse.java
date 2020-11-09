package webserver.http.response;

import java.util.Map;

public class HttpResponse {
    private HttpStatusLine httpStatusLine;
    private Map<String, String> responseHeaders;
    private byte[] body;
}
