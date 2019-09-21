package http.response;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResponseBodyParser {

    public static ResponseBody parse(String filePath) throws IOException, URISyntaxException {
        return new ResponseBody(FileIoUtils.loadFileFromClasspath("./templates" + filePath));
    }
}
