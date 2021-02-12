package ServerSocket;

import messageCollection.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection extends Thread {
    ServerSocket serverSocket;

    public ServerConnection(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("建立连接");
                Socket server = serverSocket.accept();
                ServerDealing dealing = new ServerDealing(server);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 5526;
        try {
            Thread t = new ServerConnection(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
