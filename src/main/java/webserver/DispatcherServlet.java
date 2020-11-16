package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.HttpRequestException;
import webserver.controller.IndexController;
import webserver.controller.UserController;
import webserver.handleradaptor.DefaultHandlerAdaptor;
import webserver.handleradaptor.HandlerAdaptor;
import webserver.handlermapping.DefaultHandlerMapping;
import webserver.handlermapping.DefaultHandlerMappingStrategy;
import webserver.handlermapping.HandlerMapping;
import webserver.handlermapping.HandlerMappingStrategy;
import webserver.messageconverter.DefaultHttpMessageConverter;
import webserver.messageconverter.HttpMessageConverter;
import webserver.request.ServletRequest;
import webserver.response.ModelAndView;
import webserver.response.ServletResponse;
import webserver.response.StatusCode;

public class DispatcherServlet implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final HandlerAdaptor DEFAULT_HANDLER_ADAPTOR;
    private static final HttpMessageConverter CONVERTER;
    private static final List<HandlerMappingStrategy> HANDLER_MAPPING_STRATEGIES;
    private static final List<Class<?>> CONTROLLERS;
    private static final HandlerMapping DEFAULT_HANDLER_MAPPING;
    private static final StaticResourceHandler RESOURCE_HANDLER;

    static {
        DEFAULT_HANDLER_ADAPTOR = new DefaultHandlerAdaptor();
        CONVERTER = new DefaultHttpMessageConverter();
        HANDLER_MAPPING_STRATEGIES = Arrays.asList(
            new DefaultHandlerMappingStrategy());
        CONTROLLERS = Arrays.asList(UserController.class, IndexController.class);
        DEFAULT_HANDLER_MAPPING = new DefaultHandlerMapping(HANDLER_MAPPING_STRATEGIES, CONTROLLERS);
        RESOURCE_HANDLER = new DefaultStaticResourceHandler();
    }

    private final Socket connection;

    public DispatcherServlet(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        ServletResponse servletResponse = null;
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            ServletRequest servletRequest = new ServletRequest(br);

            if (RESOURCE_HANDLER.isStaticResourceRequest(servletRequest)) {
                servletResponse = RESOURCE_HANDLER.handle(servletRequest);
            } else {
                Method handler = DEFAULT_HANDLER_MAPPING.mapping(servletRequest);
                ModelAndView mav = DEFAULT_HANDLER_ADAPTOR.invoke(handler, servletRequest, CONVERTER);
                servletResponse = ServletResponse.of(mav, servletRequest);
            }
        } catch (HttpRequestException e) {
            logger.error(e.getMessage(), e);
            StatusCode statusCode = e.getStatusCode();
            servletResponse = ServletResponse.of(statusCode, ModelAndView.of(statusCode));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            servletResponse = ServletResponse.of(StatusCode.INTERNAL_SERVER_ERROR,
                ModelAndView.of(StatusCode.INTERNAL_SERVER_ERROR));
        } finally {
            try (DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
                if (Objects.isNull(servletResponse)) {
                    servletResponse = ServletResponse.of(StatusCode.INTERNAL_SERVER_ERROR,
                        ModelAndView.of(StatusCode.INTERNAL_SERVER_ERROR));
                }
                servletResponse.sendResponse(dos);
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
