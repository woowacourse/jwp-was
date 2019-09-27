package mvc.view;

import server.http.response.HttpResponse;

public class RedirectView implements View {
    private final String redirectUrl;

    public RedirectView(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public HttpResponse createResponse() {
        HttpResponse response = new HttpResponse();
        response.sendRedirect(redirectUrl);
        return response;
    }
}
