package webserver.pageprovider;

import http.request.HttpRequestAccessor;
import http.response.HttpResponseAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.page.Page;
import webserver.page.RedirectPage;

public class LogoutPageProvider implements PageProvider {
    private static final Logger log = LoggerFactory.getLogger(LogoutPageProvider.class);

    @Override
    public Page provide(HttpRequestAccessor request, HttpResponseAccessor response) {
        log.debug("logout called");

        // to test session.invalidate();
        request.getSession(false).ifPresent(session -> session.invalidate());

        return RedirectPage.location("/index.html");
    }
}
