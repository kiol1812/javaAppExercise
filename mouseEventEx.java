import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class mouseEventEx extends JFrame implements MouseListener {
    JLabel label;
    public mouseEventEx(){
        super("mouse event demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(300, 300);

        label = new JLabel("hello label", JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.YELLOW);
        label.setSize(100, 20);
        label.setLocation(100, 100);
        add(label);

        getContentPane().addMouseListener(this);
    }
    public void mouseClicked(MouseEvent e){
        label.setLocation(e.getX(), e.getY());
    }
    public static void main(String args[]){
        mouseEventEx frame = new mouseEventEx();
        frame.setVisible(true);
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
