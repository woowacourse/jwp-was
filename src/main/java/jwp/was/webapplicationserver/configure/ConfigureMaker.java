package jwp.was.webapplicationserver.configure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import jwp.was.webapplicationserver.configure.annotation.Autowired;
import jwp.was.webapplicationserver.configure.annotation.Configure;
import jwp.was.webapplicationserver.configure.annotation.Controller;
import org.reflections.Reflections;

public class ConfigureMaker {

    private static final String SCAN_PACKAGE_ROUTE = "jwp.was";
    private static final ConfigureMaker INSTANCE = new ConfigureMaker();

    private final Set<Object> configures;

    private ConfigureMaker() {
        Reflections reflections = new Reflections(SCAN_PACKAGE_ROUTE);
        Set<Class<?>> configureClasses = getTypesAnnotatedWithConfigure(reflections);
        Set<Object> configureInstances = new HashSet<>();

        for (Class<?> configureClass : configureClasses) {
            addInstance(configureInstances, configureClass);
        }

        for (Object configureInstance : configureInstances) {
            addFields(configureInstance, configureInstances);
        }

        this.configures = configureInstances;
    }

    public static ConfigureMaker getInstance() {
        return INSTANCE;
    }

    private Set<Class<?>> getTypesAnnotatedWithConfigure(Reflections reflections) {
        return reflections.getTypesAnnotatedWith(Configure.class).stream()
            .filter(aClass -> !aClass.isAnnotation())
            .collect(Collectors.toSet());
    }

    private void addInstance(Set<Object> instances, Class<?> aClass) {
        for (Constructor<?> constructor : aClass.getDeclaredConstructors()) {
            try {
                instances.add(constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void addFields(Object configureInstance, Set<Object> configureInstances) {
        Field[] fields = configureInstance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            field.setAccessible(true);
            try {
                field.set(configureInstance, getField(field, configureInstances));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object getField(Field field, Set<Object> configureInstances) {
        return configureInstances.stream()
            .filter(configureInstance -> isEqualType(field, configureInstance))
            .findFirst()
            .orElseThrow(
                () -> new IllegalArgumentException(field.getType().getName() + " Bean이 없습니다."));
    }

    private boolean isEqualType(Field field, Object configureInstance) {
        Class<?> configureInstanceType = configureInstance.getClass();
        Class<?> fieldType = field.getType();
        return configureInstanceType.equals(fieldType);
    }

    public Set<Object> getControllerInstances() {
        return configures.stream()
            .filter(this::isController)
            .collect(Collectors.toSet());
    }

    private boolean isController(Object configureInstance) {
        return configureInstance.getClass().isAnnotationPresent(Controller.class);
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfigure(Class<T> aClass) {
        return (T) configures.stream()
            .filter(configureInstance -> configureInstance.getClass().equals(aClass))
            .findFirst()
            .orElseThrow(
                () -> new IllegalArgumentException(aClass.getName() + "에 해당하는 configure가 없습니다."));
    }
}
