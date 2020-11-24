package kr.wootecat.dongle.model.servlet;

import static kr.wootecat.dongle.model.http.HttpMethod.*;

import java.util.HashMap;

import kr.wootecat.dongle.model.http.HttpMethod;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;

public class ServletMethodInvokerFactory {

    private ServletMethodInvokerFactory() {
    }

    public static ServletMethodInvoker create() {
        HashMap<HttpMethod, ServletMethodInvoker.TriConsumer<HttpServlet, HttpRequest, HttpResponse>> defaultInvokers = new HashMap<>();
        defaultInvokers.put(GET, HttpServlet::doGet);
        defaultInvokers.put(POST, HttpServlet::doPost);

        return new ServletMethodInvoker(defaultInvokers);
    }
}
