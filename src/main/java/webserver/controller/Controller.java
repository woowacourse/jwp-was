package webserver.controller;

import java.io.OutputStream;

import webserver.http.request.HttpRequest;

public interface Controller {
	void service(HttpRequest httpRequest, OutputStream out);
}
