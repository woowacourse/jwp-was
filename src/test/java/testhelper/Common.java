package testhelper;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import model.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.controller.*;
import servlet.resolver.UserResolver;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Common {
    private static final Logger logger = LoggerFactory.getLogger(Common.class);

    private static final String TEST_DIRECTORY_PATH = "./src/test/resources/";
    private static Map<String, Controller> api;

    static {
        api = new HashMap<>();
        api.put("/user/create", new UserCreateController());
        api.put("/user/login", new UserLoginController());
        api.put("/user/list", new UserListController());

        try {
            HttpRequest httpRequest =
                    HttpRequestFactory.create(Common.getBufferedReaderOfText("HTTP_POST_USER_CREATE.txt"));
            new UserService().addUser(UserResolver.resolve(httpRequest));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static BufferedReader getBufferedReaderOfText(final String path) throws FileNotFoundException {
        InputStreamReader inputStreamReader = new InputStreamReader(getInputStream(path));
        return new BufferedReader(inputStreamReader);
    }

    public static InputStream getInputStream(final String path) throws FileNotFoundException {
        String absolutePath = TEST_DIRECTORY_PATH + path;
        return new FileInputStream(new File(absolutePath));
    }

    public static BufferedReader convertToBufferedReader(final OutputStream outputStream) {
        InputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public static ControllerFinder getControllerFinder() {
        return new ControllerFinder(Collections.unmodifiableMap(api));
    }
}
