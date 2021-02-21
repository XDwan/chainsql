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

public class Group extends JFrame{
    Container container;
    JButton add;
    JButton del;
    JSONArray groups;
    JSONArray members;
    String[]groupName;
    String[]memberName;
    JList groupList;
    JList memberList;
    JSONObject info = new JSONObject();
    String id;

    public Group(Message message) {
        info.put("name",message.getInfo().getString("name"));
        info.put("id",message.getInfo().getString("id"));
        setTitle("群组管理");
        setLayout(null);
        setSize(800,600);
        setLocationRelativeTo(null);
        container = getContentPane();
        setLeft();
        updateGroupName(message);
        setRight();
        setAdd();
        setDel();
        setVisible(true);
    }

    public void setLeft() {
        JPanel contentPane=new JPanel();    //创建内容面板
        contentPane.setPreferredSize(new Dimension(300,600));
        container.add(contentPane);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));    //设置面板的边框
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBounds(0,50,300,550);
        JScrollPane scrollPane=new JScrollPane();    //创建滚动面板
        contentPane.add(scrollPane,BorderLayout.CENTER);    //将面板增加到边界布局中央
        groupList=new JList();
        groupList.setFont(new Font("",0,20));
        groupList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Message message = new Message();
                message.setMessageType(MessageType.Admin_ChangeGroup);
                message.addBasicInfo(info);
                updateMemberName(message);
                ClientConnection connection = new ClientConnection();
                message = connection.send(message);
                if (message.getInfo().getBoolean("status")){
                    updateMemberName(message);
                }else {
                    new Warning("更新失败！请检查服务器运行","面板更新");
                }
            }
        });
        //限制只能选择一个元素
        groupList.setSelectedIndex(0);
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(groupList);    //在滚动面板中显示列表
        JLabel label = new JLabel("群组");
        label.setBounds(50,0,100,50);
        label.setFont(new Font("",0,20));
        container.add(label);
    }

    public void updateGroupName(Message message) {
        members = message.getInfo().getJSONArray("groupList");
        memberName = new String[members.size()];
        for (int i=0;i<members.size();i++){
            memberName[i]=members.getJSONObject(i).getString("group");
        }
        groupList.setListData(groupName);
    }

    public void updateMemberName(Message message){
        members = message.getInfo().getJSONArray("memberList");
        memberName = new String[members.size()];
        for (int i=0;i<members.size();i++){
            memberName[i]=members.getJSONObject(i).getString("group");
        }
        memberName = new String[10];
        for (int i=0;i<10;i++){
            memberName[i]="成员"+i;
        }
        memberList.setListData(memberName);
    }
    public void setRight() {
        JPanel contentPane=new JPanel();    //创建内容面板
        contentPane.setPreferredSize(new Dimension(300,600));
        container.add(contentPane);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));    //设置面板的边框
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBounds(300,50,300,550);
        JScrollPane scrollPane=new JScrollPane();    //创建滚动面板
        contentPane.add(scrollPane,BorderLayout.CENTER);    //将面板增加到边界布局中央
        memberList=new JList();
        memberList.setFont(new Font("",0,20));
        memberList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = memberList.getSelectedIndex();
                System.out.println(index);
                JSONObject object =members.getJSONObject(index);
                id = object.getString("id");
                System.out.println(id);
            }
        });
        //限制只能选择一个元素
        memberList.setSelectedIndex(0);
        memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(memberList);    //在滚动面板中显示列表
        JLabel label = new JLabel("群组成员");
        label.setBounds(350,0,100,50);
        label.setFont(new Font("",0,20));
        container.add(label);
    }

    public void setAdd() {
        add = new JButton("新增成员");
        add.setBounds(650,100,100,50);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddMember();
            }
        });
        container.add(add);
    }

    public void setDel(){
        del = new JButton("删除成员");
        del.setBounds(650,200,100,50);
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();
                message.setMessageType(MessageType.Admin_DelUser);
                message.add("id",id);
                ClientConnection connection = new ClientConnection();
                message = connection.send(message);
                if (message.getInfo().getBoolean("status")){
                    new Warning("删除成功","删除用户");
                    updateMemberName(message);
                }else {
                    new Warning("删除失败","删除用户");
                }
            }
        });
        container.add(del);
    }

    public static void main(String[] args) {
        Message message = new Message();
        new Group(message);
    }
}
