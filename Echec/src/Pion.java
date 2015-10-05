import java.util.ArrayList;

/**
 * Created by nicolas on 04/07/15.
 */

public class Pion extends Piece{
    private boolean blanc;
    private ArrayList<Case> manger;

    private boolean peutEtrePrisEnPassant;

    public Pion(boolean b, int x, int y){
        super(x,y);
        this.blanc = b;
        manger = new ArrayList<Case>();
        this.trouverPossibilites();

        this.peutEtrePrisEnPassant = false;
    }

    public ArrayList<Case> getManger() {return manger;}

    @Override
    public void deplacer(int x, int y){
        if(this.blanc){
            if(y == this.y + 2)
                this.peutEtrePrisEnPassant = true;
            else
                this.peutEtrePrisEnPassant = false;

        } else {
            if(y == this.y - 2)
                this.peutEtrePrisEnPassant = true;
            else
                this.peutEtrePrisEnPassant = false;
        }

        this.x = x;
        this.y = y;

        choix.clear();
        this.trouverPossibilites();
    }

    public boolean peutEtreDeplace(int x, int y){
        for(int i = 0; i<choix.size(); i++){
            if (choix.get(i).getX() == x && choix.get(i).getY() == y)
                return true;
        }
        for(int i = 0; i<manger.size(); i++){
            if (manger.get(i).getX() == x && manger.get(i).getY() == y)
                return true;
        }
        return false;
    }

    @Override
    public void trouverPossibilites(){
        choix.clear();
        manger.clear();

        if(this.blanc) {
            if(y+1 < Constantes.CASE_NOMBRE){
                Case c1 = new Case(x, y+1);
                choix.add(c1);
            }
            if(y+2 < Constantes.CASE_NOMBRE){
                Case c2 = new Case(x, y+2);
                choix.add(c2);
            }

            if(x-1>=0 && y+1<Constantes.CASE_NOMBRE){
                Case m1 = new Case(x-1,y+1);
                manger.add(m1);
            }
            if(x+1<Constantes.CASE_NOMBRE && y+1<Constantes.CASE_NOMBRE){
                Case m2 = new Case(x+1,y+1);
                manger.add(m2);
            }
        }
        else {
            if(y-1>=0) {
                Case c1 = new Case(x,y-1);
                choix.add(c1);
            }
            if(y-2>=0){
                Case c2 = new Case(x,y-2);
                choix.add(c2);
            }

            if(x-1>=0 && y-1>=0){
                Case m1 = new Case(x-1,y-1);
                manger.add(m1);
            }
            if(x+1<Constantes.CASE_NOMBRE && y-1>=0){
                Case m2 = new Case(x+1,y-1);
                manger.add(m2);
            }
        }
    }
}