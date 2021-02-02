package ClientSocket;

import messageCollection.Message;
import SwingFrame.Warning;

import java.io.*;
import java.net.Socket;

public class ClientConnection {
    Socket client ;
    int port;
    String ip;
    public ClientConnection(){

    }

    public void set(int port,String ip){
        this.port = port;
        this.ip=ip;
    }

    public void set(){// 不带IP与port默认为ConnectionSet中的ip
        this.port = ConnectionSet.port;
        this.ip = ConnectionSet.ip;
    }

    public void connect(){
        try {
            client = new Socket(ip,port);
        } catch (IOException e) {
            e.printStackTrace();
            new Warning("网络连接错误"+e.getLocalizedMessage(),"网络连接错误");
        }
    }

    public Message send(Message message){
        Message return_message = new Message();
        try {
            ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
            os.writeObject(message);
            ObjectInputStream is = new ObjectInputStream(client.getInputStream());
            message =(Message) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            new Warning(e.getMessage(),"传递出错");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Warning(e.getMessage(),"类出错");
        }
        return return_message;
    }

    public static void main(String[] args) {
        new ClientConnection();
    }

}
