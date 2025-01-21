import emiage.c306.sudoku.ElementDeGrille;
import emiage.c306.sudoku.ElementInterditException;
import emiage.c306.sudoku.Grille;
import emiage.c306.sudoku.HorsBornesException;
import emiage.c306.sudoku.ValeurImpossibleException;
import emiage.c306.sudoku.ValeurInitialeModificationException;

public class SudokuSolver {

    public boolean solve(Grille grille) throws HorsBornesException, ValeurImpossibleException, 
        ElementInterditException, ValeurInitialeModificationException {
        // Trouver une case vide
        for (int i = 0; i < grille.getDimension(); i++) {
            for (int j = 0; j < grille.getDimension(); j++) {
                if (grille.getValue(i, j) == null) {
                    // Essayer chaque valeur possible
                    for (ElementDeGrille value : grille.getElements()) {
                        if (grille.isPossible(i, j, value)) {
                            grille.setValue(i, j, value);

                            // Recurser pour résoudre le reste de la grille
                            if (solve(grille)) {
                                return true;
                            }

                            // Si cela ne fonctionne pas, revenir en arrière
                            grille.setValue(i, j, null);
                        }
                    }
                    return false; // Si aucune valeur ne fonctionne
                }
            }
        }

        // Si aucune case vide n'est trouvée, c'est que la grille est complète
        return grille.isComplete();
    }
}
