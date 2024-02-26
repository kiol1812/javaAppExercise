package eventDemo;
/* 2024/02/26
 * work like "mouseEventDemo.java"
 */
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.*;

public class mouseEventHelper {
    public static void main(String args[]){
        JFrame frame = new JFrame("mouse event demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(300, 300);

        JLabel label = new JLabel("hello, mouse!", JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.YELLOW);
        label.setSize(100, 20);
        label.setLocation(100, 100);
        frame.add(label);

        LabelMover mover = new LabelMover(label);
        frame.getContentPane().addMouseListener(mover);
        frame.setVisible(true);
    }
}

class LabelMover extends MouseAdapter {
    JLabel labelToMove;
    public LabelMover(JLabel label){
        labelToMove=label;
    }
    public void mouseClicked(MouseEvent e){
        labelToMove.setLocation(e.getX(), e.getY());
    }
}
