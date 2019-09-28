package controller;

import java.io.DataOutputStream;
import java.io.OutputStream;

import http.method.HttpMethod;
import http.request.HttpRequest;

import static http.request.HttpRequestReader.HTTP_METHOD;


public abstract class AbstractController implements Controller {
	@Override
	public void service(HttpRequest httpRequest, OutputStream out) {
		DataOutputStream dos = new DataOutputStream(out);
		if (HttpMethod.GET.isSameMethod(httpRequest.getRequestLineElement(HTTP_METHOD))) {
			doGet(httpRequest, dos);
			return;
		}
		doPost(httpRequest, dos);
	}

	public abstract void doPost(HttpRequest httpRequest, DataOutputStream dos);

	public abstract void doGet(HttpRequest httpRequest, DataOutputStream dos);
}
