package jwp.was.webapplicationserver.configure.maker;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.Set;
import jwp.was.webapplicationserver.configure.ConfigureMaker;
import jwp.was.webapplicationserver.configure.annotation.Controller;
import org.junit.jupiter.api.Test;

class ConfigureMakerTest {

    @Test
    void getControllerInstances() {
        Class<? extends Annotation> controllerAnnotation = Controller.class;

        ConfigureMaker configureMaker = ConfigureMaker.getInstance();
        Set<Object> controllerInstances
            = configureMaker.getConfiguresWithAnnotation(controllerAnnotation);

        for (Object controllerInstance : controllerInstances) {
            assertThat(controllerInstance.getClass().isAnnotationPresent(controllerAnnotation));
        }
    }
}
