package webserver.response;

import java.util.Objects;

public class ResponseBody {
    private byte[] body;

    public boolean isEmpty() {
        return Objects.isNull(body);
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
