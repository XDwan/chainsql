package SwingFrame;

import ClientSocket.ClientConnection;
import info.Student_info;
import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptFrame extends JFrame implements Runnable {
    public boolean runnable = true;
    JSONObject info = new JSONObject();
    int loginType ;
    final static int STU = 1; // 学生登陆模式
    final static int TEH = 2; // 教师登陆模式

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
        JSONObject object = reMessage.getInfo();
        System.out.println(object.getInt("loginType"));
        loginType = object.getInt("loginType");
        switch (loginType){
            case OptFrame.STU:
                info.put("name",object.getString("name"));
                info.put("id",object.getString("id"));
                info.put("password",object.getString("password"));
                frameShowStu();
                break;
            case OptFrame.TEH:

                frameShowTeacher();
                break;
        }


    }

    @Override
    public void run() {
//        connection.set();
//        connection.connect();
//        Message message = connection.send(request());
        System.out.println("线程开始");
        Message message = new Message(new JSONObject());
        message.addInfo("name","wan");
        message.addInfo("id","19030100408");
        message.addInfo("password","123456");
        message.addInfo("classId","190315");
        message.addInfo("login",OptFrame.STU);
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
        if (loginType == OptFrame.STU){
            request.setMessageType(MessageType.Stu_Request);
            request.addInfo("stuInfo", new Student_info(info));
        }


        return request;
    }

    public static void main(String[] args) {
        Message message = new Message(new JSONObject());
        message.addInfo("name","wan");
        message.addInfo("id","19030100408");
        message.addInfo("password","123456");
        message.addInfo("classId","190315");
        message.addInfo("login",OptFrame.STU);
        new OptFrame(message).frameShowStu();
    }
}
