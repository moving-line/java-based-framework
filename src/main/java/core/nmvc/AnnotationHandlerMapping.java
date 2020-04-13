package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;
import was.http.HttpRequest;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {
    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() throws IllegalAccessException, InstantiationException {
        ControllerScanner sc = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = sc.getControllers();

        for (Class<?> aClass : controllers.keySet()) {
            Set<Method> methods = ReflectionUtils.getAllMethods(aClass, ReflectionUtils.withAnnotation(RequestMapping.class));

            for (Method method : methods) {
                RequestMapping rm = method.getAnnotation(RequestMapping.class);
                HandlerKey handlerKey = createHandlerKey(rm);
                HandlerExecution handlerExecution = createHandlerExecution(aClass.newInstance(), method);

                handlerExecutions.put(handlerKey, handlerExecution);
            }
        }
    }

    public HandlerExecution getHandler(HttpRequest request) {
        String requestUri = request.getPath();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toString());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
}

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }

    private HandlerExecution createHandlerExecution(Object declaredObject, Method method) {
        return new HandlerExecution(declaredObject, method);
    }
}
