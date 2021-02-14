package chainsql;


import org.json.JSONArray;
import org.json.JSONObject;

public class Raw {

    static String addString(String s){
        return "'"+s+"'";
    }

    static String key(String key, String value){
        return addString(key)+":"+addString(value);
    }

    static String key(String key,int value){
        return addString(key)+":"+ value;
    }

    static String array(String key,String ...keys){
        String raw = "{";
        raw+=key;
        for (String i: keys){
            raw+= ","+i;
        }
        raw+="}";
        return raw;
    }
    static String order(String key,int value){
        String raw  ;
        raw = "{"+key+":"+value+"}";
        return raw;
    }

    static JSONArray getLines(JSONObject object){
        JSONArray array = object.getJSONArray("lines");
        return array;
    }
}
