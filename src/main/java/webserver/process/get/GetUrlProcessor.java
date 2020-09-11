package webserver.process.get;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.function.Function;

import model.User;
import utils.FileIoUtils;
import webserver.exception.NotExistUrlException;

public class GetUrlProcessor implements Function<String, byte[]> {

	@Override
	public byte[] apply(String url) {
		if (url.equals("/index.html")) {
			try {
				return FileIoUtils.loadFileFromClasspath("./templates/index.html");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if (url.equals("/user/form.html")) {
			try {
				return FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if (url.contains("/user/create")) {
			// 파싱해야된다
			User user = UserParser.parseUser(url);

			// 파싱한 값 저장
			return "create user success".getBytes();
		}
		throw new NotExistUrlException();
	}
}
