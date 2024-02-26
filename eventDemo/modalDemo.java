package eventDemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class modalDemo extends JFrame implements ActionListener {
    private JLabel modalLabel;
    public modalDemo(){
        super("modal dialog demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(300, 180);

        modalLabel = new JLabel("press 'Go' to show the popup", JLabel.CENTER);
        add(modalLabel);

        JButton goButton = new JButton("Go");
        goButton.addActionListener(this);
        add(goButton);
    }
    public void actionPerformed(ActionEvent ae){
        // A. message dialog
        // JOptionPane.showMessageDialog(this, "message", "Alert", JOptionPane.INFORMATION_MESSAGE);
        //4: .ERROR_MESSAGE / .INFORMATION_MESSAGE / .WARING_MESSAGE / .QUESION_MESSAGE / PLAIN_MESSAGE
        // modalLabel.setText("Go pressed! Press again if you like.");

        // B. confirm dialog
        // int answer = JOptionPane.showConfirmDialog(null, "Are you sure?");
        // String test = (answer==JOptionPane.YES_OPTION)?":)":">:(";
        // modalLabel.setText(test);

        // c. input dialog
        String pin = JOptionPane.showInputDialog(null, "please enter your PIN:");
        modalLabel.setText(pin);
    }
    public static void main(String args[]){
        modalDemo demo = new modalDemo();
        demo.setVisible(true);
    }
}
