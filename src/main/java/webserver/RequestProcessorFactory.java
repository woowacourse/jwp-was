package webserver;

import java.util.Arrays;

import webserver.servlet.ServletMapper;
import webserver.servlet.handler.HandlerMappings;
import webserver.servlet.handler.ServletHandlerMapping;
import webserver.servlet.handler.StaticResourceHandlerMapping;

public class RequestProcessorFactory {
    public static RequestProcessor getInstance() {
        return new RequestProcessor(new HandlerMappings(
                Arrays.asList(
                        new StaticResourceHandlerMapping(),
                        new ServletHandlerMapping(ServletMapper.getInstance())
                )
        ));
    }
}
