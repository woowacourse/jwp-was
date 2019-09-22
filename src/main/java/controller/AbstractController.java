package controller;

import java.io.DataOutputStream;
import java.io.OutputStream;

import http.method.HttpMethod;
import http.HttpRequest;

public abstract class AbstractController implements Controller {
	@Override
	public void service(HttpRequest httpRequest, OutputStream out) {
		DataOutputStream dos = new DataOutputStream(out);
		if (HttpMethod.GET.isSameMethod(httpRequest.getRequestHeaderElement("Method"))) {
			doGet(httpRequest, dos);
			return;
		}
		doPost(httpRequest, dos);
	}

	public abstract void doPost(HttpRequest httpRequest, DataOutputStream dos);

	public abstract void doGet(HttpRequest httpRequest, DataOutputStream dos);
}
