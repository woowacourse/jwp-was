package http.request;

import java.util.Map;

public interface MessageBodyParser {
    Map<String, String> parse(String body);
}