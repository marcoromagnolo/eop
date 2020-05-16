package eop.framework.test;

import eop.framework.Controller;

public class ControllerZ extends Controller<EntityX> {

    private String s;

    public ControllerZ(String s) {
        this.s = s;
    }

    public String alterStr() {
        entity.str = s;
        return entity.str;
    }
}
