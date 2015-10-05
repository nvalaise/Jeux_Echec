import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


/****
 * Début de la configuration
 * du projet.
 * Reprendre vidéo 4
 ***/

public class Main extends StateBasedGame{

    public static final String gameName = "Jeux d'echec";
    public static final int menu = 0;
    public static final int jeux = 1;

    public Main(String gameName){
        super(gameName);
        this.addState(new Menu(menu));
        this.addState(new Jeux(jeux));
    }

    public void initStatesList(GameContainer gc) throws SlickException{
        this.getState(menu).init(gc, this);
        this.getState(jeux).init(gc, this);
        this.enterState(menu);
    }

    public static void main(String[] args){
        AppGameContainer appgc;

        try{
            appgc = new AppGameContainer(new Main(gameName));
            appgc.setDisplayMode(900,670, false);
            appgc.setShowFPS(false);
            appgc.start();
        } catch (SlickException e){
            e.printStackTrace();
        }

    }
}


