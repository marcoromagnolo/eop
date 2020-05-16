package eop.framework.test;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestEntityController {

    @Test
    public void test2() {

        // Create new Instance
        EntityX entityX = new EntityX(new ControllerY(1, 2, 3));
        entityX.getController(ControllerY.class).setStr("ciao");
        assertEquals("ciao", entityX.str);

        // Overwrite Instance
        entityX.setController(new ControllerY(4,5,6));
        String str = entityX.getController(ControllerY.class).getStr();
        assertEquals("ciao", str);

        try {
            entityX.getController(ControllerZ.class).alterStr();
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }

        // Recycle Instance with new ControllerZ
        entityX.setController(new ControllerZ("foo"));
        entityX.getController(ControllerZ.class).alterStr();
        assertEquals("foo", entityX.str);

        // Back to ControllerY
        String str1 = entityX.getController(ControllerY.class).getStr();
        assertEquals("foo", str1);
    }
}
