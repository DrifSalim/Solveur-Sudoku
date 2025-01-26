import emiage.c306.sudoku.ElementDeGrille;
import emiage.c306.sudoku.ElementInterditException;
import emiage.c306.sudoku.Grille;
import emiage.c306.sudoku.HorsBornesException;
import emiage.c306.sudoku.ValeurImpossibleException;
import emiage.c306.sudoku.ValeurInitialeModificationException;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation de l'interce Grille Sudoku .
 */
public final class GrilleImpl implements Grille {
    /** Tableu représentant la grille sudoku. */
    private final ElementDeGrille[][] grille;
    /** tableau de boolean pour les valeurs initial. */
    private final boolean[][] valeursInitiales;
    /** Tableau des élements de la grille. */
    private final ElementDeGrille[] elements;
    /** Dimension de la grille. */
    private final int dimension;
    /**
     * Constructeur pour créer une grille Sudoku avec les elements.
     * @param elementDeGrilles tableau des élements de la geille
     * @throws IllegalArgumentException si les élements ne sont pas valides.
     */
    public GrilleImpl(final ElementDeGrille[] elementDeGrilles)
            throws IllegalArgumentException {
        this.elements = elementDeGrilles.clone();
        this.dimension = elementDeGrilles.length;
        this.grille = new ElementDeGrille[dimension][dimension];
        this.valeursInitiales = new boolean[dimension][dimension];
    }
    /**
     * Constructeur pour creer une grille Sudoku avec des valeurs initial.
     * @param elementDeGrilles tableau des elements de la grille
     * @param grilleTab tableau des valeurs initiales
     * @throws HorsBornesException si les coordonnées sont incorrectes
     * @throws ElementInterditException si l'élement n'est pas valide
     * @throws ValeurImpossibleException si valeur impossible
     */
    public GrilleImpl(
        final ElementDeGrille[] elementDeGrilles,
        final ElementDeGrille[][] grilleTab)
    throws HorsBornesException, ElementInterditException,
    ValeurImpossibleException {
        this(elementDeGrilles);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.isPossible(i, j, grilleTab[i][j])) {
                    this.grille[i][j] = grilleTab[i][j];
                    this.valeursInitiales[i][j] = (grilleTab[i][j] != null);
                } else {
                    throw new ValeurImpossibleException("Valeur impossible");
                }
            }
        }
    }
    @Override
    public int getDimension() {
        return this.dimension;
    }
    @Override
    public boolean isPossible(
        final int x,
        final int y,
        final ElementDeGrille value
    ) throws HorsBornesException, ElementInterditException {
        // Vérification des bornes
        if (x < 0 || x >= dimension || y < 0 || y >= dimension) {
            throw new HorsBornesException(
                String.format("Hors bornes exception: x=%d y=%d", x, y)
            );
        }
        if (value == null && !this.valeursInitiales[x][y]) {
            return true;
        }
        // Vérification de l'élément
        if (!getElements().contains(value)) {
            throw new ElementInterditException(
                "ElementInterditException exception: " + value
            );
        }
        // Vérification ligne
        for (int i = 0; i < this.getDimension(); i++) {
            if (grille[x][i] != null && grille[x][i].equals(value)) {
                return false;
            }
        }
        // Vérification colonne
        for (int i = 0; i < dimension; i++) {
            if (grille[i][y] != null && grille[i][y].equals(value)) {
                return false;
            }
        }
        // Vérification carré
        int squareSize = (int) Math.sqrt(dimension);
        int startX = x / squareSize * squareSize;
        int startY = y / squareSize * squareSize;
        for (int i = startX; i < startX + squareSize; i++) {
            for (int j = startY; j < startY + squareSize; j++) {
                if (grille[i][j] != null && grille[i][j].equals(value)) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public ElementDeGrille getValue(final int x, final int y)
            throws HorsBornesException {
        if (x < 0 || x >= dimension || y < 0 || y >= dimension) {
            throw new HorsBornesException("Valeur impossible");
        }
        return grille[x][y];
    }
    @Override
    public Set<ElementDeGrille> getElements() {
        Set<ElementDeGrille> elementSet = new HashSet<>();
        for (ElementDeGrille element : elements) {
            elementSet.add(element);
        }
        return elementSet;
    }
    @Override
    public boolean isComplete() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (grille[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public boolean isValeurInitiale(final int x, final int y) {
        return valeursInitiales[x][y];
    }
    @Override
    public void setValue(
        final int x,
        final int y,
        final ElementDeGrille value
    ) throws HorsBornesException, ValeurImpossibleException,
             ElementInterditException, ValeurInitialeModificationException {
        if (x < 0 || x >= dimension || y < 0 || y >= dimension) {
            throw new HorsBornesException("erreur hors bornes");
        }
        if (isValeurInitiale(x, y)) {
            throw new
            ValeurInitialeModificationException("Valeur initial erreur");
        }
        if (value != null) {
            if (!getElements().contains(value)) {
                throw new ElementInterditException("Element interdit erreur");
            }
            if (!isPossible(x, y, value)) {
                throw new ValeurImpossibleException(
                    String.format(
                        "Erreur valeur impossible: %s position %d %d",
                        value, x, y
                    )
                );
            }
        }
        grille[x][y] = value;
    }
}
