package SwingFrame;

import Client.ClientConnection;
import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    String id;
    String password;
    int loginType=MessageType.User_Login;
    JSONObject object = new JSONObject();

    public Login() {
        this.setTitle("登陆界面");
        this.setLayout(null);
        this.setSize(500, 400);
        Container container = this.getContentPane();
//        登陆账号部分
        JLabel label_Account = new JLabel("ID");
        JLabel label_Password = new JLabel("密码");
        label_Account.setFont(new Font("", 0, 22));
        label_Password.setFont(new Font("", 0, 22));
        container.add(label_Account);
        container.add(label_Password);
        label_Account.setBounds(75, 40, 50, 50);
        label_Password.setBounds(75, 90, 50, 50);
        JTextField account_input = new JTextField();
        JPasswordField password_input = new JPasswordField();
        account_input.setBounds(150, 50, 225, 35);
        password_input.setBounds(150, 100, 225, 35);
        container.add(account_input);
        container.add(password_input);
        account_input.setFont(new Font("", 0, 22));
        password_input.setFont(new Font("", 0, 22));
        password_input.setEchoChar('*');

//        单选框
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton radioButton_student = new JRadioButton("用户");
        JRadioButton radioButton_teacher = new JRadioButton("管理员");
        buttonGroup.add(radioButton_student);
        buttonGroup.add(radioButton_teacher);
        radioButton_student.setBounds(150, 200, 75, 50);
        radioButton_teacher.setBounds(275, 200, 75, 50);
        radioButton_student.setFont(new Font("", 0, 22));
        radioButton_teacher.setFont(new Font("", 0, 22));
        buttonGroup.setSelected(radioButton_student.getModel(), true);
        radioButton_student.addActionListener(e -> {
            loginType = MessageType.User_Login;
            System.out.println(loginType);
        });
        radioButton_teacher.addActionListener(e -> {
            loginType = MessageType.Admin_Login;
            System.out.println(loginType);
        });
        JButton button_login = new JButton("登陆");
        button_login.setFont(new Font("", 0, 22));
        button_login.setBounds(150, 280, 200, 50);
        button_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = account_input.getText();
                password = new String(password_input.getPassword());
                Message message = new Message();
                message.buildLogin(loginType, id, password);
                System.out.println(message.getInfo());

                ClientConnection connection = new ClientConnection();
                connection.set();
                connection.connect();
                message = connection.send(message);
                if (message.getInfo().getBoolean("success")){
                    OptFrame opt = new OptFrame(message,loginType);
                    while (true) {
                        Thread thread = new Thread(opt);
                        thread.run();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                }else {
                    new Warning("请检查账号密码", "登陆失败");
                }
            }
        });
        container.add(button_login);
        container.add(radioButton_student);
        container.add(radioButton_teacher);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public JSONObject getObject() {
        return object;
    }

    public static void main(String[] args) {
//        new Login();
        Message message = new Message(new JSONObject());
        message.add("name", "wan");
        message.add("id", "19030100408");
        message.add("password", "123456");
        message.add("classId", "190315");
        message.add("loginType", OptFrame.STU);
        OptFrame opt = new OptFrame(message,MessageType.User_Login);
        while (opt.runnable) {
            Thread thread = new Thread(opt);
            thread.run();
            System.out.println("线程完毕");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        System.out.println("线程结束");
    }


}
