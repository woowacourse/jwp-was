package kr.wootecat.dongle.model.handler;

import java.util.Arrays;

import kr.wootecat.dongle.model.servlet.ServletMapper;

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
