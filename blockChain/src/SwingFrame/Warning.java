package SwingFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Warning extends JDialog {
    public Warning(String message,String warningType){
        Container container = this.getContentPane();
        this.setTitle(warningType);
        JLabel label = new JLabel(message,JLabel.CENTER);
        label.setFont(new Font("",0,22));
        label.setBounds(0,0,200,120);

        JButton button = new JButton("确认");
        button.setFont(new Font("",0,20));
        button.setBounds(140,150,120,50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        container.add(button);
        container.add(label);
        this.setSize(400,250);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Warning("超市233333333333333333","nihao");
    }
}
