import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import emiage.c306.sudoku.*;

public class GrilleTest {
    @Test
    public void testDimensionGrille(){
        int [][] grille = {
            {1,1,1},
            {2,2,2},
            {3,3,3}
        };
        
        //assertEquals(3, GrilleImpl.getDimension());
        
        int [][] grille2 = {
            {1,1,1,1,1,1},
            {2,2,2,2,2,2},
            {3,3,3,3,3,3},
            {3,3,3,3,3,3},
            {3,3,3,3,3,3},
            {3,3,3,3,3,3},
        };
        //assertEquals(6, );
    }
    @Test
    public void testGetValue(){

    }
    @Test
    public void testSetAndGetValue(){

    }
    @Test
    public void testIsPossible(){

    }
    @Test
    public void testIsComplete(){

    }
    
}
