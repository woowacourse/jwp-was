package clinet.controller;

import db.DataBase;
import model.User;
import web.request.HttpRequest;
import web.response.HttpResponse;

public class CreateUserController extends AbstractController {
	private static final String INDEX_HTML_LOCATION = "/index.html";
	public static final String USER_CREATE_URI = "/user/create";

	@Override
	public void doPost(HttpRequest request, HttpResponse response) {
		User user = new User(request.getParameter("userId"), request.getParameter("password"),
			request.getParameter("name"), request.getParameter("email"));
		DataBase.addUser(user);

		logger.debug("Database : {}", user);
		response.sendRedirect(INDEX_HTML_LOCATION);
	}

	@Override
	public boolean canHandle(String path) {
		return path.contains(USER_CREATE_URI);
	}
}
