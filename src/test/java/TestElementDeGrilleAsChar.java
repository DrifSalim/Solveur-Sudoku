import emiage.c306.sudoku.ElementDeGrille;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestElementDeGrilleAsChar {
    /**Test to string. */
    @Test
    public void testToString() {
        ElementDeGrille element = new ElementDeGrilleImplAsChar('5');
        assertEquals("5", element.toString());
    }
    /**Test equals avec le même objet. */
    @Test
    public void testEqualsWithSameObject() {
        ElementDeGrille element1 = new ElementDeGrilleImplAsChar('5');
        Assertions.assertTrue(element1.equals(element1));
    }
    /**Test equals avec un objet null. */
    @Test
    public void testEqualsWithObjectNull() {
        ElementDeGrille element1 = null;
        ElementDeGrille element2 = new ElementDeGrilleImplAsChar('5');
        Assertions.assertFalse(element2.equals(element1));
    }
    /**Test equals avec un objet d'une même classe et même valeur. */
    @Test
    public void testEqualsWithSameClassAndSameValue() {
        ElementDeGrille element1 = new ElementDeGrilleImplAsChar('5');
        ElementDeGrille element2 = new ElementDeGrilleImplAsChar('5');
        Assertions.assertTrue(element1.equals(element2));
    }
    /**Test equals avec un objet d'une même classe et valeur différente. */
    @Test
    public void testEqualsWithSameClassAndDifferentValue() {
        ElementDeGrille element1 = new ElementDeGrilleImplAsChar('5');
        ElementDeGrille element3 = new ElementDeGrilleImplAsChar('6');
        Assertions.assertFalse(element1.equals(element3));
    }
    /**Test equals avec des classes différentes. */
    @Test
    public void testEqualsWithDifferentClass() {
        ElementDeGrille element1 = new ElementDeGrilleImplAsChar('5');
        Assertions.assertFalse(element1.equals('5'));
    }
    /**Test hashCode. */
    @Test
    public void testHashCode() {
        ElementDeGrille element1 = new ElementDeGrilleImplAsChar('5');
        ElementDeGrille element2 = new ElementDeGrilleImplAsChar('5');
        ElementDeGrille element3 = new ElementDeGrilleImplAsChar('6');

        assertEquals(element1.hashCode(), element2.hashCode());
        assertNotEquals(element1.hashCode(), element3.hashCode());
    }
}
