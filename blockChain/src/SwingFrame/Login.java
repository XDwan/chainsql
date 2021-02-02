package SwingFrame;

import ClientSocket.ClientConnection;
import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    String account = "";
    String password = "";
    String loginType = "";
    JSONObject object = new JSONObject();

    public Login() {
        this.setTitle("登陆界面");
        this.setLayout(null);
        this.setSize(500, 400);
        Container container = this.getContentPane();
//        登陆账号部分
        JLabel label_Account = new JLabel("学号");
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
        JRadioButton radioButton_student = new JRadioButton("学生");
        JRadioButton radioButton_teacher = new JRadioButton("教师");
        buttonGroup.add(radioButton_student);
        buttonGroup.add(radioButton_teacher);
        radioButton_student.setBounds(150, 200, 75, 50);
        radioButton_teacher.setBounds(275, 200, 75, 50);
        radioButton_student.setFont(new Font("", 0, 22));
        radioButton_teacher.setFont(new Font("", 0, 22));
        buttonGroup.setSelected(radioButton_student.getModel(), true);
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
        JButton button_login = new JButton("登陆");
        button_login.setFont(new Font("", 0, 22));
        button_login.setBounds(150, 280, 200, 50);
        button_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                account = account_input.getText();
                password = new String(password_input.getPassword());
                object.put("account", account);
                object.put("password", password);
                if (loginType.compareTo("student") == 0) object.put("MessageTpye", MessageType.Stu_Login);
                else object.put("MessageTpye", MessageType.Teacher_Login);
                System.out.println(object);
                ClientConnection connection = new ClientConnection();
                connection.set();
                connection.connect();
                Message reMessage = connection.send(new Message(object));
                switch (reMessage.getMessageType()){
                    case MessageType.nullMessage:
                        new Warning("","");
                        break;
                    case MessageType.Stu_Login_Return:
                        if (reMessage.getObject().getBoolean("success")){
                            OptFrame opt = new OptFrame(reMessage);
                            Thread thread = new Thread(opt);
                            thread.run();
                        }else{
                            new Warning("请检查账号密码","登陆失败");
                        }

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
        new Login();
    }


}
