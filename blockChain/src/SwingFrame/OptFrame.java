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
    public OptFrame(Message reMessage){
        account = reMessage.getObject();
    }

    @Override
    public void run() {

    }

    public void frameShowStu(){
        this.setTitle("学生考勤界面");
        this.setSize(600,500);

        // 签到按钮

        // 状态面板
        statusStu.setBounds(0,0,200,200);
        container.add(statusStu);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void frameShowTeacher(){
        this.setTitle("教师考勤管理界面");
    }

    public void setStuState(JSONObject state){
        // 状态标签
        JLabel name = new JLabel("姓名："+state.getString("Name"));
        JLabel id = new JLabel("学号："+state.getString("id"));
        JLabel status = new JLabel("当前状态："+state.getString("Status"));
        JLabel signInStatus = new JLabel("签到状态"+state.getString("SignInStatus"));


    }

    public static void main(String[] args) {
        new OptFrame(new Message()).frameShowStu();
    }
}
