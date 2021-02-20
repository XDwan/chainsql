package Server;

import chainsql.ChainSQLDeal;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection extends Thread {
    ServerSocket serverSocket;
    ChainSQLDeal c ;

    public ServerConnection(int port, ChainSQLDeal c) throws IOException {
        serverSocket = new ServerSocket(port);
        this.c = c;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("建立连接");
                Socket server = serverSocket.accept();
                ServerDealing dealing = new ServerDealing(server,c);
                Thread t = new Thread(dealing);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String secret="";
        String address="";
        String url = "";
        ChainSQLDeal c = new ChainSQLDeal(address,secret,url);
        int port = 5526;
        try {
            Thread t = new ServerConnection(port, c);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
