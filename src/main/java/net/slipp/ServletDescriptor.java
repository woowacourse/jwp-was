package net.slipp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.slipp.presentation.LoginServlet;
import net.slipp.presentation.UserListServlet;
import net.slipp.presentation.UserServlet;

import kr.wootecat.dongle.core.servlet.HttpServlet;

public class ServletDescriptor {

    private static final HashMap<String, Supplier<HttpServlet>> urlServletMapping = new HashMap<>();

    static {
        urlServletMapping.put("/user/create", UserServlet::new);
        urlServletMapping.put("/user/login", LoginServlet::new);
        urlServletMapping.put("/user/list", UserListServlet::new);
    }

    private ServletDescriptor() {
    }

    public static Map<String, Supplier<HttpServlet>> getConfig() {
        return Collections.unmodifiableMap(urlServletMapping);
    }
}
