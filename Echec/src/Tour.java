/**
 * Created by nicolas on 04/07/15.
 */
public class Tour extends Piece{
    private boolean roque;

    public Tour(int x, int y){
        super(x,y);
        this.trouverPossibilites();

        this.roque = true;
    }

    public boolean peutRoque(){
        return this.roque;
    }

    @Override
    public void deplacer(int x, int y){
        if(x != this.x || this.y != y)
            this.roque = false;

        this.x = x;
        this.y = y;

        choix.clear();
        this.trouverPossibilites();
    }

    @Override
    public void trouverPossibilites(){
        choix.clear();

        for(int i = 0; i < Constantes.CASE_NOMBRE ; i ++){
            if(i != this.y) {
                Case c1 = new Case(x, i);
                choix.add(c1);
            }
            if(i != this.x) {
                Case c2 = new Case(i, y);
                choix.add(c2);
            }
        }
    }
}