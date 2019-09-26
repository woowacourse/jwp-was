package webserver.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.util.Map;

public class HandleBarsView extends AbstractView {
    public HandleBarsView(final Handlebars handlebars, final String path, final Map<String, Object> attributes) throws IOException {
        final Template template = handlebars.compile(path);
        this.responseBody = template.apply(attributes).getBytes();
    }
}
