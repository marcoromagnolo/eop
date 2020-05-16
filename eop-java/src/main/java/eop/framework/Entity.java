package eop.framework;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {

    private Map<Class<Controller<? extends Entity>>, Controller<? extends Entity>> instances = new HashMap<>();

    private Controller<? extends Entity> controller;

    public <C extends Controller<? extends Entity>> Entity(C controller) {
        setController(controller);
    }

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
        return (C) instances.get(controllerClass);
    }

    public <C extends Controller<? extends Entity>> void setController(C controller) {
        this.controller = controller;
        try {
            Field field = recursiveField(this.controller.getClass());
            field.setAccessible(true);
            field.set(this.controller, this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        instances.put((Class<Controller<? extends Entity>>) controller.getClass(), controller);
    }
}
