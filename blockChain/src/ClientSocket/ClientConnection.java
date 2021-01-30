package ClientSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
    Socket client ;
    public ClientConnection(){
        int port = 5526;
        String ip = "192.168.85.134";
        try {
            client = new Socket(ip,port);
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF("服务器好，这里是XDwan");
            System.out.println(in.readUTF());
            client.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ClientConnection();
    }

}
