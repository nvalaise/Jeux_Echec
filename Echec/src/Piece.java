import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by nicolas on 04/07/15.
 */
public abstract class Piece {
    protected int x;
    protected int y;
    protected ArrayList<Case> choix;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;

        choix = new ArrayList<>();
    }

    public abstract void trouverPossibilites();

    public ArrayList<Case> getChoix() {
        return choix;
    }

    public boolean peutEtreDeplace(int x, int y){
        for(int i = 0; i<choix.size(); i++){
            if (choix.get(i).getX() == x && choix.get(i).getY() == y)
                return true;
        }
        return false;
    }

    public void deplacer(int x, int y){
        this.x = x;
        this.y = y;

        choix.clear();
        this.trouverPossibilites();
    }

    public boolean deplacementContient(int x, int y){
        for(int i = 0; i<choix.size(); i++){
            if(choix.get(i).getX() == x && choix.get(i).getY() == y)
                return true;
        }
        return false;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
