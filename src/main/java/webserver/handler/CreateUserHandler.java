package webserver.handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import db.DataBase;
import model.User;
import utils.ExtractUtils;
import webserver.Contents;
import webserver.HttpResponse;

public class CreateUserHandler extends Handler {
	private static final String USER_CREATE_URL = "/user/create";
	private static final String REDIRECT_URL = "/index.html";

	@Override
	public void handleRequest(String path, DataOutputStream dos, BufferedReader br) throws IOException {
		Contents contents = new Contents(br, 100);
		Map<String, String> userInfo = ExtractUtils.extractUserInfo(contents.getBody());
		User user = new User(userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"),
			userInfo.get("email"));
		DataBase.addUser(user);
		HttpResponse.response302Header(dos, REDIRECT_URL, logger);
	}

	@Override
	public boolean canHandle(String path) {
		return path.startsWith(USER_CREATE_URL);
	}
}
