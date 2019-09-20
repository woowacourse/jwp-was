package network;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class HttpResponseGenerator {
    public static void redirect(final DataOutputStream dos, final String host, String redirectUrl) {
        HttpResponseConverter.response3xx(dos, generateHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpStatus.FOUND,
                HttpHeader.redirect(host, redirectUrl),
                HttpResponseBody.empty()
        ));
    }

    public static void forward(final DataOutputStream dos, final String url) throws IOException, URISyntaxException {
        HttpResponseConverter.response2xx(dos, generateHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpStatus.OK,
                HttpHeader.of(Arrays.asList()),
                HttpResponseBody.of(
                        FileIoUtils.loadFileFromClasspath(url)
                )
        ));
    }

    private static HttpResponse generateHttpResponse(final HttpVersion httpVersion,
                                                     final HttpStatus httpStatus,
                                                     final HttpHeader httpHeader,
                                                     final HttpResponseBody httpResponseBody) {
        return new HttpResponse(httpVersion, httpStatus, httpHeader, httpResponseBody);
    }
}
