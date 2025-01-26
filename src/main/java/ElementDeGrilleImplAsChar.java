import emiage.c306.sudoku.ElementDeGrille;

public final class ElementDeGrilleImplAsChar implements ElementDeGrille {
    /**
     * charactère c.
     */
    private char c;
    /**
     * Constructeur.
     * @param x caractère.
     */
    public ElementDeGrilleImplAsChar(final char x) {
        this.c = x;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) { // Vérifie si les objets sont identiques
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            // Vérifie nullité et type
            return false;
        }
        ElementDeGrilleImplAsChar other = (ElementDeGrilleImplAsChar) obj;
        return this.c == other.c; // Compare les valeurs
    }

    @Override
    public int hashCode() {
        return (int) c;
    }
    @Override
    public String toString() {
        return String.valueOf(c);
    }
}
