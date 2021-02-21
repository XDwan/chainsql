package chainsql;


import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;
import com.peersafe.chainsql.core.Table;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Date;
import java.util.List;

public class ChainSQLDeal {
    Chainsql c = new Chainsql();
    String UserTable = "UserTable";
    String AdminTable = "AdminTable";
    String SignTable = "SignTable";
    public ChainSQLDeal(String address, String secret, String url) {
        c.as(address, secret);
        c.connect(url);
        createUserTable();
        createAdminTable();
        createSignTable();
    }

    JSONObject get(String tableName,String keys){
        // 获取依关键字获取表中信息
        c.beginTran();
        JSONObject object = c.table(tableName)
                .get(c.array(keys))
                .submit(Submit.SyncCond.db_success);
        c.commit();
        return object;
    }
    // 用户有关
    public void insertUser(String name, String id, String classId) {
        // 插入一个新的用户 初始密码 123456
        c.beginTran();
        Table UserTable = new Table("UserTable");
        JSONObject object = UserTable.insert(c.array("{" +
                "'id':" + id + "," +
                "'name':" + name + "," +
                "'password':" + "123456" + "," +
                "'group':" + "personal_" + id + "," +
                "}")).submit(Submit.SyncCond.db_success);
        System.out.println(object);
        c.commit();
    }

    public void createUserTable() {
        // 创建用户信息表
        // 已存在则直接返回
        c.beginTran();
        JSONObject object = c.table("UserTable").get().submit();
        if (object.getBoolean("final_result")) {
            System.out.println(object);
            return; // 表已建立
        }
        object = c.createTable("UserTable",
                c.array("{'field':'id','type':'varchar'}",
                        "{'field':'name','type':'varchar'}",
                        "{'field':'admin','type':'varchar'}",
                        "{'field':'password','type':'varchar'}",
                        "{'field':'group','type':'varchar'}")).submit(
                Submit.SyncCond.db_success);
        System.out.println(object);
        c.commit();
    }

    public void delUser(String name, String id) {
        // 删除一个用户
        c.beginTran();
        JSONObject object = c.table("UserTable")
                .get(c.array("{" +
                        "'name':" + name +
                        "'id':" + id +
                        "}"))
                .delete()
                .submit(Submit.SyncCond.db_success);
        if (object.has("error_message")) {
            System.out.println(object);
        } else {
            System.out.println("status" + object.getString("status") + " del success");
        }
        c.commit();
    }

    public boolean renewUser(String name, String id) {
        c.beginTran();
        JSONObject object = c.table("UserTable")
                .get(c.array("{" +
                        "'name':" + name +
                        "'id':" + id +
                        "}"))
                .update("{'password':'123456'}")
                .submit(Submit.SyncCond.db_success);
        if (object.has("error_message")) {
            System.out.println(object);
            return false;
        } else {
            System.out.println("status" + object.getString("status") + " del success");
        }
        c.commit();
        return true;
    }

    // 管理员有关
    public void createAdminTable() {
        // 创建管理员信息表
        // 已存在则直接返回
        c.beginTran();
        JSONObject object = c.table("AdminTable").get().submit();
        if (object.getBoolean("final_result")) {
            System.out.println(object);
            return; // 表已建立
        }
        object = c.createTable("AdminTable",
                c.array("{'field':'id','type':'varchar'}",
                        "{'field':'name','type':'varchar'}",
                        "{'field':'password','type':'varchar'}",
                        "{'field':'adminGroup','type':'varchar'}")).submit(
                Submit.SyncCond.db_success);
        System.out.println(object);
        c.commit();
    }

    public void createAdminGroup(String adminId) {
        // 创建管理群组表
        JSONObject object = c.createTable(adminId,
                c.array("{'field':'" + adminId + "','type':'varchar'}",
                        "{'field':'groupName','type':'varchar'}",
                        "{'field':'password','type':'varchar'}",
                        "{'field':'adminGroup','type':'varchar'}")).submit(
                Submit.SyncCond.db_success);
    }

    public boolean insertAdmin(String id, String name,String group) {
        // 插入一个新的用户 初始密码 123456
        c.beginTran();
        Table UserTable = new Table("UserTable");
        JSONObject object = UserTable.insert(c.array("{" +
                "'id':" + id + "," +
                "'name':" + name + "," +
                "'password':" + "123456" + "," +
                "'group':'" + group+"'," +
                "}")).submit(Submit.SyncCond.db_success);
        createAdminGroup(id);
        if (object.has("error_message")){
            return false;
        }
        System.out.println(object);
        c.commit();
        return true;
    }

    public boolean delAdmin(String id, String name) {
        // 删除一个用户
        c.beginTran();
        JSONObject object = c.table("AdminTable")
                .get(c.array("{" +
                        "'name':" + name +
                        "'id':" + id +
                        "}"))
                .delete()
                .submit(Submit.SyncCond.db_success);
        if (object.has("error_message")) {
            System.out.println(object);
            return false;
        } else {
            System.out.println("status" + object.getString("status") + " del success");
        }
        object = c.dropTable("id")
                .submit(Submit.SyncCond.db_success);
        if (object.has("error_message")) {
            System.out.println(object);
        } else {
            System.out.println("status" + object.getString("status") + " del success");
        }
        c.commit();
        return true;
    }

    public void createSignTable() {
        c.beginTran();
        JSONObject object = c.table("SignTable").get().submit();
        if (object.getBoolean("final_result")) {
            System.out.println(object);
            return; // 表已建立
        }
        object = c.createTable("SignTable",
                c.array("{'field':'index','type':'varchar'}",
                        "{'field':'id','type':'varchar'}",
                        "{'field':'name','type':'varchar'}",
                        "{'field':'group','type':'varchar'}",
                        "{'field':'admin','type':'varchar'}",
                        "{'field':'releaseTime','type':'varchar'}",
                        "{'field':'endTime','type':'varchar'}",
                        "{'field':'status','type':'int'}",
                        "{'field':'signTime','type':'varchar'}")).submit(
                Submit.SyncCond.db_success);
        System.out.println(object);
        c.commit();
    }


    public void insertSign(String index, String id, String name, String group, String releaseTime, String endTime) {
        c.beginTran();
        Table UserTable = new Table("SignTable");
        JSONObject object = UserTable.insert(c.array("{" +
                "'index':'" + index + "'," +
                "'id':'" + id + "'," +
                "'name':'" + name + "'," +
                "'group':" + group + "," +
                "'releaseTime':'" + releaseTime + "'," +
                "'endTime':'" + endTime + "'," +
                "'status':0," +
                "'signTime':''" +
                "}")).submit(Submit.SyncCond.db_success);
        createAdminGroup(id);
        System.out.println(object);
        c.commit();
    }


    public boolean releaseSign(String group, long lastMin) {
        Date date = new Date();
        String releaseTime = String.valueOf(date.getTime());
        date = new Date(date.getTime() + lastMin * 60 * 1000);
        String endTime = String.valueOf(date.getTime());
        JSONObject object = c.table("UserTable")
                .get(
                        c.array(Raw.array(Raw.key("group",group)))
                )
                .submit(Submit.SyncCond.db_success);
        if (object.has("error_message")) {
            System.out.println(object);
            return false;
        }
        JSONArray array = object.getJSONArray("lines");
        for (Object i : array) {
            JSONObject object1 = new JSONObject(i);
            String name = ((JSONObject) i).getString("name");
            String id = ((JSONObject) i).getString("id");
            String admin = ((JSONObject) i).getString("admin");
            insertSign(admin + releaseTime, id, name, group, releaseTime, endTime);
        }

        return true;
    }

    public void updateSignTime(String index) {
        c.beginTran();
        Date date = new Date();
        String signTime = String.valueOf(date.getTime());
        JSONObject object = c.table("SignTable"
        ).get(
                c.array(
                        Raw.array(Raw.key("index",index))
                )
        ).update(
                Raw.array(Raw.key("signTime",signTime),Raw.key("status",1))
        ).submit(Submit.SyncCond.db_success);
        c.commit();
    }

    public String getUserIndex(String id){
        // 通过id 查询最近签到
        c.beginTran();
        String index = "null";
        JSONObject object = c.table("SignTable").get(
                c.array(
                        Raw.array(Raw.key("id",id))
                )
        ).order(
                c.array(
                        Raw.order("releaseTime",-1)
                )
        ).submit(Submit.SyncCond.db_success);
        JSONArray array = Raw.getLines(object);
        object = array.getJSONObject(0);
        index = object.getString("index");
        c.commit();
        return index;
    }

    public JSONObject getUser(String id){
        JSONObject object = get(UserTable,Raw.array("id",id));
        JSONArray array = Raw.getLines(object);
        object = (JSONObject) array.get(0);
        return object;
    }

    public JSONObject getAdmin(String id){
        JSONObject object = get(AdminTable,Raw.array("id",id));
        JSONArray array = Raw.getLines(object);
        object = (JSONObject) array.get(0);
        return object;
    }

    public JSONObject getSignStatus(String index){
        c.beginTran();
        JSONObject object = c.table("SignTable").get(
                c.array(
                        Raw.array(Raw.key("index",index))
                )
        ).order(
                c.array(
                        Raw.order("releaseTime",-1)
                )
        ).submit(Submit.SyncCond.db_success);
        JSONArray array = Raw.getLines(object);
        object = array.getJSONObject(0);
        c.commit();
        return object;
    }

    public JSONArray getUserHistory(String id){
        c.beginTran();
        JSONObject object = c.table("SignTable").get(
                c.array(
                        Raw.array(Raw.key("id",id))
                )
        ).order(
                c.array(
                        Raw.order("releaseTime",-1)
                )
        ).submit(Submit.SyncCond.db_success);
        if (object.has("error_message")){
            return null;
        }
        JSONArray array = Raw.getLines(object);
        c.commit();
        return array;
    }

    public JSONArray getGroupList(String group){
        c.beginTran();
        JSONObject object = c.table("UserTable").get(
                c.array(
                        Raw.array(Raw.key("group",group))
                )
        ).submit(Submit.SyncCond.db_success);
        if (object.has("error_message")){
            return null;
        }
        JSONArray array = Raw.getLines(object);
        c.commit();
        return array;
    }
}
