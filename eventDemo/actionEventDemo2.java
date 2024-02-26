package eventDemo;
/* 2024/02/26
 * would work like "actionEventDemo.java", but hava a bug
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
// import 
import java.awt.event.ActionListener;

public class actionEventDemo2 {
    public static void main(String args[]){
        JFrame frame = new JFrame("action listener demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(300, 180);

        JLabel label = new JLabel("Result go here", JLabel.CENTER);
        ActionCommandHelper helper = new ActionCommandHelper(label);

        JButton simpleButton = new JButton("Button");
        simpleButton.addActionListener(helper);

        JTextField simpleField = new JTextField(10);
        simpleField.addActionListener(helper);

        frame.add(simpleButton);
        frame.add(simpleField);
        frame.add(label);

        frame.setVisible(true);
    }
}
class ActionCommandHelper implements ActionListener{
    private JLabel resultLabel;
    public ActionCommandHelper(JLabel label){
        resultLabel = label;
    }
    public void actionPerformed(ActionEvent ae){
        resultLabel.setText(ae.getActionCommand());
    }
}