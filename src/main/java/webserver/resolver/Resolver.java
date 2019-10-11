package webserver.resolver;

import webserver.view.View;

import java.io.IOException;

public interface Resolver {
    View createView(String viewName) throws IOException;
}