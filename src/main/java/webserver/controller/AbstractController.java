package webserver.controller;

import java.io.DataOutputStream;
import java.io.OutputStream;

import webserver.http.method.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.session.SessionManager;

import static webserver.http.request.HttpRequestReader.HTTP_METHOD;


public abstract class AbstractController implements Controller {
	protected static final SessionManager sessionManager = new SessionManager();

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
