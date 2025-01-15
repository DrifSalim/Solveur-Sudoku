import emiage.c306.sudoku.*;
import java.util.HashSet;
import java.util.Set;

public class GrilleImpl implements Grille {
  private ElementDeGrille[][] grille;
  private boolean[][] valeursInitiales;
  private ElementDeGrille[] elements;
  private final int dimension;

  public GrilleImpl(ElementDeGrille[] elementDeGrilles) {
    this.elements = elementDeGrilles;
    this.dimension = elementDeGrilles.length;
    this.grille = new ElementDeGrille[dimension][dimension];
    this.valeursInitiales = new boolean[dimension][dimension];
  }

  public GrilleImpl(ElementDeGrille[] elementDeGrilles, ElementDeGrille[][] grilleTab) 
  throws HorsBornesException, ElementInterditException, ValeurImpossibleException {
    this(elementDeGrilles);
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if( this.isPossible(i, j, grilleTab[i][j]) ){
          this.grille[i][j] = grilleTab[i][j];
          this.valeursInitiales[i][j] = (grilleTab[i][j] != null);
        }else{
          throw new ValeurImpossibleException("Valeur impossible");
        }
      }
    }
  }

  @Override
  public int getDimension() {
    return dimension;
  }

  @Override
  public boolean isPossible(int x, int y, ElementDeGrille value)
      throws HorsBornesException, ElementInterditException {
    // Vérification des bornes
    if (x < 0 || x >= dimension || y < 0 || y >= dimension) {
      throw new HorsBornesException("Hors borns exception: x="+x+" y="+y);
    }
    if(value==null && this.valeursInitiales[x][y] == false){
         return true;
    }
    // Vérification de l'élément
    if (!getElements().contains(value)) {
      throw new ElementInterditException("ElementInterditException exception: "+value);
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
    int startX = (x / squareSize) * squareSize;
    int startY = (y / squareSize) * squareSize;
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
  public ElementDeGrille getValue(int x, int y) throws HorsBornesException {
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
  public boolean isValeurInitiale(int x, int y) {
    return valeursInitiales[x][y];
  }

  @Override
  public void setValue(int x, int y, ElementDeGrille value)
      throws HorsBornesException, ValeurImpossibleException,
      ElementInterditException, ValeurInitialeModificationException {

    if (x < 0 || x >= dimension || y < 0 || y >= dimension) {
      throw new HorsBornesException("erreur hors bornes");
    }

    if (isValeurInitiale(x, y)) {
      throw new ValeurInitialeModificationException("Valeur initial erreur");
    }

    if (value != null) {
      if (!getElements().contains(value)) {
        throw new ElementInterditException("Element interdit erreur");
      }

      if (!isPossible(x, y, value)) {
        throw new ValeurImpossibleException("Erreur valeur impossible: "+value);
      }
    }

    grille[x][y] = value;
  }
}
