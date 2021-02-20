package Server;

import chainsql.ChainSQLDeal;
import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

                break;

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
