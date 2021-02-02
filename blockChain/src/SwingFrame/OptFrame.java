package SwingFrame;

import messageCollection.Message;
import messageCollection.MessageType;
import net.sf.json.JSONObject;

import javax.swing.*;

public class OptFrame extends JFrame implements Runnable {
    JSONObject account = new JSONObject();

    public OptFrame(Message reMessage){
        account = reMessage.getObject();
    }

    @Override
    public void run() {

    }

    public void frameShow(){

    }
}
