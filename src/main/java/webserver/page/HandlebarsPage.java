package webserver.page;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.ContentType;

import java.io.IOException;

public class HandlebarsPage implements Page {
    private final static Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        handlebars = new Handlebars(loader);
    }

    private final ContentType contentType;
    private final byte[] body;

    private HandlebarsPage(ContentType contentType, byte[] body) {
        this.contentType = contentType;
        this.body = body;
    }

    public static HandlebarsPage locationWithObj(String location, Object obj) {
        byte[] b = adjustTemplate(location, obj);

        return new HandlebarsPage(ContentType.HTML, b);
    }

    private static byte[] adjustTemplate(String location, Object obj) {
        try {
            Template template = handlebars.compile(location);
            return template.apply(obj).getBytes("UTF-8");
        } catch (IOException e) {
            throw TemplateEngineFailException.from(location, obj);
        }
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }

    @Override
    public byte[] getBody() {
        return body;
    }
}
