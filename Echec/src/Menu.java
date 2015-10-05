import org.newdawn.slick.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by nicolas on 07/07/15.
*/

public class Menu extends BasicGameState {
    private Image fond;

    public Menu(int state){

    }

    public void init(GameContainer gc, StateBasedGame stbg) throws SlickException {
        fond = new Image("res/echec.jpg");
    }

    public void render(GameContainer gc, StateBasedGame stbg, Graphics g) throws SlickException{
        fond.draw(0,0,900,670);
        g.setColor(Color.blue);
        g.drawString("Appuyer sur entrer pour jouer", 50,100);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
       Input input = gc.getInput();
        if(input.isKeyDown(Input.KEY_ENTER)){
            sbg.enterState(1);
        }
    }

    public int getID(){
        return 0;
    }
}
