package eventDemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgressDemo {
    public static void main(String args[]){
        JFrame frame = new JFrame("SwingUtilities 'invoke' demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(300, 180);

        JLabel label = new JLabel("Download progress goes here!", JLabel.CENTER);
        Thread pretender = new Thread(new ProgressPretender(label));

        JButton simpleButton = new JButton("Start");
        simpleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                simpleButton.setEnabled(false);
                pretender.start();
            }
        });

        JLabel checkLabel = new JLabel("Can you still type?");
        JTextField checkField = new JTextField(10);

        frame.add(label);
        frame.add(simpleButton);
        frame.add(checkLabel);
        frame.add(checkField);
        frame.setVisible(true);
    }
}
class ProgressPretender implements Runnable {
    private JLabel label;
    private int progress;
    public ProgressPretender(JLabel label){
        this.label = label;
        progress = 0;
    }
    public void run(){
        while(progress<=100){
            SwingUtilities.invokeLater(new Runnable() {
                public void run(){
                    label.setText(progress+"%");
                }
            });
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie){
                System.err.println("Someone inerttupted us. Skipping download.");
                break;
            }
            progress++;
        }
    }
}
