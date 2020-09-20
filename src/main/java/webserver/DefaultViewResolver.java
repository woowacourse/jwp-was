package webserver;

import java.io.IOException;

import utils.FileIoUtils;

public class DefaultViewResolver implements ViewResolver{

    public View resolve(final ServletRequest servletRequest) throws IOException {
        final String path = servletRequest.getPath();

        if (path.contains(".")) {
            return new View(FileIoUtils.loadFileFromClasspath(path));
        }
        return new View("".getBytes());
    }
}
