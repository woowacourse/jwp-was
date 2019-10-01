package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.ContentType;
import http.request.HttpRequest;
import http.response.DataOutputStreamWrapper;
import http.response.HttpResponse;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.router.RouterFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.of(in);
            DataOutputStreamWrapper dos = new DataOutputStreamWrapper(new DataOutputStream(out));
            HttpResponse httpResponse = HttpResponse.of(dos);

            try {
                Controller controller = route(httpRequest);
                controller.service(httpRequest, httpResponse);
                // 여기서 response 를 보내는 역할을 할까??
                // 그렇다면... controller 에서 response 관련 해주어야 할 로직들이 이 곳에 모일 수 있을 것 같은데..
                // (예를 들어 압축, 캐시, 템플릿 적용 등등)
            } catch (RuntimeException e) {
                // show error page
                logger.error("runtime error: ", e);
                responseErrorPage(httpResponse, e);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error("not handled error: ", e);
        }
    }

    private Controller route(HttpRequest httpRequest) {
        String path = httpRequest.getPath();

        return RouterFactory.getRouter().retrieveController(path);
    }

    private void responseErrorPage(HttpResponse response, RuntimeException re) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile("error/error_500");
            byte[] b = template.apply(new HashMap<String, String>() {{
                put("error", re.getMessage());
            }}).getBytes("UTF-8");

            Tika tika = new Tika();
            String mimeType = tika.detect(new ByteArrayInputStream(b));
            ContentType contentType = ContentType.fromMimeType(mimeType).get();

            response.setHeader("Content-Type", contentType.toHeaderValue());
            response.setHeader("Content-Length", Integer.toString(b.length));
            response.response200Header();
            response.responseBody(b);
        } catch (IOException e) {
            logger.error("error: ", e);
        }
    }
}
