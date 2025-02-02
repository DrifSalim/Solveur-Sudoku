import emiage.c306.sudoku.ElementDeGrille;
import emiage.c306.sudoku.ElementInterditException;
import emiage.c306.sudoku.Grille;
import emiage.c306.sudoku.HorsBornesException;
import emiage.c306.sudoku.Solveur;
import emiage.c306.sudoku.SolveurException;
import emiage.c306.sudoku.ValeurImpossibleException;
import emiage.c306.sudoku.ValeurInitialeModificationException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public final class SolveurIntelligentImp implements Solveur {

    @Override
    public boolean solve(final Grille grille) throws SolveurException {
        try {
            PriorityQueue<Cell> queue = initializeQueue(grille);
            return backtrack(grille, queue);
        } catch (HorsBornesException | ValeurImpossibleException
        | ElementInterditException | ValeurInitialeModificationException e) {
            throw new SolveurException(e);
        }
    }

    private boolean backtrack(final Grille grille,
    final PriorityQueue<Cell> queue) throws HorsBornesException,
    ValeurImpossibleException, ElementInterditException,
    ValeurInitialeModificationException {
        if (queue.isEmpty()) {
            return grille.isComplete();
        }

        Cell cell = queue.poll();

        for (ElementDeGrille value : cell.possibleValues) {
            if (grille.isPossible(cell.row, cell.col, value)) {
                grille.setValue(cell.row, cell.col, value);

                PriorityQueue<Cell> updatedQueue =
                updateQueue(grille, new PriorityQueue<>(queue), cell);

                if (backtrack(grille, updatedQueue)) {
                    return true;
                }

                grille.setValue(cell.row, cell.col, null);
            }
        }

        queue.add(cell);
        return false;
    }

    private PriorityQueue<Cell> initializeQueue(final Grille grille)
    throws HorsBornesException {
        PriorityQueue<Cell> queue = new
        PriorityQueue<>(Comparator.comparingInt(c -> c.possibleValues.size()));
        for (int i = 0; i < grille.getDimension(); i++) {
            for (int j = 0; j < grille.getDimension(); j++) {
                if (grille.getValue(i, j) == null) {
                    queue.add(new Cell(i, j, getPossibleValues(grille, i, j)));
                }
            }
        }
        return queue;
    }

    private PriorityQueue<Cell> updateQueue(final Grille grille,
    final PriorityQueue<Cell> queue, final Cell currentCell)
    throws HorsBornesException {
        List<Cell> updatedCells = new ArrayList<>();

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            if (cell.row == currentCell.row || cell.col == currentCell.col
                || isInSameSubgrid(grille, cell, currentCell)) {
                cell.possibleValues = getPossibleValues(grille, cell.row,
                                        cell.col);
            }
            updatedCells.add(cell);
        }

        // Sort cells by the number of possible values
        updatedCells.sort(Comparator.comparingInt(c ->
                            c.possibleValues.size()));

        // Add sorted cells back to the queue
        queue.addAll(updatedCells);

        return queue;
    }

    private boolean isInSameSubgrid(final Grille grille, final Cell cell1,
    final Cell cell2) {
        int subGridSize = (int) Math.sqrt(grille.getDimension());
        return (cell1.row / subGridSize == cell2.row / subGridSize)
        && (cell1.col / subGridSize == cell2.col / subGridSize);
    }

    private Set<ElementDeGrille> getPossibleValues(final Grille grille,
    final int row, final int col) throws HorsBornesException {
        Set<ElementDeGrille> possibleValues = new
                            HashSet<>(grille.getElements());
        int dimension = grille.getDimension();
        int subGridSize = (int) Math.sqrt(dimension);

        // Remove values present in the same row and column
        for (int i = 0; i < dimension; i++) {
            possibleValues.remove(grille.getValue(row, i));
            possibleValues.remove(grille.getValue(i, col));
        }

        // Remove values present in the same subgrid
        int subGridRowStart = row / subGridSize * subGridSize;
        int subGridColStart = col / subGridSize * subGridSize;
        for (int i = subGridRowStart; i < subGridRowStart + subGridSize; i++) {
            for (int j = subGridColStart; j < subGridColStart
                    + subGridSize; j++) {
                possibleValues.remove(grille.getValue(i, j));
            }
        }

        return possibleValues;
    }

    private static class Cell {
        /**
         * Position x.
         */
        private int row;
        /**
         * Position y.
         */
        private int col;
        /**
         * Valeurs possibles dans une colonne.
         */
        private Set<ElementDeGrille> possibleValues;

        Cell(final int r, final int c, final Set<ElementDeGrille> possibleVal) {
            this.row = r;
            this.col = c;
            this.possibleValues = possibleVal;
        }
    }
}

