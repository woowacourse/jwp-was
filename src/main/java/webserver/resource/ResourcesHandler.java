package webserver.resource;

import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class ResourcesHandler {

    public Resource convertUriToResource(String uri) throws IOException, URISyntaxException {
        String filePath = "";

        if ("/".equals(uri)) {
            uri = "/index.html";
        }
        if (uri.endsWith(".html")) {
            filePath = "./templates" + uri;
            return new Resource(FileIoUtils.loadFileFromClasspath(filePath), ContentType.HTML);
        }
        if (uri.endsWith(".css")) {
            filePath = "./static" + uri;
            return new Resource(FileIoUtils.loadFileFromClasspath(filePath), ContentType.CSS);
        }
        if (uri.endsWith(".js")) {
            filePath = "./static" + uri;
            return new Resource(FileIoUtils.loadFileFromClasspath(filePath), ContentType.JS);
        }
        if (uri.endsWith(".ico")) {
            filePath = "./templates" + uri;
            return new Resource(FileIoUtils.loadFileFromClasspath(filePath), ContentType.ICON);
        }
        if (uri.endsWith(".ttf")) {
            filePath = "./static" + uri;
            return new Resource(FileIoUtils.loadFileFromClasspath(filePath), ContentType.FONT_TTF);
        }
        if (uri.endsWith(".woff")) {
            filePath = "./static" + uri;
            return new Resource(FileIoUtils.loadFileFromClasspath(filePath), ContentType.FONT_WOFF);
        }
        throw new IllegalArgumentException("cannot convert given uri to resource.");
    }
}
