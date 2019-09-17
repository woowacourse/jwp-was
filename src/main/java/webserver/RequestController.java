package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    public static Response handle(Request request) {
        try {
            logger.info(request.getUrl());

            String url = request.getUrl();
            Response staticResponse = serveStaticFile(url);
            if (staticResponse != null) {
                return staticResponse;
            }
            
            if ("/index.html".equals(url)) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Length", String.valueOf(body.length));
                return new Response(200, "OK", MediaType.HTML, headers, body);
            }
        } catch (Exception e) {
            logger.error("Error is occurred while processing request", e);
        }
        return new Response(404, "NOT FOUND", null, null, null);
    }

    private static Response serveStaticFile(String url) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./static" + url);
            Map<String, String> headers = new HashMap<>();

            MediaType contentType = extractExtension(url);
            headers.put("Content-Length", String.valueOf(body.length));
            return new Response(200, "OK", contentType, headers, body);
        } catch (Exception e) {
            return null;
        }
    }

    private static MediaType extractExtension(String url) {
        String[] tokens = url.split("\\.");
        return MediaType.fromExtension(tokens[tokens.length - 1])
            .orElseThrow(() -> new IllegalArgumentException("지원되지 않는 확장자 입니다."));
    }
}
