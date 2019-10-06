package webserver.engine;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class HandleBar implements TemplateEngine {
    private TemplateLoader loader;
    private Handlebars handlebars;
    private Template template;

    public HandleBar() {
        this.loader = new ClassPathTemplateLoader();
    }

    @Override
    public void compile(String location) throws IOException {
        handlebars = new Handlebars(loader);
        template = handlebars.compile(location);
    }

    @Override
    public byte[] apply(Map<String, Object> model) throws IOException {
        return template.apply(model).getBytes();
    }

    @Override
    public void setPrefix(String prefix) {
        loader.setPrefix(prefix);
    }

    @Override
    public void setSuffix(String suffix) {
        loader.setSuffix(suffix);
    }
}
