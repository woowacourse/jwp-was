package http.response;

import http.common.HttpHeader;
import http.common.HttpVersion;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class HttpResponseGenerator {
    public static void redirect(final DataOutputStream dos, final String host, String redirectUrl) {
        HttpResponseConverter.response3xx(dos, generateHttpResponse(
                new StartLine(HttpVersion.HTTP_1_1, HttpStatus.FOUND),
                HttpHeader.redirect(host, redirectUrl),
                HttpResponseBody.empty()
        ));
    }

    public static void forward(final DataOutputStream dos, final String url) throws IOException, URISyntaxException {
        HttpResponseConverter.response2xx(dos, generateHttpResponse(
                new StartLine(HttpVersion.HTTP_1_1, HttpStatus.OK),
                HttpHeader.of(Arrays.asList("Content-Type: " + HttpContentType.of(url).getContentType())),
                HttpResponseBody.of(
                        FileIoUtils.loadFileFromClasspath(url)
                )
        ));
    }

    private static HttpResponse generateHttpResponse(final StartLine startLine,
                                                     final HttpHeader httpHeader,
                                                     final HttpResponseBody httpResponseBody) {
        return new HttpResponse(startLine, httpHeader, httpResponseBody);
    }
}
