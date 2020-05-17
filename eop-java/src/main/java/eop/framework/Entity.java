package eop.framework;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {

    private Map<Class<Controller<? extends Entity>>, Controller<? extends Entity>> instances = new HashMap<>();

    private Controller<? extends Entity> controller;

    private Field recursiveField(Class clazz) {
        Class superClass = clazz.getSuperclass();
        try {
            Field field = superClass.getDeclaredField("entity");
            return field;
        } catch (NoSuchFieldException e) {
            return recursiveField(superClass);
        }
    }

    public <C extends Controller<? extends Entity>> C getController(Class<C> controllerClass) {
        if (!instances.containsKey(controllerClass)) {
            throw new IllegalArgumentException(controllerClass + " controller type not initialized");
        }
        controller = instances.get(controllerClass);
        return (C) controller;
    }

    public <C extends Controller<? extends Entity>> void init(C controller) {
        this.controller = controller;
        try {
            Field field = recursiveField(this.controller.getClass());
            field.setAccessible(true);
            field.set(this.controller, this);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
        instances.put((Class<Controller<? extends Entity>>) controller.getClass(), controller);
    }

    public void invoke(String methodName) {
        try {
            Method method = controller.getClass().getDeclaredMethod(methodName);
            method.invoke(controller);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void invoke(String methodName, Object... params) {
        try {
            Method method = controller.getClass().getDeclaredMethod(methodName);
            method.invoke(controller, params);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <R extends Object> R invoke(String methodName, Class<R> ret, Object... params) {
        try {
            Method method = controller.getClass().getDeclaredMethod(methodName);
            return (R) method.invoke(controller, params);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
