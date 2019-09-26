package view.template;

import exception.HtmlTemplateNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class HtmlTemplateResolver {

    private static final Map<String, HtmlTemplate> templates = new HashMap<>();

    static {
        HandlebarsTemplate handlebars = new HandlebarsTemplate();

        templates.put("/user/list.html", handlebars);
    }

    private HtmlTemplateResolver() {
    }

    public static HtmlTemplate findTemplate(String fileUri) {
        HtmlTemplate htmlTemplate = templates.get(fileUri);
        if (htmlTemplate == null) {
            throw new HtmlTemplateNotFoundException();
        }
        return htmlTemplate;
    }
}
