import emiage.c306.sudoku.ElementDeGrille;

public class ElementDeGrilleImplAsChar implements ElementDeGrille{
    char c;
    public ElementDeGrilleImplAsChar(char x) {
        this.c = x;
    }

    @Override
    public boolean equals(Object obj){
        if(this.c == ((ElementDeGrilleImplAsChar) obj).c){
            return true;
        }
        return false;
    }
    public int hashCode(){
        return (int) c;
    }
    public String toString(){
        return " "+this.c;
    }
}
