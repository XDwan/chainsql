package SwingFrame;

import Client.ClientConnection;
import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Release extends JFrame {
    Container container;
    JButton release ;
    JButton newMember;
    JPanel left = new JPanel();
    String groups[];
    JList list ;
    String group = null;
    public Release(Message message){
        setTitle("发布签到");
        setLayout(null);
        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //设置窗体退出时操作
        setSize(700,600);   //设置窗体位置和大小
        JPanel contentPane=new JPanel();    //创建内容面板
        contentPane.setPreferredSize(new Dimension(300,600));
        container = getContentPane();
        container.add(contentPane);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));    //设置面板的边框
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBounds(0,0,350,600);
        JScrollPane scrollPane=new JScrollPane();    //创建滚动面板
        contentPane.add(scrollPane,BorderLayout.CENTER);    //将面板增加到边界布局中央
        list=new JList();
        list.setFont(new Font("",0,20));
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                group = (String) list.getSelectedValue();
                System.out.println(group);
            }
        });
        //限制只能选择一个元素
        list.setSelectedIndex(0);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(list);    //在滚动面板中显示列表
        updateGroups(message);
        this.setVisible(true);

        setRelease();
        container.add(release);
    }

    public void setRelease() {
        JButton release = new JButton("发布签到");
        release.setBounds(400,100,100,50);
        release.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (group == null){
                    new Warning("请选择要发送的组","发布签到");
                    return ;
                }
                Message message = new Message();
                message.setMessageType(MessageType.Admin_Release);
                message.add("group",group);
                ClientConnection connection = new ClientConnection();
                message = connection.send(message);
                if (message.getInfo().getBoolean("status")){
                    new Warning("发布成功","发布签到");
                }else {
                    new Warning("未成功,请检查服务器运行","发布签到");
                }
            }
        });
        this.release = release;
    }



    public void updateGroups(Message message){
        JSONArray array = message.getInfo().getJSONArray("groupList");
        String [] groupList = new String[array.size()];
        for (int i=0;i<array.size();i++){
            groupList[i]=array.getJSONObject(i).getString("group");
        }
        list.setListData(groupList);

    }

    public static void main(String[] args) {
//        Message message = new Message();
//        JSONArray array = new JSONArray();
//        for (int i=0;i<5;i++){
//            JSONObject object = new JSONObject();
//            object.put("group","第"+String.valueOf(i)+"组");
//            array.add(object);
//        }
//        message.add("groupList",array);
//        new Release(message);
        new Warning("未成功,请检查服务器运行","发布签到");
    }
}
