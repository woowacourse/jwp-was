package view.statics;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.AbstractView;
import webserver.http.utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticView extends AbstractView {
    private static final Logger log = LoggerFactory.getLogger(StaticView.class);

    public StaticView(final String path) {
        setResponseBody(path);
    }

    protected void setResponseBody(final String path) {
        try {
            responseBody = FileIoUtils.loadFileFromClasspath(path);
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }
}
