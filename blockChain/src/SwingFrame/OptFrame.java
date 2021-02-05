package SwingFrame;

import ClientSocket.ClientConnection;
import info.Student_info;
import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class OptFrame extends JFrame implements Runnable {
    JSONObject info = new JSONObject();
    Student_info stuInfo;

    Container container = this.getContentPane();
    JPanel statusStu = new JPanel();
    JLabel name = new JLabel("姓名：万世杰");
    JLabel id = new JLabel("学号：19030100408");
    JLabel status = new JLabel("当前状态：空闲");
    JLabel signInStatus = new JLabel("签到状态：无签到");
    JButton signButton = new JButton();
    JButton historyButton = new JButton();
    ClientConnection connection = new ClientConnection();

    public OptFrame(Message reMessage) {
        info.put("name",reMessage.getInfo().getString("name"));
        info.put("id",reMessage.getInfo().getString("id"));
        info.put("password",reMessage.getInfo().getString("password"));
        stuInfo = new Student_info(reMessage.getInfo());
    }

    @Override
    public void run() {
        connection.set();
        connection.connect();
        Message message = connection.send(request());
        if (message.getMessageType() == MessageType.Stu_Return){
            stuStateUpdate(message.getInfo());
            System.out.println("学生信息更新完毕");
        }

    }

    public void frameShowStu() {
        setTitle("学生考勤界面");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        // 签到按钮
        signInButton_Set();
        // 状态面板
        stuStatus_Set();
        // 查看历史记录
        historyButton_Set();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void frameShowTeacher() {
        this.setTitle("教师考勤管理界面");
    }


    public void signInButton_Set() {
        signButton.setBounds(350,50,100,50);
        signButton.setText("签到");
        container.add(signButton);
    }

    public void historyButton_Set(){
        historyButton.setBounds(350,150,100,50);
        historyButton.setText("签到历史");
        container.add(historyButton);

    }
    public void stuStatus_Set() {
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

    public void stuStateUpdate(JSONObject state) {
        // 状态标签
        name.setText("姓名：" + state.getString("name"));
        id.setText("学号：" + state.getString("id"));
        status.setText("当前状态：" + state.getString("Status"));
        signInStatus.setText("签到状态" + state.getString("signInStatus"));
    }

    public Message request(){
        Message request = new Message();
        request.setMessageType(MessageType.Stu_Request);
        request.addInfo("stuInfo",stuInfo);
        return request;
    }
    public static void main(String[] args) {
        new OptFrame(new Message()).frameShowStu();
    }
}
