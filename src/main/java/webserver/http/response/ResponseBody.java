package webserver.http.response;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;

public class ResponseBody {
    private byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public ResponseBody(String body) {
        this(body.getBytes());
    }

    public static ResponseBody ofFile(String name) throws IOException {
        try {
            return new ResponseBody(FileIoUtils.loadFileFromClasspath(name));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("형식에 맞지 않습니다.");
        }
    }

    public byte[] getBody() {
        return body;
    }

    public int getBodyLength() {
        return body.length;
    }
}
