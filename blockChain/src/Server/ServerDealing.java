package Server;

import chainsql.ChainSQLDeal;
import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class ServerDealing implements Runnable{
    Message message;
    Socket socket;
    ChainSQLDeal c;
    ObjectInputStream in;
    ObjectOutputStream out;

    public ServerDealing(Socket socket, ChainSQLDeal c) {
        this.c = c;
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            message = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public Message deal(Message message) {
        Message returnMessage = new Message();
        JSONObject object;
        String password;
        JSONArray array;
        Date date = new Date();

        switch (message.getMessageType()) {
            case MessageType.User_Login:
                object= JSONObject.fromObject(c.getUser(message.getDetail("id")));
                returnMessage.setMessageType(MessageType.User_Login_Return);
                if (object.has("error_message")){
                    returnMessage.add("status",false);
                }
                password = message.getDetail("password");
                if (password.compareTo(object.getString("password"))==0){

                    returnMessage.addBasicInfo(object);
                    returnMessage.add("status",true);
                    returnMessage.add("group",object.getString("group"));
                }
                break;
            case MessageType.Admin_Login:
                object = JSONObject.fromObject(c.getAdmin(message.getDetail("id")));
                returnMessage.setMessageType(MessageType.Admin_Login_Return);
                if (object.has("error_message")){
                    returnMessage.add("status",false);
                }
                password = message.getDetail("password");
                if (password.compareTo(object.getString("password"))==0){

                    returnMessage.addBasicInfo(object);
                    returnMessage.add("status",true);
                }
                break;

            case MessageType.User_Request:
                object = JSONObject.fromObject(c.getUserIndex(message.getDetail("id")));

                returnMessage.setMessageType(MessageType.User_Return);
                if (date.getTime()>Integer.valueOf(object.getString("endTime"))){
                    returnMessage.add("status",false);
                }else{
                    returnMessage.add("status",true);
                }
                break;
            case MessageType.User_History:
                array = JSONArray.fromObject(c.getUserHistory(message.getDetail("id")));
                if (array == null){
                    returnMessage.add("status",false);
                }else{
                    returnMessage.add("status",true);
                }
                returnMessage.setMessageType(MessageType.User_History);
                returnMessage.add("history",array);
                break;
            case MessageType.User_Sign:
                String index = c.getUserIndex(message.getDetail("id"));
                c.updateSignTime(index);
                object = JSONObject.fromObject(c.getUserIndex(message.getDetail("id")));
                returnMessage.setMessageType(MessageType.User_Return);
                if (date.getTime()>Integer.valueOf(object.getString("endTime"))){
                    returnMessage.add("status",false);
                }else{
                    returnMessage.add("status",true);
                }
                break;
            case MessageType.Admin_Release:
                boolean sign =c.releaseSign(message.getDetail("group"),Integer.valueOf(message.getDetail("time")));
                returnMessage.add("status",sign);
                returnMessage.setMessageType(MessageType.Admin_Release);
                break;

            case MessageType.Admin_Group:
                array = JSONArray.fromObject(c.getGroupList(message.getDetail("group")));
                if (array==null){
                    returnMessage.add("status",true);
                    break;
                }else{
                    returnMessage.add("status",false);
                }
                returnMessage.add("groupList",array);
                break;
            case MessageType.Admin_NewUser:
                returnMessage.add("status",
                        c.insertAdmin(message.getDetail("id")
                                ,message.getDetail("name")
                                ,message.getDetail("group")));
                break;
            case MessageType.Admin_DelUser:
                returnMessage.add(
                        "status",
                        c.delAdmin(
                                message.getDetail("id")
                                ,message.getDetail("name")
                        )
                );
                break;
            case MessageType.Admin_ReNew:
               returnMessage.add(
                       "status",
                       c.renewUser(
                               message.getDetail("name")
                               ,message.getDetail("id")
                       )
               );
        }
        return returnMessage;
    }


    @Override
    public void run() {
         message =deal(message);
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
