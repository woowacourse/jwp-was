package http.response.view;

import http.response.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ModelAndView extends View {
    private static final Logger log = LoggerFactory.getLogger(RedirectView.class);

    public ModelAndView(String path, Map<String, Object> model, TemplateResolver templateResolver) throws IOException {
        super(ResponseStatus.OK);
        this.body = createBody(path, model, templateResolver);
    }

    public ModelAndView(String path, Map<String, Object> model) throws IOException {
        super(ResponseStatus.OK);
        this.body = createBody(path, model, new HandlebarResolver());
    }

    private byte[] createBody(String path, Map<String, Object> model, TemplateResolver templateResolver) throws IOException {
        return templateResolver.getBody(path, model).getBytes();
    }
}
