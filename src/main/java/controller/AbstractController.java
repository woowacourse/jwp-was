package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.exception.HttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.common.HttpStatus;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public abstract class AbstractController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    private static final String CONTENT_TYPE_HTML = "text/html";
    private static final String CONTENT_TYPE_CSS = "text/css";
    private static final String TEMPLATES_PATH = "./templates";
    private static final String HEADER_FIELD_ACCEPT = "Accept";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String HTML_SUFFIX = ".html";
    private static final String CHARSET_UTF8 = "charset=utf-8";
    private static final String SEMICOLON = ";";
    private static final String PATH_PREFIX = "/templates";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException {
        if (httpRequest.isGet()) {
            resolveGet(httpRequest, httpResponse);
        }

        if (httpRequest.isPost()) {
            resolvePost(httpRequest, httpResponse);
        }
    }

    private void resolveGet(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException {
        ModelAndView modelAndView = doGet(httpRequest, httpResponse);
        String filePath = modelAndView.getView();

        if (Objects.nonNull(filePath) && filePath.startsWith(REDIRECT_PREFIX)) {
            httpResponse.sendRedirect(httpRequest, filePath);
            return;
        }

        if (httpRequest.containHeaderField(HEADER_FIELD_ACCEPT, CONTENT_TYPE_CSS)) {
            httpResponse.forward(httpRequest, getStaticFile(httpRequest), CONTENT_TYPE_CSS);
            return;
        }

        if (httpRequest.containHeaderField(HEADER_FIELD_ACCEPT, CONTENT_TYPE_HTML)) {
            httpResponse.forward(httpRequest, getFile(modelAndView), CONTENT_TYPE_HTML + SEMICOLON + CHARSET_UTF8);
        }
    }

    private void resolvePost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = doPost(httpRequest, httpResponse);
        if (path.startsWith(REDIRECT_PREFIX)) {
            httpResponse.sendRedirect(httpRequest, path);
        }
    }

    protected byte[] getStaticFile(HttpRequest httpRequest) throws FileNotFoundException {
        String file = httpRequest.getSource();
        try {
            return FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + file);
        } catch (IOException | URISyntaxException e) {
            log.debug("fail to load file {}", e.getMessage());
        }
        throw new FileNotFoundException("fail to find file.");
    }

    private byte[] getFile(ModelAndView modelAndView) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PATH_PREFIX);
        loader.setSuffix(HTML_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);

        byte[] file = null;
        try {
            String view = modelAndView.getView();
            Template template = handlebars.compile(view);
            file = template.apply(modelAndView.getModel()).getBytes();
        } catch (IOException e) {
            log.debug("fail to compile {}", e.getMessage());
        }
        return file;
    }

    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new HttpRequestException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new HttpRequestException(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
