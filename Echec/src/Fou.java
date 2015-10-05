import java.util.ArrayList;

/**
 * Created by nicolas on 04/07/15.
 */

public class Fou extends Piece{
    public Fou(int x, int y){
        super(x,y);
        this.trouverPossibilites();
    }

    @Override
    public void trouverPossibilites(){
        choix.clear();

        for(int i = 1; i < Constantes.CASE_NOMBRE ; i ++){
            //diagonale haut/gauche -> bas/droite
            if(x+i < Constantes.CASE_NOMBRE && y+i < Constantes.CASE_NOMBRE) {
                Case c1 = new Case(x + i, y + i);
                choix.add(c1);
            }
            if(x-i >= 0 && y-i >= 0) {
                Case c2 = new Case(x - i, y - i);
                choix.add(c2);
            }


            //diagonale haut/droit -> bas/gauche
            if(x-i >= 0 && y+i < Constantes.CASE_NOMBRE ) {
                Case c3 = new Case(x - i, y + i);
                choix.add(c3);
            }
            if(x+i < Constantes.CASE_NOMBRE && y-i >= 0) {
                Case c4 = new Case(x + i, y - i);
                choix.add(c4);
            }
        }
    }
}