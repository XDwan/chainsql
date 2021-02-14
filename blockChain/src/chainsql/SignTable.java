package chainsql;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SignTable {
    private String index;
    private String id;
    private String name;
    private String group;
    private String admin;
    private String releaseTime;
    private String endTime;
    private int status;
    private String signTime;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIndex() {
        return index;
    }

    public String getGroup() {
        return group;
    }

    public String getAdmin() {
        return admin;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getStatus() {
        return status;
    }

    public String getSignTime() {
        return signTime;
    }

    public static void main(String[] args) {
        SignTable table = new SignTable();
        table.admin = "19030100408";
        table.index = "123456";
        table.name = "XDwan";
        JSONObject object = JSONObject.fromObject(table);
        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(table));
    }
}
