package webserver;

import java.io.IOException;

public interface ViewResolver {
    View resolve(final ServletRequest servletRequest) throws IOException;
}
