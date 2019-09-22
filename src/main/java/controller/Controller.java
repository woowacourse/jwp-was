package controller;

import java.io.OutputStream;

import http.HttpRequest;

public interface Controller {
	void service(HttpRequest httpRequest, OutputStream out);
}
