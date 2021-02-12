package chainsql;


import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;
import com.peersafe.chainsql.core.Table;
import org.json.JSONObject;

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
                c.array("{'field':'id','type':'varchar'}",
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
                "'group':" + "personal_" + id + "," +
                "}")).submit(Submit.SyncCond.db_success);
        createAdminGroup(id);
        System.out.println(object);
        c.commit();
    }


}
