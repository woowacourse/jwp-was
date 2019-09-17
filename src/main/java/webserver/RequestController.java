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
            if ("/index.html".equals(url)) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "text/html;charset=utf-8");
                headers.put("Content-Length", String.valueOf(body.length));
                return new Response(200, "OK", headers, body);
            }
        } catch (Exception e) {
            logger.error("Error is occurred while processing request", e);
        }
        return null;
    }
}
