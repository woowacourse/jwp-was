package http.response;

import http.HttpUri;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResponseBodyParser {

    public static ResponseBody parse(HttpUri httpUri) throws IOException, URISyntaxException {
        return new ResponseBody(FileIoUtils.loadFileFromClasspath(httpUri.getPath()));
    }
}
