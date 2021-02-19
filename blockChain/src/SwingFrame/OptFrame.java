package SwingFrame;

import Client.ClientConnection;
import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class OptFrame extends JFrame implements Runnable {
    public boolean runnable = true;
    JSONObject info = new JSONObject();
    int loginType ;
    final static int STU = MessageType.User_Login; // 学生登陆模式
    final static int TEH = MessageType.Admin_Login; // 教师登陆模式

    Container container = this.getContentPane();
    JPanel statusStu = new JPanel();
    JLabel name = new JLabel("姓名：万世杰");
    JLabel id = new JLabel("学号：19030100408");
    JLabel status = new JLabel("当前状态：空闲");
    JLabel signInStatus = new JLabel("签到状态：无签到");
    JButton signButton = new JButton();
    JButton historyButton = new JButton();
    ClientConnection connection = new ClientConnection();


    public OptFrame(Message reMessage,int login) {
        JSONObject object = reMessage.getInfo();
        loginType = login;
        switch (loginType){
            case OptFrame.STU:
                info.put("name",object.getString("name"));
                info.put("id",object.getString("id"));
                info.put("password",object.getString("password"));
                frameShowUser();
                break;
            case OptFrame.TEH:

                frameShowTeacher();
                break;
        }


    }

    @Override
    public void run() {

        System.out.println("线程开始");
        Message message = new Message();
        message.buildUserRequest(info);
        connection.set();
        connection.connect();
        message = connection.send(message);
        if (message.getMessageType() == MessageType.User_Return){
            userStateUpdate(message.getInfo());
            System.out.println("用户信息更新完毕");
        }

    }

    public void frameShowUser() {
        setTitle("用户考勤界面");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        // 签到按钮
        signInButton_Set();
        // 状态面板
        userStatus_Set();
        // 查看历史记录
        historyButton_Set();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void frameShowTeacher() {
        this.setTitle("考勤管理界面");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void signInButton_Set() {
        // 登陆按钮设置
        signButton.setBounds(350,50,100,50);
        signButton.setText("签到");
        container.add(signButton);
    }

    public void historyButton_Set(){
        historyButton.setBounds(350,150,100,50);
        historyButton.setText("签到历史");
        container.add(historyButton);

    }
    public void userStatus_Set() {
        statusStu.setBounds(50, 50, 200, 200);
        statusStu.add(name);
        statusStu.add(id);
        statusStu.add(status);
        statusStu.add(signInStatus);
        statusStu.setLayout(null);
        name.setBounds(0, 0, 200, 50);
        id.setBounds(0, 50, 200, 50);
        status.setBounds(0, 100, 200, 50);
        signInStatus.setBounds(0, 150, 200, 50);
        name.setFont(new Font("", 0, 22));
        id.setFont(new Font("", 0, 22));
        status.setFont(new Font("", 0, 22));
        signInStatus.setFont(new Font("", 0, 22));
        container.add(statusStu);
    }

    public void userStateUpdate(JSONObject state) {
        // 状态标签
        name.setText("姓名：" + state.getString("name"));
        id.setText("学号：" + state.getString("id"));
        status.setText("当前状态：" + state.getString("Status"));
        signInStatus.setText("签到状态" + state.getString("signInStatus"));
    }


    public static void main(String[] args) {
        Message message = new Message(new JSONObject());
        message.add("name","wan");
        message.add("id","19030100408");
        message.add("password","123456");
        message.add("classId","190315");
        message.add("login",OptFrame.STU);
        new OptFrame(message,STU).frameShowUser();
    }
}
