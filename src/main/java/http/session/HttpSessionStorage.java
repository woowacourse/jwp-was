package http.session;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class HttpSessionStorage {
    private static Map<UUID, HttpSession> sessions = new ConcurrentHashMap<>();

    public static HttpSession generate(String userId) {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("userId", userId);
        sessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public static boolean isValidSession(String cookie) {
        String[] attributes = cookie.split("; ");
        Optional<String> jsessionid = Stream.of(attributes)
            .filter(it -> it.startsWith("jsessionid"))
            .map(it -> it.substring("jsessionid=".length()))
            .findAny();

        try {
            return sessions.containsKey(UUID.fromString(jsessionid.get()));
        } catch (IllegalArgumentException | NoSuchElementException | NullPointerException e) {
            return false;
        }
    }
}
