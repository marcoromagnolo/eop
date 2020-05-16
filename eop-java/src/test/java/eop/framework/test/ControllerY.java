package eop.framework.test;

import eop.framework.Controller;

public class ControllerY extends Controller<EntityX> {

    private int a, b, c;

    public ControllerY(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getStr() {
        return entity.str;
    }

    public void setStr(String str) {
        entity.str = str;
    }
}
