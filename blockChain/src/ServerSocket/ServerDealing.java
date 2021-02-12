package ServerSocket;

import messageCollection.Message;
import messageCollection.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerDealing implements Runnable{
    Message message;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public ServerDealing(Socket socket) {
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
        switch (message.getMessageType()) {
            case MessageType.Stu_Login:

                break;
            case MessageType.Teacher_Login:

                break;

            case MessageType.Stu_Request:

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
