package webserver.http.response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.exception.FailResponseException;
import webserver.exception.WrongUriException;
import webserver.http.Header;
import webserver.utils.FileExtension;
import webserver.utils.FileIoUtils;
import webserver.utils.ResourcePathUtils;
import webserver.view.ModelAndView;
import webserver.view.template.HandlebarsHtmlTemplate;
import webserver.view.template.HtmlTemplateEngine;

import static webserver.http.Header.JSESSION_ID;
import static webserver.http.HttpVersion.HTTP11;
import static webserver.http.response.HttpResponse.HEADER_RESPONSE_LOCATION;
import static webserver.http.response.ResponseStatus.FOUND;
import static webserver.http.response.ResponseStatus.OK;

public class HttpResponseGenerator {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final HtmlTemplateEngine HTML_TEMPLATE_ENGINE = new HandlebarsHtmlTemplate();

    public static final String HTTP_VERSION = "HttpVersion";
    public static final String STATUS_CODE = "StatusCode";
    public static final String REASON_PHRASE = "ReasonPhrase";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";

    private static String getCookieValue(String sessionId) {
        return String.format("logined=true; %s=%s; %s=/\r\n", JSESSION_ID, "Path", sessionId);
    }

    private static Map<String, String> addHeaderElementWithLoginFlag(int bodyLength, String mimeType, String sessionId) {
        Map<String, String> headerElement = addHeaderElement(bodyLength, mimeType);
        headerElement.put("Set-Cookie", getCookieValue(sessionId));
        return headerElement;
    }

    private static Map<String, String> addHeaderElement(int bodyLength, String mimeType) {
        Map<String, String> headerElement = new LinkedHashMap<>();
        headerElement.put(HEADER_CONTENT_TYPE, mimeType + ";charset=utf-8\r\n");
        headerElement.put(HEADER_CONTENT_LENGTH, bodyLength + "\r\n");

        return headerElement;
    }

    private static Map<String, String> getStatusLines(ResponseStatus ok) {
        Map<String, String> statusLines = new LinkedHashMap<>();
        statusLines.put(HTTP_VERSION, HTTP11.getVersion());
        statusLines.put(STATUS_CODE, ok.getCode());
        statusLines.put(REASON_PHRASE, ok.getPhrase());
        return statusLines;
    }

    public static HttpResponse responseLoginSuccess(ModelAndView modelAndView, String sessionId) {
        try {
            String path = ResourcePathUtils.getResourcePath(modelAndView.getViewName());
            byte[] responseBody = renderResponseBody(modelAndView);
            String mimeType = Files.probeContentType(Paths.get(path));
            StatusLine statusLine = new StatusLine(getStatusLines(OK));

            Header header = new Header(addHeaderElementWithLoginFlag(responseBody.length, mimeType, sessionId));
            return new HttpResponse(statusLine, header, responseBody);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        throw new FailResponseException();
    }

    public static HttpResponse response200Header(ModelAndView modelAndView) {
        try {
            byte[] responseBody = renderResponseBody(modelAndView);
            String mimeType = Files.probeContentType(Paths.get(modelAndView.getViewName()));
            StatusLine statusLine = new StatusLine(getStatusLines(OK));
            Header header = new Header(addHeaderElement(responseBody.length, mimeType));
            return new HttpResponse(statusLine, header, responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new FailResponseException();
    }

    private static byte[] renderResponseBody(ModelAndView modelAndView) throws IOException {
        try {
            if (isHtmlFile(modelAndView)) {
                return HTML_TEMPLATE_ENGINE.render(modelAndView);
            }

            String path = ResourcePathUtils.getResourcePath(modelAndView.getViewName());
            return FileIoUtils.loadFileFromClasspath(path);
        } catch (URISyntaxException e) {
            throw new WrongUriException();
        }
    }

    private static boolean isHtmlFile(ModelAndView modelAndView) {
        return modelAndView.isSameViewExtension(FileExtension.HTML.getFileExtension());
    }

    public static HttpResponse response302Header(ModelAndView modelAndView) {
        StatusLine statusLine = new StatusLine(getStatusLines(FOUND));

        Map<String, String> headerElement = new LinkedHashMap<>();
        headerElement.put(HEADER_RESPONSE_LOCATION, modelAndView.getViewName());

        Header header = new Header(headerElement);

        return new HttpResponse(statusLine, header);
    }
}
