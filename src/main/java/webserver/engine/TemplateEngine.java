package webserver.engine;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class TemplateEngine {
    private TemplateLoader loader;
    private Handlebars handlebars;
    private Template template;

    public TemplateEngine() {
        this.loader = new ClassPathTemplateLoader();
    }

    public void compile(String location) throws IOException {
        handlebars = new Handlebars(loader);
        template = handlebars.compile(location);
    }

    public byte[] apply(Map<String, Object> model) throws IOException {
        return template.apply(model).getBytes();
    }

    public void setPrefix(String prefix) {
        loader.setPrefix(prefix);
    }

    public void setSuffix(String suffix) {
        loader.setSuffix(suffix);
    }
}
