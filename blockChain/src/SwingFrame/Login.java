package SwingFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    String account="";
    String password="";
    String loginType="";

    public Login(){
        this.setTitle("登陆界面");
        this.setVisible(true);
        this.setBounds(100,100,200,200);
        Container container = this.getContentPane();
//        登陆账号部分
        JLabel label_Account = new JLabel("账号");
        JLabel label_Password = new JLabel("密码");
//        单选框
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton radioButton_student = new JRadioButton("学生");
        JRadioButton radioButton_teacher = new JRadioButton("教师");
        buttonGroup.add(radioButton_student);
        buttonGroup.add(radioButton_teacher);
        buttonGroup.setSelected( radioButton_student.getModel(),true);
        loginType = "student";
        radioButton_student.addActionListener(this);
        radioButton_teacher.addActionListener(this);
        container.add(label_Account);
        container.add(label_Password);
        container.add(radioButton_student);
        container.add(radioButton_teacher);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Login();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("学生")){
            loginType = "student";
        }else{
            loginType = "teacher";
        }
        System.out.println(loginType);
    }
}
