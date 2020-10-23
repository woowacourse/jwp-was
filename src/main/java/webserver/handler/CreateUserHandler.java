package webserver.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import db.DataBase;
import model.User;
import utils.ExtractUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class CreateUserHandler extends Handler {
	private static final String USER_CREATE_URL = "/user/create";
	private static final String REDIRECT_URL = "/index.html";

	@Override
	public void handleRequest(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
		Map<String, String> userInfo = ExtractUtils.extractUserInfo(httpRequest.getBody());
		User user = User.of(userInfo);
		DataBase.addUser(user);
		HttpResponse.response302Header(dos, REDIRECT_URL, logger);
	}

	@Override
	public boolean canHandle(String path) {
		return path.equals(USER_CREATE_URL);
	}
}
