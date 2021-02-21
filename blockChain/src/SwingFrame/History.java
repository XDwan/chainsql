package SwingFrame;

import messageCollection.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.DataOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class History extends JFrame {
    Container container;
    JList list;
    String[] history;

    public History(Message message){
        setTitle("历史记录");
        setLayout(null);
        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //设置窗体退出时操作
        setSize(1000,600);   //设置窗体位置和大小
        JPanel contentPane=new JPanel();    //创建内容面板
        contentPane.setPreferredSize(new Dimension(1000,600));
        container = getContentPane();
        container.add(contentPane);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));    //设置面板的边框
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBounds(0,0,980,600);
        JScrollPane scrollPane=new JScrollPane();    //创建滚动面板
        contentPane.add(scrollPane,BorderLayout.CENTER);    //将面板增加到边界布局中央
        list=new JList();
        list.setFont(new Font("",0,20));
        list.setSelectedIndex(0);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(list);    //在滚动面板中显示列表
        updateHistory(message);
        this.setVisible(true);
    }

    private void updateHistory(Message message) {
        JSONArray array = message.getInfo().getJSONArray("history");
        String [] groupList = new String[array.size()];
        for (int i=0;i<array.size();i++){
            groupList[i]=setHistory(array.getJSONObject(i));
        }
        list.setListData(groupList);
    }

    private String setHistory(JSONObject object){
        String history;
        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = object.getLong("releaseTime");

        String releaseTime = dFormat.format(new Date(time));
        System.out.println(releaseTime);
        time = object.getLong("endTime");
        String endTime = dFormat.format(new Date(time));

        history = "发布人："+object.getString("admin")+" 起始时间:"+releaseTime+" 终止时间： "+endTime+" 签到状态: ";
        if (object.getInt("status")==-1){
            return history;
        }else {
            if (object.getInt("status")==0){
                history+="签到成功";
            }else {
                history+="签到失败";
            }
        }
        return history;
    }

    public static void main(String[] args) {
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

        Message message = new Message();
        message.add("history",array);
        new History(message);
    }
}
