/**
 * Created by nicolas on 04/07/15.
 */
public class Cavalier extends Piece{
    public Cavalier(int x, int y){
        super(x,y);
        this.trouverPossibilites();
    }

    @Override
    public void trouverPossibilites(){
        choix.clear();

        if(x-1>=0 && y-2>=0){
            Case c1 = new Case(x-1,y-2);
            choix.add(c1);
        }
        if(x-2>=0 && y-1>=0){
            Case c2 = new Case(x-2,y-1);
            choix.add(c2);
        }
        if(x+2<Constantes.CASE_NOMBRE && y-1>=0){
            Case c3 = new Case(x+2,y-1);
            choix.add(c3);
        }
        if(x+1<Constantes.CASE_NOMBRE && y-2>=0){
            Case c4 = new Case(x+1,y-2);
            choix.add(c4);
        }
        if(x+1<Constantes.CASE_NOMBRE && y+2<Constantes.CASE_NOMBRE){
            Case c5 = new Case(x+1,y+2);
            choix.add(c5);
        }
        if(x+2<Constantes.CASE_NOMBRE && y+1<Constantes.CASE_NOMBRE){
            Case c6 = new Case(x+2,y+1);
            choix.add(c6);
        }
        if(x-1>=0 && y+2<Constantes.CASE_NOMBRE){
            Case c7 = new Case(x-1,y+2);
            choix.add(c7);
        }
        if(x-2>=0 && y+2<Constantes.CASE_NOMBRE){
            Case c8 = new Case(x-2,y+1);
            choix.add(c8);
        }
    }
}