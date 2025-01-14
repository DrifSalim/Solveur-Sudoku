import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import emiage.c306.sudoku.*;

public class GrilleTest {
    private GrilleImpl grille = new GrilleImpl(9);
    ElementDeGrille e1 = new ElementDeGrilleImplAsChar('1');
    ElementDeGrille e2 = new ElementDeGrilleImplAsChar('2');
    ElementDeGrille e3 = new ElementDeGrilleImplAsChar('3');    
    ElementDeGrille e4 = new ElementDeGrilleImplAsChar('4');    
    ElementDeGrille e5 = new ElementDeGrilleImplAsChar('5');    
    ElementDeGrille e6 = new ElementDeGrilleImplAsChar('6');    
    ElementDeGrille e7 = new ElementDeGrilleImplAsChar('7'); 
    ElementDeGrille e8 = new ElementDeGrilleImplAsChar('8');
    ElementDeGrille e9 = new ElementDeGrilleImplAsChar('9');    
    
   

    @Test
    public void testDimensionGrille(){
        assertEquals(9, grille.getDimension());
    }
    @Test
    public void testGetValue() throws HorsBornesException{
        // Test case vide
        assertNull(grille.getValue(0, 0));
        assertEquals(3,grille.getValue(3, 3));
        // Test coordonnées hors bornes
        assertThrows(HorsBornesException.class, () -> grille.getValue(-1, 0));
        assertThrows(HorsBornesException.class, () -> grille.getValue(0, -1));
        assertThrows(HorsBornesException.class, () -> grille.getValue(9, 0));
        assertThrows(HorsBornesException.class, () -> grille.getValue(0, 9));
    }
    @Test
    public void testSetAndGetValue() throws HorsBornesException, ValeurImpossibleException, ElementInterditException, ValeurInitialeModificationException{
         // Test insertion normale
         grille.setValue(0, 0, e1);
         assertEquals(e1, grille.getValue(0, 0));
         
         // Test avec null (vider une case)
         grille.setValue(0, 0, null);
         assertNull(grille.getValue(0, 0));
         
         // Test exceptions
         assertThrows(HorsBornesException.class, () -> grille.setValue(-1, 0, e1));
         
         // Test valeur impossible (même ligne)
         grille.setValue(0, 0, e1);
         assertThrows(ValeurImpossibleException.class, () -> grille.setValue(0, 1, e1));
         
         // Test élément interdit
         ElementDeGrille elementInterdit = new ElementDeGrilleImplAsChar('X');
         assertThrows(ElementInterditException.class, () -> grille.setValue(0, 0, elementInterdit));
    }
    @Test
    public void testIsPossible() throws HorsBornesException, ElementInterditException, ValeurImpossibleException, ValeurInitialeModificationException{
         // Test case vide
         assertTrue(grille.isPossible(0, 0, e1));
        
         // Test conflit sur ligne
         grille.setValue(0, 0, e1);
         assertFalse(grille.isPossible(0, 1, e1));
         
         // Test conflit sur colonne
         assertFalse(grille.isPossible(1, 0, e1));
         
         // Test conflit dans le carré
         assertFalse(grille.isPossible(1, 1, e1));
         
         // Test élément interdit
         ElementDeGrille elementInterdit = new ElementDeGrilleImplAsChar('X');
         assertThrows(ElementInterditException.class, () -> grille.isPossible(0, 0, elementInterdit));
         
         // Test hors bornes
         assertThrows(HorsBornesException.class, () -> grille.isPossible(-1, 0, e1));
    }
    @Test
    public void testIsComplete() throws HorsBornesException, ValeurImpossibleException, ElementInterditException, ValeurInitialeModificationException{
         // Test grille vide
         assertFalse(grille.isComplete());
        
         // Test grille partiellement remplie
         grille.setValue(0, 0, e1);
         assertFalse(grille.isComplete());
         
         // Test grille complète
         assertTrue(grille.isComplete());
    }
    
}
