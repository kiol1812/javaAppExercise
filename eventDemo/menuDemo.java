package eventDemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuDemo extends JFrame implements ActionListener {
    private JLabel resultsLabel;
    public menuDemo(){
        super("JMenu demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(300, 180);

        resultsLabel = new JLabel("Click a menu item!");
        add(resultsLabel);

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(this);
        fileMenu.add(quitItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(this);
        editMenu.add(cutItem);
        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(this);
        editMenu.add(copyItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(this);
        editMenu.add(pasteItem);

        JMenuBar mainBar = new JMenuBar();
        mainBar.add(fileMenu);
        mainBar.add(editMenu);
        setJMenuBar(mainBar);
    }
    public void actionPerformed(ActionEvent ae){
        resultsLabel.setText("Menu selected: "+ae.getActionCommand());
    }
    public static void main(String args[]){
        menuDemo demo = new menuDemo();
        demo.setVisible(true);
    }
}
