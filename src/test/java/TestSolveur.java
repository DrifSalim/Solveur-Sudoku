import emiage.c306.sudoku.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
public class TestSolveur {
    private Grille read(String path) throws Exception {
        try (InputStream in = GrilleTest.class.getResourceAsStream(path)) {
          assertNotNull(in);
          return GrilleParser.parse(in);
        }
      }
    @Test
    public void testSolveur() throws Exception {
    Grille grille = read("/grilles/sudoku9vide.txt");
    assertNotNull(grille);
    SudokuSolver solver = new SudokuSolver();
    assertTrue(solver.solve(grille));
    assertTrue(grille.isComplete());

    // Afficher la grille résolue (optionnel)
    for (int i = 0; i < grille.getDimension(); i++) {
        for (int j = 0; j < grille.getDimension(); j++) {
            System.out.print(grille.getValue(i, j) + " ");
        }
        System.out.println();
    }
}

}
