package webserver.protocol;

import java.util.Map;

public class RequestBody {

    private final Map<String, String> contents;

    public RequestBody(final Map<String, String> contents) {
        this.contents = contents;
    }

    public Map<String, String> getContents() {
        return contents;
    }
}
