package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.exception.HttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.common.HttpStatus;
import webserver.common.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtils.class);
    private static final String PATH_PREFIX = "/templates";
    private static final String HTML_SUFFIX = ".html";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static byte[] compileTemplate(ModelAndView modelAndView) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PATH_PREFIX);
        loader.setSuffix(HTML_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);

        try {
            String view = modelAndView.getView();
            Template template = handlebars.compile(view);
            return template.apply(modelAndView.getModel()).getBytes();
        } catch (IOException e) {
            log.debug("fail to compile {}", e.getMessage());
            throw new HttpRequestException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
