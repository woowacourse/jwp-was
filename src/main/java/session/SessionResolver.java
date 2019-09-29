package session;

import http.response.HttpResponse;

public interface SessionResolver {
    void resolve(HttpResponse response);
}
