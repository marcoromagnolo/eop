package eop.framework.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestEntityController {

    @Test
    public void testInstanceRecycle() {

        // Create new Instance
        EntityX entityX = new EntityX();
        entityX.init(new ControllerY(1, 2, 3));
        entityX.getController(ControllerY.class).setStr("ciao");
        assertEquals("ciao", entityX.str);

        // Overwrite Instance
        entityX.init(new ControllerY(4,5,6));
        String str = entityX.getController(ControllerY.class).getStr();
        assertEquals("ciao", str);

        try {
            entityX.getController(ControllerZ.class).alterStr();
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }

        // Recycle Instance with new ControllerZ
        entityX.init(new ControllerZ("foo"));
        entityX.getController(ControllerZ.class).alterStr();
        assertEquals("foo", entityX.str);

        // Back to ControllerY
        String str1 = entityX.getController(ControllerY.class).getStr();
        assertEquals("foo", str1);
    }

    @Test
    public void testHyperPolymorphism() {
        List<EntityX> list = new ArrayList<>();
        EntityX entityX1 = new EntityX();
        entityX1.init(new ControllerY(1, 2, 3));
        EntityX entityX2 = new EntityX();
        entityX2.init(new ControllerZ("hyper"));
        list.add(entityX1);
        list.add(entityX2);
        for (EntityX entity : list) {
            String str = entity.invoke("getStr", String.class);
        }

        assertNull(entityX1.invoke("getStr", String.class));
        entityX2.invoke("alterStr");
        assertEquals("hyper", entityX2.invoke("getStr", String.class));
    }

    @Test
    public void testDynamicInheritance() {
        Professional professional = new Professional();
        professional.init(new ProfessionalReseller());
        professional.getController(ProfessionalReseller.class).resell();
        professional.init(new PersonReseller());
        professional.getController(PersonReseller.class).resell();
    }
}
