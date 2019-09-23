package http.response.view;

import http.HTTP;
import http.response.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedirectView extends View {
    private static final Logger log = LoggerFactory.getLogger(RedirectView.class);

    public RedirectView(String path) {
        super(ResponseStatus.FOUND);
        header.put(HTTP.LOCATION, path);
    }
}
