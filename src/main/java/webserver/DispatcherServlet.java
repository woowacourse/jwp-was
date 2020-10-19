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
    private static final HandlerAdaptor DEFAULT_HANDLER_ADAPTOR;
    private static final HttpMessageConverter CONVERTER;
    private static final List<HandlerMappingStrategy> HANDLER_MAPPING_STRATEGIES;
    private static final List<Class<?>> CONTROLLERS;
    private static final HandlerMapping DEFAULT_HANDLER_MAPPING;

    static {
        DEFAULT_HANDLER_ADAPTOR = new DefaultHandlerAdaptor();
        CONVERTER = new DefaultHttpMessageConverter();
        HANDLER_MAPPING_STRATEGIES = Arrays.asList(
            new DefaultHandlerMappingStrategy(), new StaticResourceHandlerMappingStrategy());
        CONTROLLERS = Arrays.asList(UserController.class, IndexController.class,
            StaticResourceHandlers.class);
        DEFAULT_HANDLER_MAPPING = new DefaultHandlerMapping(HANDLER_MAPPING_STRATEGIES, CONTROLLERS);
    }

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
            Method handler = DEFAULT_HANDLER_MAPPING.mapping(servletRequest);
            final ModelAndView mav = DEFAULT_HANDLER_ADAPTOR.invoke(handler, servletRequest, CONVERTER);
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
