package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import webserver.controller.Handlers;
import webserver.controller.IndexController;
import webserver.controller.StaticResourceHandlers;
import webserver.controller.UserController;
import webserver.handleradaptor.DefaultHandlerAdaptor;
import webserver.handleradaptor.HandlerAdaptor;
import webserver.handlermapping.DefaultHandlerMapping;
import webserver.handlermapping.DefaultHandlerMappingStrategy;
import webserver.handlermapping.HandlerMapping;
import webserver.handlermapping.HandlerMappingStrategy;
import webserver.handlermapping.StaticResourceHandlerMappingStrategy;
import webserver.messageconverter.DefaultHttpMessageConverter;
import webserver.messageconverter.HttpMessageConverter;
import webserver.request.ServletRequest;
import webserver.response.ModelAndView;
import webserver.response.ServletResponse;
import webserver.response.StatusCode;
import webserver.response.View;

public class DispatcherServlet implements Runnable {
    public static final String INTERNAL_SERVER_ERROR_VIEW_NAME = "internalServerError";
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final Socket connection;

    public DispatcherServlet(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            ServletRequest servletRequest = new ServletRequest(br);
            List<Class<? extends Handlers>> controllers = Arrays.asList(UserController.class, IndexController.class,
                StaticResourceHandlers.class);
            List<HandlerMappingStrategy> handlerMappingStrategies = Arrays.asList(new DefaultHandlerMappingStrategy(),
                new StaticResourceHandlerMappingStrategy());
            HandlerMapping defaultHandlerMapping = new DefaultHandlerMapping(handlerMappingStrategies, controllers);
            Method handler = defaultHandlerMapping.mapping(servletRequest);
            HandlerAdaptor defaultHandlerAdaptor = new DefaultHandlerAdaptor();
            HttpMessageConverter converter = new DefaultHttpMessageConverter();
            final ModelAndView mav = defaultHandlerAdaptor.invoke(handler, servletRequest, converter);
            View view = View.of(mav.getViewName());
            ServletResponse response = ServletResponse.of(mav, servletRequest, view);
            response.sendResponse(dos);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            try (DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
                ModelAndView mav = ModelAndView.of(StatusCode.INTERNAL_SERVER_ERROR,
                    Maps.newLinkedHashMap(), Maps.newLinkedHashMap(), INTERNAL_SERVER_ERROR_VIEW_NAME);
                ServletResponse internalServerError = ServletResponse.of(mav, View.of(INTERNAL_SERVER_ERROR_VIEW_NAME));
                internalServerError.sendResponse(dos);
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
