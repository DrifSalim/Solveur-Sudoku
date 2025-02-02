import emiage.c306.sudoku.Grille;
import emiage.c306.sudoku.ElementDeGrille;
import emiage.c306.sudoku.HorsBornesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
public class TestSolveurIntelligent {
    /**
     * créer le solveur.
     */
    private SolveurIntelligentImp solveur;
    /**
     * Instance du solveur avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        solveur = new SolveurIntelligentImp();
    }
    /**
     * Methode lecture d'une grille.
     * @param path chemin du fichier à lire.
     * @throws Exception si le parser echoue.
     * @return la lecture de la grille
     */
    private Grille read(final String path) throws Exception {
        try (InputStream in = GrilleTest.class.getResourceAsStream(path)) {
            assertNotNull(in);
            return GrilleParser.parse(in);
        }
    }
/**
     * Méthode pour afficher la grille.
     * @param grille la grille à afficher
     * @throws HorsBornesException si les coordonnées sont hors bornes
     */
    private void afficherGrille(final Grille grille) throws
    HorsBornesException {
        int dimension = grille.getDimension();
        System.out.println("\nGrille résolue " + dimension + "x"
                            + dimension + ":");
        System.out.println("-".repeat(dimension * 2 + 1));
        for (int i = 0; i < dimension; i++) {
            System.out.print("|");
            for (int j = 0; j < dimension; j++) {
                ElementDeGrille valeur = grille.getValue(i, j);
                if (valeur != null) {
                    System.out.print(valeur.toString());
                } else {
                    System.out.print(" ");
                }
                System.out.print("|");
            }
            System.out.println("\n" + "-".repeat(dimension * 2 + 1));
        }
        System.out.println();
    }
    /**
     * Methode de test de résolution de grille 9x9 vide.
     * @throws Exception
     */
    @Test
    public void testGrille16x16() throws Exception {
        Grille grille = read("/grilles/sudoku16-debutant.txt");
        assertTrue(solveur.solve(grille));
        afficherGrille(grille);
    }
    /**
     * Methode de test de résolution de grille 9x9 vide.
     * @throws Exception
     */
    @Test
    public void testGrillePresqueComplete16x16() throws Exception {
        Grille grille = read("/grilles/sudoku16-pc.txt");
        assertTrue(solveur.solve(grille));
        afficherGrille(grille);
    }
    /**
     * Methode de test de résolution de grille 9x9 vide.
     * @throws Exception
     */
    @Test
    public void testGrille25x25() throws Exception {
        Grille grille = read("/grilles/sudoku25.txt");
        assertTrue(solveur.solve(grille));
        afficherGrille(grille);
    }
}
