package server.web.view;

import server.web.model.Model;
import server.web.request.HttpRequest;
import server.web.response.HttpResponse;

public abstract class AbstractView implements View {
    private static final String REDIRECT = "redirect:";
    private static final int REDIRECT_PATH = 1;

    protected final String path;

    public AbstractView(String path) {
        this.path = path;
    }

    @Override
    public void render(Model model, HttpRequest httpRequest, HttpResponse httpResponse) {
        if (path.startsWith(REDIRECT)) {
            httpResponse.sendRedirect(path.split(REDIRECT)[REDIRECT_PATH]);
            return;
        }

        processRender(model, httpRequest, httpResponse);
    }

    protected abstract void processRender(Model model, HttpRequest httpRequest, HttpResponse httpResponse);
}
