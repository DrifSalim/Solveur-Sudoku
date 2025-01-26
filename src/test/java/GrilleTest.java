import emiage.c306.sudoku.Grille;
import emiage.c306.sudoku.ElementDeGrille;
import emiage.c306.sudoku.ValeurImpossibleException;
import emiage.c306.sudoku.HorsBornesException;
import emiage.c306.sudoku.ElementInterditException;
import emiage.c306.sudoku.ValeurInitialeModificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;


/**
 * Class de tests de l'implémentation de la Grille.
 */
public class GrilleTest {

    /**
     * Setup method.
     */
    @BeforeEach
    public void setUp() {
    }
    /**
     * Test grid dimension grille 4x4.
     * @throws Exception si le parseur echoue
     */
    @Test
    public void testDimensionGrille4x4() throws Exception {
      final int quatre = 4;
        Grille grille = read("/grilles/sudoku4.txt");
        Assertions.assertEquals(quatre, grille.getDimension());
    }
    /**
     * Test initialisation avec une grille incorrecte.
     * @throws Exception si exception
     */
    @Test
    public void testConstructionGrilleIncorrecte() throws Exception {
        Assertions.assertThrows(ValeurImpossibleException.class, () ->
                read("/grilles/sudoku4 invalid.txt"));
        Assertions.assertThrows(ValeurImpossibleException.class, () ->
                read("/grilles/sudoku4 invalid copy.txt"));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                read("/grilles/sudoku3 invalid.txt"));
    }
    /**
     * Methode lecture d'une grille.
     * @param path chemin du fichier à lire.
     * @throws Exception si le parser echoue.
     * @return la lecture de la grille
     */
    private Grille read(final String path) throws Exception {
        try (InputStream in = GrilleTest.class.getResourceAsStream(path)) {
            Assertions.assertNotNull(in);
            return GrilleParser.parse(in);
        }
    }
    /**
     * Test grille dimension d'un fichier donnée.
     * @throws Exception si le parser echoue.
     */
    @Test
    public void testGetDimensionAndElementsFromResource4x4() throws Exception {
      final int quatre = 4;
        Grille grille = read("/grilles/sudoku4.txt");
        Assertions.assertEquals(quatre, grille.getDimension());
        Assertions.assertEquals(quatre, grille.getElements().size());
    }
    /**
     * Test grille dimension et les element.
     * @throws Exception si le parser echoue.
     */
    @Test
    public void testGetDimensionAndElementsFromResource9x9() throws Exception {
      final int neuf = 9;
        Grille grille = read("/grilles/sudoku9vide.txt");
        Assertions.assertEquals(neuf, grille.getDimension());
        Assertions.assertEquals(neuf, grille.getElements().size());
    }
  /**
   * Test getValue des cases vide d'une grille 4x4.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testGetValueCaseVide4x4() throws Exception {
    final int zero = 0;
    final int un = 1;
    final int deux = 2;
    final int trois = 3;
    Grille grille = read("/grilles/sudoku4.txt");
    Assertions.assertNull(grille.getValue(zero, trois));
    Assertions.assertNull(grille.getValue(deux, un));
  }
  /**
   * Test getValue des cases vide d'une grille 9x9.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testGetValueCaseVide9x9() throws Exception {
    final int zero = 0;
    Grille grille = read("/grilles/sudoku9vide.txt");
    // Test case vide
    Assertions.assertNull(grille.getValue(zero, zero));
    Assertions.assertNull(grille.getValue(zero, zero));
  }
  /**
   * Test getValue Hors Bornes.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testGetValueHorsBornes9x9() throws Exception {
    final int outOfBoundsNegative = -1;
    final int zero = 0;
    final int outOfBounds10 = 10;
    Grille grille = read("/grilles/sudoku9vide.txt");
    // Test coordonnées hors bornes
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.getValue(outOfBoundsNegative, zero));
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.getValue(zero, outOfBoundsNegative));
    Assertions.assertThrows(HorsBornesException.class, () ->
                grille.getValue(outOfBounds10, zero));
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.getValue(zero, outOfBounds10));
  }
  /**
   * Test getValue Hors Bornes.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testGetValueHorsBornes4x4() throws Exception {
    final int outOfBoundsNegative = -1;
    final int zero = 0;
    final int outOfBounds5 = 5;
    Grille grille = read("/grilles/sudoku4.txt");
    // Test coordonnées hors bornes
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.getValue(outOfBoundsNegative, zero));
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.getValue(zero, outOfBoundsNegative));
    Assertions.assertThrows(HorsBornesException.class, () ->
                grille.getValue(outOfBounds5, zero));
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.getValue(zero, outOfBounds5));
  }
  /**
   * Test getValue des valeurs initiales d'un fichier donnée.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testGetValueCorrecteFromRessource4x4() throws Exception {
    final int trois = 3;
    Grille grille2 = read("/grilles/sudoku4.txt");
    ElementDeGrille elt = new ElementDeGrilleImplAsChar('2');
    Assertions.assertEquals(elt, grille2.getValue(0, 0));
    ElementDeGrille elt2 = new ElementDeGrilleImplAsChar('3');
    Assertions.assertEquals(elt2, grille2.getValue(1, trois));
  }
  /**
   * Test set & getValue.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testSetAndGetValue() throws Exception {
    Grille grille = read("/grilles/sudoku9vide.txt");
    ElementDeGrille elementDeGrille = grille.getElements().iterator().next();
    // Test insertion normale
    grille.setValue(0, 0, elementDeGrille);
    Assertions.assertEquals(elementDeGrille, grille.getValue(0, 0));
    // Test avec null (vider une case)
    grille.setValue(0, 0, null);
    Assertions.assertNull(grille.getValue(0, 0));
    //réafecter la valeur
    grille.setValue(0, 0, elementDeGrille);
    Assertions.assertEquals(elementDeGrille, grille.getValue(0, 0));
    // Test valeur impossible (ajout d'une même valeur sur la même ligne)
    Assertions.assertThrows(ValeurImpossibleException.class, () ->
                  grille.setValue(0, 1, elementDeGrille));
    // Test valeur impossible (ajout d'une même valeur sur la même colonne)
    Assertions.assertThrows(ValeurImpossibleException.class, () ->
                  grille.setValue(2, 0, elementDeGrille));
    // Test valeur impossible (ajout d'une même valeur sur le carré)
    Assertions.assertThrows(ValeurImpossibleException.class, () ->
                  grille.setValue(1, 1, elementDeGrille));
  }
  /**
   * Test setValue avec des valeurs déja existantes.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void setValueDejaExistante() throws Exception {
    final int trois = 3;
    Grille grille = read("/grilles/sudoku4.txt");
    //Récuperer une valeur de la grille
    ElementDeGrille elementDeGrille = grille.getValue(0, 0);
    Assertions.assertNotNull(elementDeGrille);
    // Test valeur impossible (ajout d'une même valeur sur la même ligne)
    Assertions.assertThrows(ValeurImpossibleException.class, () ->
                  grille.setValue(0, 1, elementDeGrille));
    // Test valeur impossible (ajout d'une même valeur sur la même colonne)
    Assertions.assertThrows(ValeurImpossibleException.class, () ->
                  grille.setValue(trois, 0, elementDeGrille));
    // Test valeur impossible (ajout d'une même valeur sur le carré)
    Assertions.assertThrows(ValeurImpossibleException.class, () ->
                  grille.setValue(1, 1, elementDeGrille));
  }
  /**
   * Test setValue Hors Bornes.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void setValueHorsBornes() throws Exception {
    final int outOfBounds10 = 10;
    final int outOfBounds12 = 12;

    Grille grille = read("/grilles/sudoku9vide.txt");
    ElementDeGrille e1 = grille.getElements().iterator().next();
    // Test exceptions hors bornes
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.setValue(outOfBounds10, 2, e1));
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.setValue(2, outOfBounds12, e1));
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.setValue(-1, 2, e1));
    Assertions.assertThrows(HorsBornesException.class, () ->
                            grille.setValue(2, -1, e1));
  }
  /**
   * Test setValue Avec des elements interdits.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void setValueElementInterdit() throws Exception {
    Grille grille = read("/grilles/sudoku9vide.txt");
    // Test élément interdit
    ElementDeGrille elementInterdit = new ElementDeGrilleImplAsChar('X');
    Assertions.assertThrows(ElementInterditException.class, () ->
                  grille.setValue(0, 0, elementInterdit));
    //Test avec un élément non existant
    Grille grille2 = read("/grilles/sudoku4.txt");
    ElementDeGrille elementInterdit2 = new ElementDeGrilleImplAsChar('6');
    Assertions.assertThrows(ElementInterditException.class, () ->
                  grille2.setValue(0, 1, elementInterdit2));
  }
  /**
   * Test setValue pour modifier une valeur initiale.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void setValueInitiale() throws Exception {
    Grille grille = read("/grilles/sudoku4.txt");
    ElementDeGrille element = new ElementDeGrilleImplAsChar('5');
    Assertions.assertThrows(ValeurInitialeModificationException.class, () ->
                  grille.setValue(0, 0, element));
    //test vider une valeur initial
    Assertions.assertThrows(ValeurInitialeModificationException.class, () ->
                  grille.setValue(0, 0, null));
  }
  /**
   * Test isPossible.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testIsPossible() throws Exception {
    final int trois = 3;
    Grille grille = read("/grilles/sudoku9vide.txt");
    ElementDeGrille e1 = grille.getElements().iterator().next();
    // Test case vide
    Assertions.assertTrue(grille.isPossible(0, 0, e1));
    // Test conflit sur ligne
    //on ajoute l'element à la grille
    grille.setValue(0, 0, e1);
    //on teste le conflit sur la ligne
    Assertions.assertFalse(grille.isPossible(0, trois, e1));
    // Test conflit sur colonne
    Assertions.assertFalse(grille.isPossible(trois, 0, e1));
    // Test conflit dans le carré
    Assertions.assertFalse(grille.isPossible(1, 1, e1));
    Assertions.assertFalse(grille.isPossible(2, 2, e1));
    Assertions.assertTrue(grille.isPossible(trois, 2, e1));
    grille.setValue(trois, 2, e1); // Définir une valeur normale
    // Vérifier que la valeur est bien définie
    Assertions.assertEquals(e1, grille.getValue(trois, 2));
    // Tenter de définir une valeur null
    Assertions.assertTrue(grille.isPossible(trois, 2, null));
    grille.setValue(trois, 2, null); // Définir une valeur null
    // Vérifier que la valeur est null
    Assertions.assertNull(grille.getValue(trois, 2));
  }
  /**
   * Test isPossible modifier valeur initial.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testIsPossibleValeurInitial() throws Exception {
    final int trois = 3;
    Grille grille = read("/grilles/sudoku4.txt");
    ElementDeGrille elt = new ElementDeGrilleImplAsChar('5');
    Assertions.assertThrows(ElementInterditException.class,
                  () -> grille.isPossible(0, 0, null));
    Assertions.assertThrows(ElementInterditException.class,
                  () -> grille.isPossible(2, trois, null));
    Assertions.assertThrows(ElementInterditException.class,
                  () -> grille.isPossible(2, trois, elt));
  }
  /**
   * Test isPossible Hors Bornes.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testisPossibleHorsBornes() throws Exception {
    final int outOfBounds10 = 10;
    final int outOfBounds12 = 12;
    final int outOfBoundsNegative = -1;
    Grille grille = read("/grilles/sudoku9vide.txt");
    ElementDeGrille e1 = grille.getElements().iterator().next();
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.isPossible(outOfBounds10, 2, e1));
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.isPossible(2, outOfBounds12, e1));
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.isPossible(outOfBoundsNegative, 2, e1));
    Assertions.assertThrows(HorsBornesException.class, () ->
                  grille.isPossible(2, outOfBoundsNegative, e1));
  }
  /**
   * Test isPossible Avec des elements interdits.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testisPossibleElementInterdit() throws Exception {
    Grille grille = read("/grilles/sudoku9vide.txt");
    // Test élément interdit
    ElementDeGrille elementInterdit = new ElementDeGrilleImplAsChar('X');
    Assertions.assertThrows(ElementInterditException.class, () ->
                  grille.isPossible(0, 0, elementInterdit));
    Grille grille2 = read("/grilles/sudoku4.txt");
    ElementDeGrille elementInterdit2 = new ElementDeGrilleImplAsChar('5');
    Assertions.assertThrows(ElementInterditException.class, () ->
                  grille2.isPossible(0, 0, elementInterdit2));
  }
  /**
   * Test isComplete avec une grille vide.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testIsCompleteGrilleVide() throws Exception {
    Grille grille = read("/grilles/sudoku9vide.txt");
   // Test grille vide
    Assertions.assertFalse(grille.isComplete());
  }
  /**
   * Test isComplete Avec une grille partielement complete.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testIsCompleteGrillePartiellementComplete() throws Exception {
    Grille grille = read("/grilles/sudoku4-cp.txt");
   // Test grille partiellement remplie
    Assertions.assertFalse(grille.isComplete());
  }
  /**
   * Test isComplete grille complete.
   * @throws Exception si le parser echoue.
   */
  @Test
  public void testIsComplete() throws Exception {
    Grille grille = read("/grilles/sudoku4-complete.txt");
    // Test grille complète
    Assertions.assertTrue(grille.isComplete());
  }
}
