package webserver.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import webserver.HttpCookie;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpSession;
import webserver.SessionStorage;

public class UserListHandler extends Handler {
	private static final String USER_LIST_URL = "/user/list";

	@Override
	public void handleRequest(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
		String allCookies = httpRequest.getHeader("Cookie");
		HttpCookie cookies = new HttpCookie(allCookies);
		HttpSession httpSession = SessionStorage.getSession(cookies.getCookie("sessionId"));
		boolean isLogin = (boolean)httpSession.getAttribute("logined");

		if (isLogin) {
			String template = createTemplate(httpRequest);
			HttpResponse.responseUserList200Header(dos, template.getBytes(), logger);
		} else {
			HttpResponse.responseNotLogin302Header(dos, logger);
		}
	}

	private String createTemplate(HttpRequest httpRequest) throws IOException {
		TemplateLoader loader = new ClassPathTemplateLoader();
		loader.setPrefix("/templates");
		loader.setSuffix(".html");
		Handlebars handlebars = new Handlebars(loader);
		handlebars.registerHelper("indexModifier", (Helper<Integer>)(index, options) -> index + 1);
		Map<String, Object> map = new HashMap<>();
		map.put("users", DataBase.findAll());
		Template template = handlebars.compile(httpRequest.getUrl());

		return template.apply(map);
	}

	@Override
	public boolean canHandle(String url) {
		return USER_LIST_URL.equals(url);
	}
}
