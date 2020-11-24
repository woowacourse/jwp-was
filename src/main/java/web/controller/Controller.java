package web.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import web.request.HttpRequest;
import web.response.HttpResponse;

public interface Controller {
	void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;

	boolean canHandle(String path);
}
