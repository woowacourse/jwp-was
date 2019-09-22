package controller;

import java.io.OutputStream;

import webserver.HttpRequest;

public interface Controller {
	void service(HttpRequest httpRequest, OutputStream out);
}
