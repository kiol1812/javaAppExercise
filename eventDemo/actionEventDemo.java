package eventDemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class actionEventDemo extends JFrame implements ActionListener {
    private int counterValue;
    private JLabel counterLabel;
    public actionEventDemo(){
        super("action event counter demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(300, 300);

        counterLabel = new JLabel("Count: 0", JLabel.CENTER);
        add(counterLabel);

        JButton increnter = new JButton("increment");
        increnter.addActionListener(this);
        add(increnter);
    }
    public void actionPerformed(ActionEvent e){
        counterValue++;
        counterLabel.setText("Count: "+counterValue);
    }
    public static void main(String args[]){
        actionEventDemo demo = new actionEventDemo();
        demo.setVisible(true);
    }
}
