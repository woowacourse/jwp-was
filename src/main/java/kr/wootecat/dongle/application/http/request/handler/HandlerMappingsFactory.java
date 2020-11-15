package kr.wootecat.dongle.application.http.request.handler;

import java.util.Arrays;

import kr.wootecat.dongle.application.servlet.ServletMapper;

public class HandlerMappingsFactory {
    public static HandlerMappings create() {
        return new HandlerMappings(
                Arrays.asList(
                        new StaticResourceHandlerMapping(),
                        new ServletHandlerMapping(ServletMapper.getInstance())
                )
        );
    }
}
