package controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerManager {

    private final Map<Class<?>, Controller> controllers;

    public ControllerManager(List<Class> controllerInfos) {
        try {
            controllers = createControllers(controllerInfos);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException
                | IllegalAccessException e) {
            throw new IllegalArgumentException("올바르지 않은 컨트롤러 정보가 들어왔습니다.");
        }
    }

    private Map<Class<?>, Controller> createControllers(List<Class> controllerInfos)
            throws NoSuchMethodException, IllegalAccessException,
                InvocationTargetException, InstantiationException {
        Map<Class<?>, Controller> controllersMap = new HashMap<>();

        for (Class<?> controllerInfo : controllerInfos) {
            controllersMap.put(controllerInfo,
                (Controller) controllerInfo.getConstructor().newInstance());
        }
        return Collections.unmodifiableMap(controllersMap);
    }

    public Controller get(Class<?> controllerClass) {
        if (!controllers.containsKey(controllerClass)) {
            throw new IllegalArgumentException("this controller does not exist.");
        }
        return controllers.get(controllerClass);
    }
}
