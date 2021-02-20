package Server;

import chainsql.ChainSQLDeal;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) {

        String secret="";
        String address="";
        String url = "";
        ChainSQLDeal c = new ChainSQLDeal(address,secret,url);
        int port = 5526;
        try {
            Thread t = new ServerConnection(port,c);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
