package testhelper;

import http.request.HttpRequest;
import http.request.support.HttpRequestFactory;
import http.servlet.controller.*;
import http.session.support.RandomKeyGenerator;
import http.session.support.SessionManager;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Common {
    private static final String TEST_DIRECTORY_PATH = "./src/test/resources/";
    private static Map<String, Controller> api;

    static {
        api = new HashMap<>();
        api.put("/user/create", new UserCreateController());
        api.put("/user/login", new UserLoginController());
        api.put("/user/list", new UserListController());
    }

    public static BufferedReader getBufferedReaderOfTextFile(final String path) throws FileNotFoundException {
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

    public static HttpRequest getHttpRequest(final String path) throws IOException {
        return HttpRequestFactory.create(getBufferedReaderOfTextFile(path), getSessionManager());
    }

    private static SessionManager getSessionManager() {
        return new SessionManager(new RandomKeyGenerator());
    }
}
