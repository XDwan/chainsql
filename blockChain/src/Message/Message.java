package Message;

import net.sf.json.JSONObject;

import java.io.Serializable;

public class Message implements Serializable {
    JSONObject object;
    int messageType;
    public  Message(){
        messageType = MessageType.nullMessage;
    }
    public Message(JSONObject object){
        this.messageType= (int) object.get("MessageType");
        object.remove("MessageType");
        this.object = object;
    }
    public Message(JSONObject object,int messageType){
        this.object=object;
        this.messageType=messageType;
    }
    public int getMessageType() {
        return messageType;
    }

    public JSONObject getObject() {
        return object;
    }

}
