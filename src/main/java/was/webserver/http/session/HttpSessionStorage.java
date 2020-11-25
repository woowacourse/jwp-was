package was.webserver.http.session;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionStorage {
    private static final String JSESSIONID_DELIMITER_REGEX = "=";
    private static final int UUID_INDEX = 1;
    private static final Map<UUID, HttpSession> sessions = new ConcurrentHashMap<>();

    public static HttpSession create(String id) {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("userId", id);
        httpSession.setAttribute("Path", "/");
        sessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public static boolean isValidSession(List<String> jSessionCookies) {
        return jSessionCookies.stream()
                .anyMatch(cookie -> {
                    String uuid = cookie.split(JSESSIONID_DELIMITER_REGEX)[UUID_INDEX];
                    UUID jSessionUuid = UUID.fromString(uuid);
                    return sessions.containsKey(jSessionUuid)
                            && sessions.get(jSessionUuid).isValidSession();
                });
    }
}
