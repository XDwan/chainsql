package SwingFrame;

import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class OptFrame extends JFrame implements Runnable {
    JSONObject account = new JSONObject();
    Container container = this.getContentPane();
    JPanel statusStu = new JPanel();
    JLabel name = new JLabel("姓名：万世杰");
    JLabel id = new JLabel("学号：19030100408");
    JLabel status = new JLabel("当前状态：空闲");
    JLabel signInStatus = new JLabel("签到状态：无签到");

    public OptFrame(Message reMessage) {
        account = reMessage.getObject();
    }

    @Override
    public void run() {

    }

    public void frameShowStu() {
        setTitle("学生考勤界面");
        setSize(600, 500);
        setLocationRelativeTo(null);
        // 签到按钮

        // 状态面板
        stuStatus_Set();

        container.add(statusStu);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void frameShowTeacher() {
        this.setTitle("教师考勤管理界面");
    }

    public void stuStatus_Set(){
        statusStu.setBounds(50, 50, 200, 200);
        statusStu.add(name);
        statusStu.add(id);
        statusStu.add(status);
        statusStu.add(signInStatus);
        statusStu.setLayout(null);
        name.setBounds(0,0,200,50);
        id.setBounds(0,50,200,50);
        status.setBounds(0,100,200,50);
        signInStatus.setBounds(0,150,200,50);
        name.setFont(new Font("",0,22));
        id.setFont(new Font("",0,22));
        status.setFont(new Font("",0,22));
        signInStatus.setFont(new Font("",0,22));

    }
    public void stuStateUpdate(JSONObject state) {
        // 状态标签
        name.setText("姓名：" + state.getString("Name"));
        id.setText("学号：" + state.getString("id"));
        status.setText("当前状态：" + state.getString("Status"));
        signInStatus.setText("签到状态" + state.getString("SignInStatus"));
    }

    public static void main(String[] args) {
        new OptFrame(new Message()).frameShowStu();
    }
}
