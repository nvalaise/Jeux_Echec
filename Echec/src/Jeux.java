/**
 * Created by nicolas on 07/07/15.
 */

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.io.Console;

/*****
 * Reste à fare :
 *  - prise en passant
 *  - roque -> OK
 *  - déplacement pion
 *  - algorithme échec et math -> Presque, à revoir
 *  - pion arrivant à la ligne la plus haute
 *
 */

public class Jeux extends BasicGameState {
    private Controleur controleur;
    private Image piece;
    private Case selection;

    private int time;
    private int heure;
    private int minute;
    private int seconde;

    private int marge;
    private boolean cliquer;

    private boolean echec;
    private boolean echecEtMat;

    public Jeux(int state){
    }

    public void init(GameContainer gc, StateBasedGame stbg) throws SlickException {
        controleur = new Controleur();
        piece = new Image("res/sprites.png");
        selection = null;

        marge = 35;
        cliquer = false;

        time = 0;
        heure = 0;
        minute = 0;
        seconde = 0;

        echec = false;
        echecEtMat = false;
    }

    public void render(GameContainer gc, StateBasedGame stbg, Graphics g) throws SlickException{
        g.clear();

        seconde = time/1000 % 60;
        minute = time/60000 % 60;
        heure = time/360000% 60;

        g.setColor(Color.red);
        g.drawString(Integer.toString(heure) + " : " + Integer.toString(minute) + " : " + Integer.toString(seconde), 680,10);

        this.afficheGrille(g);
        switch (controleur.getJoueurActuel()){
            case 1 :
                g.setColor(new Color(204,153,102));
                g.drawString("Joueur or a le trait", 680,40);
                break;
            case 2 :
                g.setColor(Color.white);
                g.drawString("Joueur blanc a le trait", 680,40);
                break;
        }
    }

    public void update(GameContainer gc, StateBasedGame stbg, int delta) throws SlickException{
        time += delta;
        int mouseX = Mouse.getX() - marge;
        int mouseY = Mouse.getY() - marge;

        //clique gauche relaché
        if(!Mouse.isButtonDown(0)) {
            this.cliquer = false;
        }

        if(Mouse.isButtonDown(0)){
            for(int i = 0; i<controleur.getJoueur(controleur.getJoueurActuel()).getMesPieces().size(); i++){
                if((!cliquer) && ((mouseX/75) == controleur.getJoueur(controleur.getJoueurActuel()).getMesPieces().get(i).getX()) && (8-(mouseY/75)-1 == controleur.getJoueur(controleur.getJoueurActuel()).getMesPieces().get(i).getY())){
                    if(selection == null)
                        selection = new Case(mouseX / 75, 8 - (mouseY / 75) - 1, i);
                    else {
                        if((mouseX / 75 == selection.getX()) && (8 - (mouseY / 75) - 1 == selection.getY()))
                            selection = null;
                        else {
                            selection = null;
                            selection = new Case(mouseX / 75, 8 - (mouseY / 75) - 1, i);
                        }
                    }
                    cliquer = true;
                }
            }
            if(selection != null){
                for (int i = 0; i < controleur.afficherPossibilites(selection.getX(), selection.getY()).size(); i++){
                    if(
                            (!cliquer)
                                &&
                            ((mouseX/75) == controleur.afficherPossibilites(selection.getX(), selection.getY()).get(i).getX())
                                &&
                            ((8-(mouseY/75)-1) == controleur.afficherPossibilites(selection.getX(), selection.getY()).get(i).getY())
                        ){
                             cliquer = true;
                            if(controleur.getJoueur(controleur.getJoueurActuel()).getMesPieces().get(selection.getId()).peutEtreDeplace(mouseX/75,8-mouseY/75-1)){
                                controleur.deplacerPiece(selection.getId(), mouseX / 75, 8 - mouseY / 75 - 1);
                                controleur.ecraserPiece(mouseX / 75, 8 - mouseY / 75 - 1);
                                controleur.changerJoueur();
                                selection = null;

                                echecEtMat = false;
                                echec = false;
                                if(controleur.joueurActuelEchec()){
                                    if(controleur.joueurActuelEchecEtMat())
                                        echecEtMat = true;
                                    else
                                        echec = true;
                                }
                            }
                            break;
                    }
                }
            }
        }

    }

    private int getTypeCase(String nom){
        if(nom == "Roi")
            return 1;

        else if(nom == "Dame")
            return 2;

        else if(nom == "Fou")
            return 3;

        else if(nom == "Cavalier")
            return 4;

        else if(nom == "Tour")
            return 5;

        else if(nom == "Pion")
            return 6;

        else
            return 0;
    }

    private void afficheGrille(Graphics g){
        g.setColor(new Color(150, 150, 150));
        g.fillRect(0,0,670,670);

        g.setColor(Color.black);
        g.drawRect(marge-1,marge-1,601,601);
        int mouseX = Mouse.getX() - marge;
        int mouseY = Mouse.getY() - marge;

        g.setColor(Color.black);
        for(int i = 0; i < Constantes.CASE_NOMBRE ; i++){
            g.setColor(Color.black);
            g.drawString(Integer.toString(Constantes.CASE_NOMBRE  - i), 10, i*75 + 2*marge - 10);
            switch (i){
                case 0:
                    g.drawString("A", i*75 + 2*marge - 5, 10);
                    break;
                case 1:
                    g.drawString("B", i*75 + 2*marge - 5, 10);
                    break;
                case 2:
                    g.drawString("C", i*75 + 2*marge - 5, 10);
                    break;
                case 3:
                    g.drawString("D", i*75 + 2*marge - 5, 10);
                    break;
                case 4:
                    g.drawString("E", i*75 + 2*marge - 5, 10);
                    break;
                case 5:
                    g.drawString("F", i*75 + 2*marge - 5, 10);
                    break;
                case 6:
                    g.drawString("G", i*75 + 2*marge - 5, 10);
                    break;
                case 7:
                    g.drawString("H", i*75 + 2*marge - 5, 10);
                    break;
            }
            for(int j = 0; j < Constantes.CASE_NOMBRE ; j++){
                if((i+j) % 2 == 0) {
                    g.setColor(new Color(204,153,102));
                    g.fillRect(i*75 + marge,j*75 + marge,75,75);
                }
                else {
                    g.setColor(new Color(200,200,200));
                    g.fillRect(i*75 + marge,j*75 + marge,75,75);
                }

                //case survolé
                for(int k = 0 ; k < controleur.getJoueur(controleur.getJoueurActuel()).getMesPieces().size(); k++){
                    if(((mouseX/75) == controleur.getJoueur(controleur.getJoueurActuel()).getMesPieces().get(k).getX()) && (8-(mouseY/75)-1 == controleur.getJoueur(controleur.getJoueurActuel()).getMesPieces().get(k).getY())){
                        g.setColor(Color.yellow);
                        g.drawRect((mouseX/75)*75 + marge,(8-(mouseY)/75-1)*75 + marge,75,75);
                        g.drawRect(3+(mouseX/75)*75 + marge,3+(8-(mouseY)/75-1)*75 + marge,69,69);
                    }
                }

                //on affiche les possibilités de la case sélectionné
                if(selection != null){
                    g.setColor(Color.green);
                    g.drawRect(selection.getX()*75 + marge,selection.getY()*75 + marge,75,75);
                    g.drawRect(3+selection.getX()*75 + marge,3+selection.getY()*75 + marge,69,69);
                    for(int k = 0; k < controleur.afficherPossibilites(selection.getX(), selection.getY()).size(); k++){
                        if(this.controleur.deplacementPeutManger(controleur.afficherPossibilites(selection.getX(), selection.getY()).get(k).getX(), controleur.afficherPossibilites(selection.getX(), selection.getY()).get(k).getY()))
                            g.setColor(Color.red);

                        else
                            g.setColor(Color.blue);

                        g.drawRect(controleur.afficherPossibilites(selection.getX(), selection.getY()).get(k).getX()*75 + marge,controleur.afficherPossibilites(selection.getX(), selection.getY()).get(k).getY()*75 + marge,75,75);
                        g.drawRect(3 + controleur.afficherPossibilites(selection.getX(), selection.getY()).get(k).getX() * 75 + marge, 3 + controleur.afficherPossibilites(selection.getX(), selection.getY()).get(k).getY() * 75 + marge, 69, 69);
                    }
                }

                int type = 0;

                //affixchage des cases du joueur 1
                for(int k = 0 ; k < controleur.getJoueur(1).getMesPieces().size(); k++){
                    if(controleur.getJoueur(1).getMesPieces().get(k).getX() == i && controleur.getJoueur(1).getMesPieces().get(k).getY() == j)
                        type = this.getTypeCase(controleur.getJoueur(1).getMesPieces().get(k).getClass().getName());
                }

                //affichage des cases du joueur 2
                for(int k = 0 ; k < controleur.getJoueur(2).getMesPieces().size(); k++){
                    if(controleur.getJoueur(2).getMesPieces().get(k).getX() == i && controleur.getJoueur(2).getMesPieces().get(k).getY() == j)
                        type = this.getTypeCase(controleur.getJoueur(2).getMesPieces().get(k).getClass().getName()) + 6;
                }

                switch (type){
                    case 1: //roi or
                        piece.getSubImage(0,0,64,64).draw(i*75 + 5 + marge, j*75 + 5 + marge);
                        break;

                    case 2: //dame or
                        piece.getSubImage(64,0,64,64).draw(i*75 + 5 + marge, j*75 + 5 + marge);
                        break;

                    case 3: //fou or
                        piece.getSubImage(256,0,64,64).draw(i * 75 + 5 + marge, j * 75 + 5 + marge);

                        break;

                    case 4: //cavalier or
                        piece.getSubImage(192,0,64,64).draw(i * 75 + 5 + marge, j * 75 + 5 + marge);

                        break;

                    case 5: //tour or
                        piece.getSubImage(128,0,64,64).draw(i * 75 + 5 + marge, j * 75 + 5 + marge);

                        break;

                    case 6: //pion or
                        piece.getSubImage(320,0,64,64).draw(i * 75 + 5 + marge, j *75 + 5 + marge);
                        break;


                    case 7: //roi blanc
                        piece.getSubImage(0,64,64,64).draw(i*75 + 5 + marge, j*75 + 5 + marge);
                        break;

                    case 8: //dame blanche
                        piece.getSubImage(64,64,64,64).draw(i*75 + 5 + marge, j*75 + 5 + marge);
                        break;

                    case 9: //fou blanc
                        piece.getSubImage(256,64,64,64).draw(i * 75 + 5 + marge, j * 75 + 5 + marge);
                        break;

                    case 10: //cavalier blanc
                        piece.getSubImage(192,64,64,64).draw(i * 75 + 5 + marge, j * 75 + 5 + marge);
                        break;

                    case 11: //tour blanche
                        piece.getSubImage(128,64,64,64).draw(i * 75 + 5 + marge, j * 75 + 5 + marge);
                        break;

                    case 12: //pion blanc
                        piece.getSubImage(320,64,64,64).draw(i * 75 + 5 + marge, j *75 + 5 + marge);
                        break;
                }
            }
        }
        if(this.echecEtMat){
            g.setColor(Color.red);
            g.drawString("Echec et mat !\nVous avez perdu.", 680,80);
        }
        else if(this.echec){
            g.setColor(Color.red);
            g.drawString("Echec", 680,80);
        }
    }

    public int getID(){
        return 1;
    }
}
