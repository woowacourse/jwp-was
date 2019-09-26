package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HandleBarModelAndView implements ModelAndView {
    public static final String PRE_FIX = "/templates";
    public static final String SUF_FIX = ".html";

    TemplateLoader loader;
    Map<String, Object> data;

    public HandleBarModelAndView() {
        loader = loader(PRE_FIX, SUF_FIX);
        data = new HashMap<>();
    }

    private TemplateLoader loader(String preFix, String sufFix) {
        loader = new ClassPathTemplateLoader();

        loader.setPrefix(PRE_FIX);
        loader.setSuffix(SUF_FIX);
        loader.setCharset(StandardCharsets.UTF_8);

        return loader;
    }

    public void putData(String name, Object o) {
        data.put(name, o);
    }

    @Override
    public byte[] render(String location) throws IOException {
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(location);

        return template.apply(data).getBytes();
    }

    @Override
    public String getPreFix() {
        return PRE_FIX;
    }

    @Override
    public String getSufFix() {
        return SUF_FIX;
    }
}