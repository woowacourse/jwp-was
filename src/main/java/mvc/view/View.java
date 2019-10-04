package mvc.view;

import server.http.response.HttpResponse;

public interface View {
    HttpResponse createResponse();
}
