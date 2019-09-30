package mvc.controller;

import server.http.request.HttpRequest;
import mvc.view.View;
import was.http.servlet.Servlet;

import java.io.UnsupportedEncodingException;

public interface Controller extends Servlet {
    View get(HttpRequest httpRequest);
    View post(HttpRequest httpRequest) throws UnsupportedEncodingException;
    View put(HttpRequest httpRequest);
    View delete(HttpRequest httpRequest);
}
