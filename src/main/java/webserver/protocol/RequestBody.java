package webserver.protocol;

import java.util.Collections;
import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestBody {

    private final Map<String, String> contents;

    public Map<String, String> getContents() {
        return Collections.unmodifiableMap(contents);
    }
}
