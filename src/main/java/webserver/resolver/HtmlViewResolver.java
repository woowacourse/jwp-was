package webserver.resolver;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlViewResolver implements ViewResolver {
    @Override
    public byte[] resolve(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path + ".html");
    }

}
