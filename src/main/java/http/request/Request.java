package http.request;

import java.util.Map;

public interface Request {
    Map<String, String> getParams();

    RequestPath getRequestPath();
}
