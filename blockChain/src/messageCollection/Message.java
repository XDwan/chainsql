package messageCollection;

import net.sf.json.JSONObject;

import java.io.Serializable;

public class Message implements Serializable {
    JSONObject info;
    int messageType;
    public  Message(){
        messageType = MessageType.nullMessage;
    }
    public Message(JSONObject info){
        this.messageType= (int) info.get("MessageType");
        info.remove("MessageType");
        this.info = info;
    }
    public Message(JSONObject info,int messageType){
        this.info=info;
        this.messageType=messageType;
    }
    public int getMessageType() {
        return messageType;
    }

    public JSONObject getInfo() {
        return info;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void addInfo(String key,Object value){
        info.put(key,value);
    }
}
