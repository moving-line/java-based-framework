package core.nmvc;

import core.annotation.Controller;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private final Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() throws InstantiationException, IllegalAccessException {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(annotated);
    }

    private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers) throws IllegalAccessException, InstantiationException {
        Iterator<Class<?>> iterator = preInitiatedControllers.iterator();
        Map<Class<?>, Object> controllers = new HashMap<>();

        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            controllers.put(clazz, clazz.newInstance());
        }
        return controllers;
    }
}
