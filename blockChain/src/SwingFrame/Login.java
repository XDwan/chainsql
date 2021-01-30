package SwingFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    String account="";
    String password="";
    String loginType="";

    public Login(){
        this.setTitle("登陆界面");
        this.setLayout(null);
        this.setSize(500,400);
        Container container = this.getContentPane();
//        登陆账号部分
        JLabel label_Account = new JLabel("账号");
        JLabel label_Password = new JLabel("密码");
        label_Account.setFont(new Font("",0,22));
        label_Password.setFont(new Font("",0,22));
        container.add(label_Account);
        container.add(label_Password);
        label_Account.setBounds(50,50,50,50);
        label_Password.setBounds(50,100,50,50);
        JTextField account = new JTextField();
        JPasswordField password = new JPasswordField();
        account.setBounds(100,50,150,35);
        password.setBounds(100,100,150,35);
        container.add(account);
        container.add(password);
        account.setFont(new Font("",0,22));
        password.setFont(new Font("",0,22));
        password.setEchoChar('*');

//        单选框
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton radioButton_student = new JRadioButton("学生");
        JRadioButton radioButton_teacher = new JRadioButton("教师");
        buttonGroup.add(radioButton_student);
        buttonGroup.add(radioButton_teacher);
        buttonGroup.setSelected( radioButton_student.getModel(),true);

        loginType = "student";

        radioButton_student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginType = "student";
                System.out.println(loginType);
            }
        });
        radioButton_teacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginType = "teacher";
                System.out.println(loginType);
            }
        });


        container.add(radioButton_student);
        container.add(radioButton_teacher);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }


}
