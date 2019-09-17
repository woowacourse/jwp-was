package http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    final HttpMethod method;
    final HttpHost host;
    final HttpSubPath path;
    final HttpVersion version;
    final Map<String, String> others = new HashMap<>();
}