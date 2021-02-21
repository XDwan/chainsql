package SwingFrame;

import Client.ClientConnection;
import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMember extends JFrame {


    public AddMember() {
        this.setTitle("注册用户界面");
        this.setLayout(null);
        this.setSize(500, 400);
        Container container = this.getContentPane();
//        登陆账号部分
        JLabel label_Account = new JLabel("ID");
        JLabel label_Name = new JLabel("姓名");
        JLabel label_Group = new JLabel("群组");
        label_Group.setText("群组");
        label_Account.setFont(new Font("", 0, 22));
        label_Name.setFont(new Font("", 0, 22));
        label_Group.setFont(new Font("", 0, 22));
        container.add(label_Account);
        container.add(label_Name);
        container.add(label_Group);
        label_Account.setBounds(75, 40, 50, 50);
        label_Name.setBounds(75, 90, 50, 50);
        label_Group.setBounds(75, 140, 50, 50);
        JTextField account_input = new JTextField();
        JTextField name_input = new JTextField();
        JTextField group_input = new JTextField();
        account_input.setBounds(150, 50, 225, 35);
        name_input.setBounds(150, 100, 225, 35);
        group_input.setBounds(150, 150, 225, 35);
        container.add(account_input);
        container.add(name_input);
        container.add(group_input);
        account_input.setFont(new Font("", 0, 22));
        name_input.setFont(new Font("", 0, 22));
        group_input.setFont(new Font("", 0, 22));

//        单选框
        JButton button_login = new JButton("注册");
        button_login.setFont(new Font("", 0, 22));
        button_login.setBounds(150, 280, 200, 50);
        button_login.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Message message = new Message();
                        message.setMessageType(MessageType.Admin_NewUser);
                        message.add("id",account_input.getText());
                        message.add("group",group_input.getText());
                        message.add("name",name_input.getText());
                        ClientConnection connection = new ClientConnection();
                        message = connection.send(message);
                        if (message.getInfo().getBoolean("status")){
                            new Warning("注册成功","注册用户");
                            setVisible(false);
                        }else{
                            new Warning("注册失败","注册用户");
                        }
                        new Warning("注册成功","注册用户");

                    }
                }
        );
        container.add(button_login);
        setLocationRelativeTo(null);
        this.setVisible(true);
    }



    public static void main(String[] args) {
        new AddMember();
    }
}
