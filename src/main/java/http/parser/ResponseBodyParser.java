package http.parser;

import http.response.ResponseBody;
import utils.FileIoUtils;

public class ResponseBodyParser {

    public static ResponseBody parse(String filePath) {
        return new ResponseBody(FileIoUtils.loadFileFromClasspath(filePath));
    }
}
