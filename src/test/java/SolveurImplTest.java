import emiage.c306.sudoku.Grille;
import emiage.c306.sudoku.ElementDeGrille;
import emiage.c306.sudoku.HorsBornesException;
import emiage.c306.sudoku.Solveur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;

public class SolveurImplTest {
    /**
     * créer le solveur.
     */
    private Solveur solveur;
    /**
     * Instance du solveur avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        solveur = new SolveurImpl();
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
    public void testGrilleVide() throws Exception {
        Grille grille = read("/grilles/sudoku9vide.txt");
        assertTrue(solveur.solve(grille));
        assertTrue(grille.isComplete());
        afficherGrille(grille);
    }
    /**
     * Methode de test de resolution de grille partiellement remplie.
     * @throws Exception
     */
    @Test
    public void testGrillePresqueComplete() throws Exception {
        Grille grille = read("/grilles/sudoku4-cp.txt");
        assertTrue(solveur.solve(grille));
        assertTrue(grille.isComplete());
        afficherGrille(grille);
    }
    /**
     * Methode de test pour résolution de grille complete.
     * @throws Exception
     */
    @Test
    public void testGrilleComplete() throws Exception {
        Grille grille = read("/grilles/sudoku4-complete.txt");
        assertTrue(solveur.solve(grille));
        assertTrue(grille.isComplete());
        afficherGrille(grille);
    }
}
