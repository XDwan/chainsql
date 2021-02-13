package chainsql;


import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;
import com.peersafe.chainsql.core.Table;
import net.sf.json.JSON;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class ChainSQLDeal {
    Chainsql c = new Chainsql();

    public ChainSQLDeal(String address, String secret, String url) {
        c.as(address, secret);
        c.connect(url);
        createUserTable();
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
        if (object.getBoolean("final_result"))
        {
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

    public void delUser(String name,String id){
        // 删除一个用户
        c.beginTran();
        JSONObject object = c.table("UserTable")
                .get(c.array("{"+
                        "'name':"+name+
                        "'id':"+id+
                        "}"))
                .delete()
                .submit(Submit.SyncCond.db_success);
        if (object.has("error_message")){
            System.out.println(object);
        }else {
            System.out.println("status"+object.getString("status")+" del success");
        }
        c.commit();
    }

    public void renewUser(String name,String id){
        c.beginTran();
        JSONObject object = c.table("UserTable")
                .get(c.array("{"+
                        "'name':"+name+
                        "'id':"+id+
                        "}"))
                .update("{'password':'123456'}")
                .submit(Submit.SyncCond.db_success);
        if (object.has("error_message")){
            System.out.println(object);
        }else {
            System.out.println("status"+object.getString("status")+" del success");
        }
        c.commit();
    }

    // 管理员有关
    public void createAdminTable(){
        // 创建管理员信息表
        // 已存在则直接返回
        c.beginTran();
        JSONObject object = c.table("AdminTable").get().submit();
        if (object.getBoolean("final_result"))
        {
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

    public void createAdminGroup(String adminId){
        // 创建管理群组表
        JSONObject object = c.createTable(adminId,
                c.array("{'field':'"+adminId+"','type':'varchar'}",
                        "{'field':'groupName','type':'varchar'}",
                        "{'field':'password','type':'varchar'}",
                        "{'field':'adminGroup','type':'varchar'}")).submit(
                Submit.SyncCond.db_success);
    }

    public void insertAdmin(String id,String name){
        // 插入一个新的用户 初始密码 123456
        c.beginTran();
        Table UserTable = new Table("UserTable");
        JSONObject object = UserTable.insert(c.array("{" +
                "'id':" + id + "," +
                "'name':" + name + "," +
                "'password':" + "123456" + "," +
                "'group':" + "''," +
                "}")).submit(Submit.SyncCond.db_success);
        createAdminGroup(id);
        System.out.println(object);
        c.commit();
    }

    public void delAdmin(String id,String name){
        // 删除一个用户
        c.beginTran();
        JSONObject object = c.table("AdminTable")
                .get(c.array("{"+
                        "'name':"+name+
                        "'id':"+id+
                        "}"))
                .delete()
                .submit(Submit.SyncCond.db_success);
        if (object.has("error_message")){
            System.out.println(object);
            return ;
        }else {
            System.out.println("status"+object.getString("status")+" del success");
        }
        object = c.dropTable("id")
                .submit(Submit.SyncCond.db_success);
        if (object.has("error_message")){
            System.out.println(object);
        }else {
            System.out.println("status"+object.getString("status")+" del success");
        }
        c.commit();
    }

    public void createSignTable(){
        c.beginTran();
        JSONObject object = c.table("SignTable").get().submit();
        if (object.getBoolean("final_result"))
        {
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


    public void insertSign(String index,String id,String name,String group,String releaseTime,String endTime){
        c.beginTran();
        Table UserTable = new Table("SignTable");
        JSONObject object = UserTable.insert(c.array("{" +
                "'index':'" + index + "'," +
                "'id':'" + id + "'," +
                "'name':'" + name + "'," +
                "'group':" + group + "," +
                "'releaseTime':'" + releaseTime + "'," +
                "'endTime':'" +endTime+ "'," +
                "'status':0,"+
                "'signTime':''" +
                "}")).submit(Submit.SyncCond.db_success);
        createAdminGroup(id);
        System.out.println(object);
        c.commit();
    }


    public void releaseSign(String group,long lastMin){
        Date date = new Date();
        String releaseTime = String.valueOf(date.getTime());
        date = new Date(date.getTime()+lastMin*60*1000);
        String endTime = String.valueOf(date.getTime());
        JSONObject object = c.table("UserTable")
                .get(
                    c.array("{" +
                            "'group':'" +
                            group+
                            "'}")
                )
                .submit(Submit.SyncCond.db_success);
        if (object.has("error_message")){
            System.out.println(object);
            return;
        }
        List user = (List) object.get("lines");
        for (Object i : user){
            i = (JSONObject) i;
            String name = ((JSONObject) i).getString("name");
            String id = ((JSONObject) i).getString("name");
            String admin = ((JSONObject) i).getString("name");
            insertSign(admin+releaseTime,id,name,group,releaseTime,endTime);
        }

    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        date = new Date(date.getTime()+1*60*1000);
        System.out.println(date);
    }
}
