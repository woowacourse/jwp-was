package webserver;

import db.DataBase;
import model.User;
import utils.UrlEncodedBodyParser;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    public static Response signUp(Request req) {
        String body = new String(req.getBody());
        Map<String, String> parsedBody = UrlEncodedBodyParser.parse(body);
        User user = new User(parsedBody.get("userId"),
            parsedBody.get("password"),
            parsedBody.get("name"),
            parsedBody.get("email"));
        DataBase.addUser(user);

        Map<String, String> headers = new HashMap<>();
        headers.put("Location", "/index.html");
        return new Response(302, "Found", null, headers, null);
    }
}
