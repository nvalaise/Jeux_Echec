import java.awt.*;
import java.util.ArrayList;

/**
 * Created by nicolas on 06/07/15.
 */
public class Joueur{
    private ArrayList<Piece> mesPieces;
    private boolean jouer;
    private boolean blanc;
    private static boolean premier = true;

    Joueur(){
        mesPieces = new ArrayList<Piece>();
        if(premier){
            this.jouer = true;
            this.blanc = false;
            this.ajouterPiece();
            this.premier = false;
        }
        else{
            this.jouer = false;
            this.blanc = true;
            this.ajouterPiece();
            this.premier = true;
        }
    }

    private void ajouterPiece(){
        if(this.blanc){
            Tour t1 = new Tour(0,0);
            mesPieces.add(t1);

            Cavalier c1 = new Cavalier(1,0);
            mesPieces.add(c1);

            Fou f1 = new Fou(2,0);
            mesPieces.add(f1);

            Dame d1 = new Dame(3,0);
            mesPieces.add(d1);

            Roi r1 = new Roi(4,0);
            mesPieces.add(r1);

            Fou f2 = new Fou(5,0);
            mesPieces.add(f2);

            Cavalier c2 = new Cavalier(6,0);
            mesPieces.add(c2);

            Tour t2 = new Tour(7,0);
            mesPieces.add(t2);

            for(int i = 0; i<Constantes.CASE_NOMBRE; i++){
                Pion p = new Pion(this.blanc,i,1);
                mesPieces.add(p);
            }
        }
        else{
            Tour t1 = new Tour(0,7);
            mesPieces.add(t1);

            Cavalier c1 = new Cavalier(1,7);
            mesPieces.add(c1);

            Fou f1 = new Fou(2,7);
            mesPieces.add(f1);

            Dame d1 = new Dame(3,7);
            mesPieces.add(d1);

            Roi r1 = new Roi(4,7);
            mesPieces.add(r1);

            Fou f2 = new Fou(5,7);
            mesPieces.add(f2);

            Cavalier c2 = new Cavalier(6,7);
            mesPieces.add(c2);

            Tour t2 = new Tour(7,7);
            mesPieces.add(t2);

            for(int i = 0; i<Constantes.CASE_NOMBRE; i++){
                Pion p = new Pion(this.blanc,i,6);
                mesPieces.add(p);
            }
        }
    }

    public boolean sonTour() { return jouer; }

    public void tourSuivant(){
        if(jouer)
            jouer = false;
        else
            jouer = true;
    }


    public boolean occupeCase(int x, int y){
        for(int i = 0; i < this.mesPieces.size(); i++){
            if(this.mesPieces.get(i).getX() == x && this.mesPieces.get(i).getY() == y)
                return true;
        }
        return false;
    }

    public ArrayList<Piece> getMesPieces(){
        return this.mesPieces;
    }
}
