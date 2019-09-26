package webserver.view;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticView extends AbstractView {
    private static final Logger log = LoggerFactory.getLogger(StaticView.class);

    public StaticView(final String path) {
        setPath(path);
        setResponseBody();
    }

    @Override
    protected void setPath(final String path) {
        this.path = path;
    }

    @Override
    protected void setResponseBody() {
        try {
            responseBody = FileIoUtils.loadFileFromClasspath(path);
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }
}
