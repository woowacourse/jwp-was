package view;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.StaticFile;
import http.exceptions.FailToRenderView;
import http.response.HttpResponse;

public class HandleBarViewResolver implements ViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(HandleBarViewResolver.class);
    private static final String lineSeparator = System.lineSeparator();
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_DELIMITER = ": ";
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    private Handlebars handlebars;

    public HandleBarViewResolver() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);
        handlebars = new Handlebars(loader);
    }

    @Override
    public void render(HttpResponse httpResponse, DataOutputStream dataOutputStream) {
        try {
            write(dataOutputStream, apply(httpResponse));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new FailToRenderView(e.getMessage());
        }
    }

    private String apply(HttpResponse httpResponse) throws IOException {
        String resource = httpResponse.getResource();
        Template template = handlebars.compile(resource);
        return template.apply(httpResponse.getModelAndView().getAttributes());
    }

    private void write(DataOutputStream dataOutputStream, String body) throws IOException {
        StaticFile staticFile = StaticFile.findStaticFile(SUFFIX);

        dataOutputStream.writeBytes(CONTENT_TYPE + HEADER_DELIMITER + staticFile.getContentType() + lineSeparator);
        dataOutputStream.writeBytes(CONTENT_LENGTH + HEADER_DELIMITER + body.length() + lineSeparator);
        // HEADER 와 BODY 사이릃 한 줄 띄워야 한다.
        dataOutputStream.writeBytes(lineSeparator);
        dataOutputStream.write(body.getBytes(), 0, body.length());
        dataOutputStream.flush();
    }
}
