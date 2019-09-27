package mvc.controller;

import server.http.request.HttpRequest;
import mvc.view.View;
import was.http.servlet.Servlet;

public interface Controller extends Servlet {
    View get(HttpRequest httpRequest);
    View post(HttpRequest httpRequest);
    View put(HttpRequest httpRequest);
    View delete(HttpRequest httpRequest);
}
