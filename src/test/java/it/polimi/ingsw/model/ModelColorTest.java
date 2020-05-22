package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**ModelColor TestClass
 * @author Piersilvio Mancuso
 */
public class ModelColorTest {
    ModelColor modelColor;

    @Before
    public void setUp() throws Exception {
        modelColor = ModelColor.BLUE;
    }

    @Test
    public void getMessage() {
        assertEquals("Blue", modelColor.getMessage());
    }

    @Test
    public void values() {
        List<ModelColor> colors = new ArrayList<>();
        colors.add(ModelColor.RED);
        colors.add(ModelColor.YELLOW);
        colors.add(ModelColor.CYAN);
        colors.add(ModelColor.GREEN);
        colors.add(ModelColor.PURPLE);
        colors.add(ModelColor.BLUE);

        assertTrue(colors.containsAll(Arrays.asList(ModelColor.values())));
        assertTrue(Arrays.asList(ModelColor.values()).containsAll(colors));
    }

}