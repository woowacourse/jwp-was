package webserver;

import java.util.HashMap;
import java.util.Map;

public class UrlMapper {
    private static Map<String, Controller> map = new HashMap<>();

    static {
//        map.put("/", MainController.of());
//        map.put("/", SignUpController.of());
    }

    public Object service(HttpRequest request, HttpResponse response, String url) {
        if (map.containsKey(url)) {
            return map.get(url).service(request, response);
        }
        throw new IllegalArgumentException("404 error");
    }
}
