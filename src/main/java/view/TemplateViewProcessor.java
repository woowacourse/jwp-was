package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.response.HttpResponse;
import webserver.ServerErrorException;

import java.io.IOException;

import static view.RedirectViewProcessor.REDIRECT;

public class TemplateViewProcessor implements ViewProcessor {
    private static final String TEMPLATES = "/templates";
    private static final String HTML = ".html";

    @Override
    public byte[] render(ModelAndView modelAndView, HttpResponse response) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES);
        loader.setSuffix(HTML);
        Handlebars handlebars = new Handlebars(loader);

        Template template;
        String userListPage;
        try {
            template = handlebars.compile(modelAndView.getView());
            userListPage = template.apply(modelAndView.getModel());
        } catch (IOException e) {
            throw new ServerErrorException("렌더링에 실패했습니다.");
        }

        byte[] body = userListPage.getBytes();
        response.okResponse("html", body);

        return body;
    }

    @Override
    public boolean isSupported(String viewName) {
        return !viewName.startsWith(REDIRECT);
    }
}
