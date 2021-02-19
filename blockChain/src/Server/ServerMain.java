package Server;

import java.io.IOException;

public class ServerMain {

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
