package utils.modelandview.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import utils.modelandview.model.Model;

import java.io.IOException;
import java.nio.charset.Charset;

public class HandleBarView implements View {
    private TemplateLoader loader;

    public HandleBarView(String preFix, String sufFix, Charset charset) {
        loader = new ClassPathTemplateLoader();

        loader.setPrefix(preFix);
        loader.setSuffix(sufFix);
        loader.setCharset(charset);
    }

    @Override
    public byte[] render(String location, Model model) throws IOException {
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(location);

        return template.apply(model.getData()).getBytes();
    }
}
