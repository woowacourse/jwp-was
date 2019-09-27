package mvc.config;

import mvc.annotation.RequestMapping;
import mvc.controller.Controller;
import org.reflections.Reflections;
import was.http.Router;

import java.util.Set;

public class ControllerScanner {
    private static final Router ROUTER =Router.getInstance();

    public static void scan() {
        try {
            Reflections reflections = new Reflections("domain.controller");
            Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(RequestMapping.class);
            for (Class<?> controller : controllers) {
                ROUTER.addServlet(controller.getAnnotation(RequestMapping.class).urlPath(),
                        (Controller) controller.newInstance());
            }
        } catch (Exception e) {
            throw new ControllerScanFailException();
        }
    }
}
