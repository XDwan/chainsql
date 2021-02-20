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

    public String getDetail(String key){
        return info.getString(key);
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void clear(){
        info.clear();
    }

    public void add(String key, Object value){
        info.put(key,value);
    }

    public void addBasicInfo(JSONObject info){
        add("name",info.getString("name"));
        add("id",info.getString("id"));
    }



    public void buildLogin(int loginType, String id, String password){
        setMessageType(loginType);
        add("id",id);
        add("password",password);
    }

    public void buildUserRequest(JSONObject info){
        setMessageType(MessageType.User_Request);
        addBasicInfo(info);
    }
}
