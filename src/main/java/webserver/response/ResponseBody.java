package webserver.response;

import utils.FileIoUtils;
import webserver.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class ResponseBody {
    private byte[] body;

    private ResponseBody(byte[] body) {
        this.body = body;
    }

    public static ResponseBody of(HttpRequest httpRequest) {
        String file = httpRequest.getSource();
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath("./templates/" + file);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new ResponseBody(body);
    }

    public boolean addBody(byte[] body) {
        if (Objects.isNull(body)) {
            return false;
        }
        this.body = body;
        return true;
    }

    public int getLengthOfContent() {
        return body.length;
    }

    public byte[] getContent() {
        return body;
    }
}
