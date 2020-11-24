package net.slipp;

import static java.util.Collections.*;

import java.util.HashMap;
import java.util.function.Supplier;

import net.slipp.presentation.LoginServlet;
import net.slipp.presentation.UserCreateServlet;
import net.slipp.presentation.UserListServlet;

import kr.wootecat.dongle.ServletConfig;
import kr.wootecat.dongle.model.servlet.HttpServlet;
import kr.wootecat.dongle.view.dto.ServletMappingConfig;

public class ServletDescriptor {

    private static final HashMap<String, Supplier<HttpServlet>> urlServletMapping = new HashMap<>();

    static {
        urlServletMapping.put("/user/create", UserCreateServlet::new);
        urlServletMapping.put("/user/login", LoginServlet::new);
        urlServletMapping.put("/user/list", UserListServlet::new);
    }

    private ServletDescriptor() {
    }

    public static ServletConfig getConfig() {
        return new ServletConfig(new ServletMappingConfig(unmodifiableMap(urlServletMapping)));
    }
}
