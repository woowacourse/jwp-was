package webserver.resolver;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlViewResolver implements Resolver {
    final String PREFIX = "/";
    final String POSTFIX = ".html";

    @Override
    public byte[] resolve(String path) throws IOException, URISyntaxException {
        if (path == null) {
            return new byte[0];
        }
        return FileIoUtils.loadHtmlFile(PREFIX + path + POSTFIX);
    }
}
