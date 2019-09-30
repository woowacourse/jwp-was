package webserver.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserListView implements View {
    private static final String TEMPLATE_VALUE_MODEL = "users";
    private static final String TEMPLATE_VALUE_INDEX = "inc";
    private static final String TEMPLATE_PREFIX = "/templates";
    private static final String TEMPLATE_SUFFIX = ".html";
    private final String path;

    public UserListView(String path) {
        this.path = path;
    }


    @Override
    public void render(HttpRequest request, HttpResponse response) throws IOException {
        Map<String, Object> users = new HashMap<>();
        users.put(TEMPLATE_VALUE_MODEL, request.getAttribute(TEMPLATE_VALUE_MODEL));
        byte[] body = applyTemplate(users).getBytes();
        response.setContentLengthAndType(body.length, "text/html");
        response.setContentEncoding("utf-8");
        response.ok(body);
    }

    private String applyTemplate(Map<String, Object> value) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_PREFIX);
        loader.setSuffix(TEMPLATE_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper(TEMPLATE_VALUE_INDEX, (Helper<Integer>) (context, options) -> context + 1);
        Template template = handlebars.compile(path);
        return template.apply(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserListView that = (UserListView) o;

        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }
}
