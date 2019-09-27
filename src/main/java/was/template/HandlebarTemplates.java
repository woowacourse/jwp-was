package was.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.http.request.exception.IntervalServerException;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class HandlebarTemplates {
    private TemplateLoader templateLoader;
    private Template template;
    private Handlebars handlebars;
    private Map<String, Object> handle = new LinkedHashMap<>();

    public HandlebarTemplates(final String prefix, final String location, final String suffix) {
        templateLoader = new ClassPathTemplateLoader();
        handlebars = new Handlebars(templateLoader);
        templateLoader.setPrefix(prefix);
        templateLoader.setSuffix(suffix);
        compileLocation(location);
    }

    private void compileLocation(final String location) {
        try {
            template = handlebars.compile(location);
        } catch (IOException e) {
            throw new IntervalServerException();
        }
    }

    /**
     * Collection 을 사용하여 Handlebar 를 사용할 경우
     */
    public void put(final String key, final Collection collection) {
        handle.put(key, collection);
    }

    public String apply() {
        try {
            return template.apply(handle);
        } catch (IOException e) {
            throw new IntervalServerException();
        }
    }

    /**
     * Collection 을 제외한 Object 가 들어올 시 사용
     */
    public String apply(final Object object) {
        try {
            exceptCollection(object);
            return template.apply(object);
        } catch (IOException e) {
            throw new IntervalServerException();
        }
    }

    private void exceptCollection(final Object object) {
        if (object instanceof Collection) {
            throw new IntervalServerException();
        }
    }
}
