import javax.swing.*;
import java.awt.*;

public class frameEx1{
    public static void main(String rags[]){
        JFrame frame = new JFrame("JLabel Examples");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        JLabel basic = new JLabel("Default Label");
        frame.add(basic);
        frame.setVisible(true);
    }
}