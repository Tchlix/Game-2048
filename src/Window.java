import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Window extends JFrame implements KeyListener {

    Grid grid=new Grid(this);

    public Window()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("2048");
        setSize(400, 500);
        setResizable(false);
        setLayout(new GridLayout(4,4));
        setLocationRelativeTo(null);
        setVisible(true);
        this.addKeyListener(this);
        grid.draw();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W)
            grid.update(Directions.UP);
        else if (e.getKeyCode() == KeyEvent.VK_S)
            grid.update(Directions.DOWN);
        else if (e.getKeyCode() == KeyEvent.VK_A)
            grid.update(Directions.LEFT);
        else if (e.getKeyCode() == KeyEvent.VK_D)
            grid.update(Directions.RIGHT);
        else if (e.getKeyCode() == KeyEvent.VK_R)
            grid.reset();
    }
    @Override
    public void keyReleased(KeyEvent e) { }
    @Override
    public void keyTyped(KeyEvent e) { }
}
