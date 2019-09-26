package view;

import view.template.HtmlTemplate;
import view.template.HtmlTemplateResolver;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private final Map<String, Object> objects = new HashMap<>();
    private final String fileUri;

    public ModelAndView(final String fileUri) {
        this.fileUri = fileUri;
    }

    public void addObject(String key, Object object) {
        objects.put(key, object);
    }

    public byte[] buildView() {
        HtmlTemplate htmlTemplate = HtmlTemplateResolver.findTemplate(fileUri);
        return htmlTemplate.render(fileUri, objects);
    }
}
