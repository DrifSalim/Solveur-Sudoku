import emiage.c306.sudoku.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class GrilleTest {
  ElementDeGrille e9 = new ElementDeGrilleImplAsChar('9');

  @BeforeEach
  public void setUp() {}

  @Test
  public void testDimensionGrille() {
    assertEquals(9, grille.getDimension());
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
    assertEquals(3, grille.getValue(3, 3));
    // Test coordonnées hors bornes
    assertThrows(HorsBornesException.class, () -> grille.getValue(-1, 0));
    assertThrows(HorsBornesException.class, () -> grille.getValue(0, -1));
    assertThrows(HorsBornesException.class, () -> grille.getValue(9, 0));
    assertThrows(HorsBornesException.class, () -> grille.getValue(0, 9));
  }

  @Test
  public void testSetAndGetValue() throws HorsBornesException, ValeurImpossibleException, ElementInterditException, ValeurInitialeModificationException {
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
  public void testIsPossible() throws HorsBornesException, ElementInterditException, ValeurImpossibleException, ValeurInitialeModificationException {
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
  public void testElementInterdit() {
    // Test élément interdit
    ElementDeGrille elementInterdit = new ElementDeGrilleImplAsChar('X');
    assertThrows(ElementInterditException.class, () -> grille.isPossible(0, 0, elementInterdit));

    // Test hors bornes
    assertThrows(HorsBornesException.class, () -> grille.isPossible(-1, 0, e1));
  }

  @Test
  public void testIsComplete() throws HorsBornesException, ValeurImpossibleException, ElementInterditException, ValeurInitialeModificationException {
    // Test grille vide
    assertFalse(grille.isComplete());

    // Test grille partiellement remplie
    grille.setValue(0, 0, e1);
    assertFalse(grille.isComplete());

    // Test grille complète
    assertTrue(grille.isComplete());
  }

}
