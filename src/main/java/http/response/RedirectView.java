package http.response;

import http.HTTP;
import http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedirectView extends View {
    private static final Logger log = LoggerFactory.getLogger(RedirectView.class);

    public RedirectView(String path) {
        header.put(HTTP.LOCATION, path);
    }

    @Override
    public String getHeader() {
        return super.getHeader(HttpStatus.FOUND);
    }
}
