package mvc.view;

import server.http.response.HttpResponse;

public class ForwardView implements View {
    private final String templatePath;

    public ForwardView(String relativeTemplatePath) {
        this.templatePath = "./templates/" + relativeTemplatePath;
    }

    @Override
    public HttpResponse createResponse() {
        HttpResponse response = new HttpResponse();
        response.forward(templatePath);
        return response;
    }
}
