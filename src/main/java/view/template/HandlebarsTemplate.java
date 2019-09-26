package view.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static configure.PathConstants.TEMPLATE_HANDLEBARS_PREFIX;

public class HandlebarsTemplate implements HtmlTemplate {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsTemplate.class);

    @Override
    public byte[] render(String uri, final Map<String, Object> objects) {
        log.debug("handlebars rendering start");
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix(TEMPLATE_HANDLEBARS_PREFIX);
            loader.setSuffix("");
            loader.setCharset(StandardCharsets.UTF_8);
            Handlebars handlebars = new Handlebars(loader);
            handlebars.registerHelper("inc", (Helper<Integer>) (context, options) -> context + 1);

            Template template = handlebars.compile(uri);

            return template.apply(objects).getBytes();
        } catch (IOException e) {
            log.debug("error at getBodyWithHandlebars()");
            return new byte[0];
        }
    }
}
