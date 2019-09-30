package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;
import webserver.http.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateEngineProcessor implements ViewProcessor {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public void resolve(ModelAndView modelAndView) throws IOException, URISyntaxException {
        if (modelAndView.getView() == null) {
            modelAndView.setByteView(FileIoUtils.loadFileFromClasspath("./templates/error.html"));
        }
        modelAndView.setByteView(handleBarResolve(modelAndView).getBytes());
    }

    private String handleBarResolve(ModelAndView modelAndView) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(modelAndView.getView());
        return template.apply(modelAndView.getModel());
    }

    @Override
    public boolean isMapping(ModelAndView modelAndView) {
        return modelAndView.isTemplateEngineView();
    }
}
