package webserver.response;

import java.util.Arrays;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseBody that = (ResponseBody) o;
        return Arrays.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(body);
    }
}
