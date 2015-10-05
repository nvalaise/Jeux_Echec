/**
 * Created by nicolas on 04/07/15.
 */
public class Case {
    private int x;
    private int y;
    private int id;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = -1;
    }
    public Case(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getY() {return y;}

    public int getX() {return x;}
    public int getId() {return id;}
}
