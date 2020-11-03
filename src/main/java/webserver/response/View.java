package webserver.response;

import static webserver.response.RedirectView.*;

import java.io.IOException;

import exception.ViewNotFoundException;

public interface View {

    static View of(String viewName) {
        try {
            if (viewName.startsWith(REDIRECT_PREFIX)) {
                return new RedirectView(viewName);
            }
            return new DefaultView(viewName);
        } catch (IOException exception) {
            throw new ViewNotFoundException(viewName);
        }
    }

    byte[] getView();

    int getLength();

    boolean isNotEmpty();

    boolean isRedirect();

    String getViewName();
}
