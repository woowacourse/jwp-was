package webserver.resolver;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileResolver implements Resolver {
    @Override
    public byte[] resolve(String path) throws IOException, URISyntaxException {
        if (path.contains(".html")) {
            return FileIoUtils.loadHtmlFile(path);
        }
        return FileIoUtils.loadStaticFile(path);
    }
}
