package eop.framework.test;

import eop.framework.Controller;
import eop.framework.Entity;

public class EntityX extends Entity {

    public String str;

    public <C extends Controller<EntityX>> EntityX(C controller) {
        super(controller);
    }
}
