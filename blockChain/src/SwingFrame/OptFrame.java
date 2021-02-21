package SwingFrame;

import Client.ClientConnection;
import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class OptFrame extends JFrame implements Runnable {
    public boolean runnable = true;
    JSONObject info = new JSONObject();
    int loginType ;
    final static int STU = MessageType.User_Login; // 学生登陆模式
    final static int TEH = MessageType.Admin_Login; // 教师登陆模式

    Container container = this.getContentPane();
    JPanel status = new JPanel();
    JLabel name = new JLabel("");
    JLabel id = new JLabel("");
    JLabel group = new JLabel("");
    JLabel signInStatus = new JLabel("");
    JButton signButton = new JButton();
    JButton historyButton = new JButton();

    JButton releaseButton = new JButton();
    JButton groupButton = new JButton();

    public OptFrame(Message reMessage,int login) {
        JSONObject object = reMessage.getInfo();
        loginType = login;
        switch (loginType){
            case OptFrame.STU:
                info.put("name",object.getString("name"));
                info.put("id",object.getString("id"));
                info.put("password",object.getString("password"));
                userStatusUpdate(reMessage.getInfo());
                frameShowUser();
                break;
            case OptFrame.TEH:
                info.put("name",object.getString("name"));
                info.put("id",object.getString("id"));
                info.put("password",object.getString("password"));
                adminStatusUpdate(reMessage.getInfo());
                frameShowAdmin();

                break;
        }


    }

    @Override
    public void run() {

        System.out.println("线程开始");
        Message message = new Message();
        message.buildUserRequest(info);
        ClientConnection connection = new ClientConnection();
        connection.set();
        connection.connect();
        message = connection.send(message);
        if (message.getMessageType() == MessageType.User_Return){
            userStatusUpdate(message.getInfo());
            System.out.println("用户信息更新完毕");
        }else {
            adminStatusUpdate(message.getInfo());
        }

    }

    public void frameShowUser() {
        setTitle("用户考勤界面");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        // 签到按钮
        setSignInButton();
        // 状态面板
        setUserStatus();
        // 查看历史记录
        setHistoryButton();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void frameShowAdmin() {
        this.setTitle("考勤管理界面");
        setSize(600, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setReleaseButton();
        setGroupButton();
        setAdminStatus();
//        adminStatusUpdate();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setAdminStatus() {
        status.setBounds(50, 50, 200, 200);
        status.add(name);
        status.add(id);
        status.setLayout(null);
        name.setBounds(0, 0, 200, 50);
        id.setBounds(0, 50, 200, 50);
        name.setFont(new Font("", 0, 22));
        id.setFont(new Font("", 0, 22));
        container.add(status);
    }


    public void setSignInButton() {
        // 登陆按钮设置
        signButton.setBounds(350,50,100,50);
        signButton.setText("签到");
        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(signInStatus.getText());
                if (signInStatus.getText().compareTo("签到状态: 空闲")==0){
                    new Warning("不在签到时间范围内","签到");
                    return ;
                }
                new Warning("签到失败！请联系管理员","签到");
                ClientConnection connection = new ClientConnection();
                Message message = new Message();
                message.setMessageType(MessageType.User_Sign);
                message.addBasicInfo(info);
                message = connection.send(message);
                if (message.getInfo().getBoolean("status")){
                    new Warning("签到成功!","签到");
                    userStatusUpdate(message.getInfo());
                }else {
                    new Warning("签到失败！","签到");
                }
            }
        });
        container.add(signButton);
    }

    public void setHistoryButton(){
        historyButton.setBounds(350,150,100,50);
        historyButton.setText("签到历史");
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                ClientConnection connection = new ClientConnection();
                Message message = new Message();
                message.setMessageType(MessageType.User_History);
                message.addBasicInfo(info);
                message.add("status",true);
//                message = connection.send(message);
                if (message.getInfo().getBoolean("status")){
                    Date date = new Date(System.currentTimeMillis());
                    long releaseTime = date.getTime();
                    long endTime = releaseTime+5*60*1000;
                    JSONArray array =new JSONArray();
                    for (int i=0;i<100;i++){
                        JSONObject object = new JSONObject();
                        object.put("releaseTime",releaseTime-i*60*60*1000);
                        object.put("endTime",endTime-i*60*60*1000);
                        object.put("admin","管理员1");
                        object.put("status",0);

                        array.add(object);
                    }

                    message = new Message();
                    message.add("history",array);
                    new History(message);
                }else {
                    new Warning("查询失败","查询签到历史");
                }

            }
        });
        container.add(historyButton);
    }

    public void setReleaseButton(){
        // 打开群组界面 发布签到
        releaseButton.setText("发布签到");
        releaseButton.setBounds(350,50,100,50);
        releaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();

                JSONArray array = new JSONArray();
                for (int i=0;i<5;i++){
                    JSONObject object = new JSONObject();
                    object.put("group","第"+String.valueOf(i)+"组");
                    array.add(object);
                }
                message.add("groupList",array);
                new Release(message);
//                ClientConnection connection = new ClientConnection();
//                message.setMessageType(MessageType.Admin_Release);
//                message.addBasicInfo(info);
//                message = connection.send(message);
//                if (message.getInfo().getBoolean("status")){
//                    new Release(message);
//                }else {
//                    new Warning("发布失败！请检查服务器运行","发布签到");
//
//                }


            }
        });
        container.add(releaseButton);
    }

    public void setGroupButton(){// 包括新建成员
        groupButton.setText("群组管理");
        groupButton.setBounds(350,150,100,50);
        groupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();
                message.addBasicInfo(info);
                System.out.println(message.getInfo());
                new Group(message);
            }
        });
        container.add(groupButton);
    }




    public void setUserStatus() {
        status.setBounds(50, 50, 200, 200);
        status.add(name);
        status.add(id);
        status.add(group);
        status.add(signInStatus);
        status.setLayout(null);
        name.setBounds(0, 0, 200, 50);
        id.setBounds(0, 50, 200, 50);
        group.setBounds(0, 100, 200, 50);
        signInStatus.setBounds(0, 150, 200, 50);
        name.setFont(new Font("", 0, 22));
        id.setFont(new Font("", 0, 22));
        group.setFont(new Font("", 0, 22));
        signInStatus.setFont(new Font("", 0, 22));
        container.add(status);
    }

    public void userStatusUpdate(JSONObject status) {
        // 状态标签
        name.setText("姓名：" + status.getString("name"));
        id.setText("ID ：" + status.getString("id"));
        group.setText("当前群组：" + status.getString("group"));
        signInStatus.setText("签到状态: " + status.getString("signInStatus"));
    }

    public void adminStatusUpdate(JSONObject status){
        name.setText("姓名：" + status.getString("name"));
        id.setText("ID ：" + status.getString("id"));
    }


    public static void main(String[] args) {
        Message message = new Message(new JSONObject());
        message.add("name","用户1");
        message.add("id","19030100000");
        message.add("password","123456");
        message.add("group","190315");
        message.add("signInStatus","签到中");
        message.add("login",OptFrame.TEH);
        new OptFrame(message,TEH);
    }
}
