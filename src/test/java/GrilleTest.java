import emiage.c306.sudoku.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class GrilleTest {
  GrilleImpl grille;
  @BeforeEach
  public void setUp() {
  }

  @Test
  public void testDimensionGrille() throws Exception{
    Grille grille = read("/grilles/sudoku4.txt");
    assertEquals(4, grille.getDimension());
  }

  @Test
  public void testConstructionGrilleIncorrecte() throws Exception{
    assertThrows(ValeurImpossibleException.class,()-> read("/grilles/sudoku4 invalid.txt"));
  }

  private Grille read(String path) throws Exception {
    try (InputStream in = GrilleTest.class.getResourceAsStream(path)) {
      assertNotNull(in);
      return GrilleParser.parse(in);
    }
  }

  @Test
  public void testGetValueFromResource() throws Exception {
    Grille grille = read("/grilles/sudoku4.txt");
    assertEquals(4, grille.getDimension());
    assertEquals(4, grille.getElements().size());
  }

  @Test
  public void testGetValue() throws Exception {
    Grille grille = read("/grilles/sudoku9vide.txt");
    // Test case vide
    assertNull(grille.getValue(0, 0));
    assertNull(grille.getValue(3, 3));
    // Test coordonnées hors bornes
    assertThrows(HorsBornesException.class, () -> grille.getValue(-1, 0));
    assertThrows(HorsBornesException.class, () -> grille.getValue(0, -1));
    assertThrows(HorsBornesException.class, () -> grille.getValue(9, 0));
    assertThrows(HorsBornesException.class, () -> grille.getValue(0, 9));
  }

  @Test
  public void testSetAndGetValue() throws Exception {
    Grille grille = read("/grilles/sudoku9vide.txt");
    ElementDeGrille elementDeGrille = grille.getElements().iterator().next();
    // Test insertion normale
    grille.setValue(0, 0, elementDeGrille);
    assertEquals(elementDeGrille, grille.getValue(0, 0));

    // Test avec null (vider une case)
    grille.setValue(0, 0, null);
    assertNull(grille.getValue(0, 0));
    grille.setValue(0, 0, elementDeGrille);
    assertEquals(elementDeGrille, grille.getValue(0, 0));
    // Test exceptions
    assertThrows(HorsBornesException.class, () -> grille.setValue(-1, 0,elementDeGrille ));

    // Test valeur impossible (même ligne)
    assertThrows(ValeurImpossibleException.class, () -> grille.setValue(0, 1, elementDeGrille));

    // Test élément interdit
    ElementDeGrille elementInterdit = new ElementDeGrilleImplAsChar('X');
    assertThrows(ElementInterditException.class, () -> grille.setValue(0, 0, elementInterdit));
  }

  @Test
  public void testIsPossible() throws Exception {
    
    Grille grille = read("/grilles/sudoku9vide.txt");
    ElementDeGrille e1 = grille.getElements().iterator().next();
    // Test case vide
    assertTrue(grille.isPossible(0, 0, e1));

    // Test conflit sur ligne
    grille.setValue(0, 0, e1);
    assertFalse(grille.isPossible(0, 1, e1));

    // Test conflit sur colonne
    assertFalse(grille.isPossible(1, 0, e1));

    // Test conflit dans le carré
    assertFalse(grille.isPossible(1, 1, e1));
  }

  @Test
  public void testElementInterdit() throws Exception {
    Grille grille = read("/grilles/sudoku9vide.txt");
    ElementDeGrille e1 = grille.getElements().iterator().next();
   
    // Test élément interdit
    ElementDeGrille elementInterdit = new ElementDeGrilleImplAsChar('X');
    assertThrows(ElementInterditException.class, () -> grille.isPossible(0, 0, elementInterdit));

    // Test hors bornes
    assertThrows(HorsBornesException.class, () -> grille.isPossible(-1, 0, e1));
  }

  @Test
  public void testIsComplete() throws Exception {
    
    Grille grille = read("/grilles/sudoku9vide.txt");
    ElementDeGrille e1 = grille.getElements().iterator().next();
   // Test grille vide
    assertFalse(grille.isComplete());

    // Test grille partiellement remplie
    grille.setValue(0, 0, e1);
    assertFalse(grille.isComplete());

    grille = read("/grilles/sudoku4-complete.txt");
    // Test grille complète
    assertTrue(grille.isComplete());
  }

}
