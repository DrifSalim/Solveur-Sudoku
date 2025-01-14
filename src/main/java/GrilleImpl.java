import emiage.c306.sudoku.ElementDeGrille;
import emiage.c306.sudoku.*;
public class GrilleImpl implements Grille{
    public int getDimension(){
        return 0;
    }
    public boolean isPossible(int x, int y, ElementDeGrille value)
    {
        return false;
    }
    public ElementDeGrille getValue(int x, int y) throws HorsBornesException {
        return null;
    }
    public Set<ElementDeGrille> getElements(){
        return null;
    }
    public boolean isComplete(){
        return false;
    }
    public boolean isValeurInitiale(int x, int y){
        return false;
    }
    public void setValue(int x, int y, ElementDeGrille value){

    }
}
