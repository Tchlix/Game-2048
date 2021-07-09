import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.awt.Color;

public class Grid{
    boolean gameEnd = false;
    JLabel[] Grid_Elements;
    Window window;
    Engine engine = new Engine();
    public Grid(Window window){
        this.window = window;
        Grid_Elements = new JLabel[16];
        for (int x = 0;x < Grid_Elements.length;x++){
            Grid_Elements[x] = new JLabel("");
            Grid_Elements[x].setOpaque(true);
            Grid_Elements[x].setBackground(Color.WHITE);
            Grid_Elements[x].setBorder(BorderFactory.createLineBorder(Color.black));
        }
        for (JLabel Grid_Element : Grid_Elements) window.add(Grid_Element);
    }
    public void update(Directions direction){
        if(!gameEnd)
            if(engine.move(direction))
                draw();
        if(engine.max_power_on_grid >= 11) {
            gameEnd = true;
            won();
        }
        if(!engine.can_make_move()) {
            gameEnd = true;
            lost();
        }
    }
    public void draw(){
        for (int i = 0;i < 16;i++) {
            Grid_Elements[i].setBackground(Colors._0.color());
            Grid_Elements[i].setText("");
            if (engine.points_on_grid[i] > 0) {
                Grid_Elements[i].setBackground(which_color(engine.points_on_grid[i]));
                Grid_Elements[i].setText(Integer.toString(engine.points_on_grid[i]));
            }
        }
    }
    private void lost() {
        for (int i = 0; i < 16; i++)
            Grid_Elements[i].setBackground(Color.RED);
    }
    public void reset(){
        engine = new Engine();
        gameEnd = false;
        draw();
    }
    private void won() {
        for (int i = 0; i < 16; i++)
            Grid_Elements[i].setBackground(Color.YELLOW);
    }
    private Color which_color(int x){
        for(Colors color:Colors.values()){
            if(color.value() == x)
                return color.color();
        }
        return null;
    }
}
