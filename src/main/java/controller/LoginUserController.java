package controller;

import controller.exception.LoginUserException;
import controller.exception.NotSupportMethod;
import db.DataBase;
import http.HttpStatusCode;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class LoginUserController extends AbstractController {
	public static final String PATH = "/user/login";

	@Override
	void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		String userId = httpRequest.getParameter("userId");
		String password = httpRequest.getParameter("password");
		User user = DataBase.findUserById(userId);

		try {
			if (user.checkPassword(password)) {
				throw new LoginUserException("password not match");
			}

			httpResponse.setStatusCode(HttpStatusCode.FOUND);
			httpResponse.setCookie("logined=true;path=/");
			httpResponse.redirect("/");
		} catch (LoginUserException e) {
			httpResponse.setStatusCode(HttpStatusCode.NOT_FOUND);
			httpResponse.setCookie("logined=false");
			httpResponse.redirect("/");
			e.printStackTrace();
		}
	}

	@Override
	void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		throw new NotSupportMethod("Not support" + httpRequest.getMethod());
	}
}
