package chainsql;


import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;
import com.peersafe.chainsql.core.Table;
import org.json.JSONObject;

public class ChainSQLDeal {
    Chainsql c = new Chainsql();

    public ChainSQLDeal(String address,String secret,String url){
        c.as(address,secret);
        c.connect(url);
        createStuTable();
        createTehTable();
    }
    // 学生表有关
    public void createStu(String name,String id,String classId){
        // 插入一个新的学生 初始密码 123456
        c.beginTran();
        Table stuTable = new Table("StuTable");
        JSONObject object = stuTable.insert(c.array("{"+
                "id:"+id+","+
                "name:"+name+","+
                "classId:"+classId+","+
                "password:"+"123456"+","+
                "tableIndex:"+"personal_"+id+","+
                "}")).submit(Submit.SyncCond.db_success);
        System.out.println(object);
        createStuSign("personal_"+id);
        c.commit();
    }

    public void createStuSign(String tableName){
        // 创建个人签到表 表名为 personal_19030100408
        // status 0 为未签到 1 为签到成功
        JSONObject object = c.createTable(tableName,
                c.array(
                        "{'field':'time','type':'varchar'}",
                        "{'field':'id','type':'varchar'}",
                        "{'field':'status','type':'int'}"
                )
                ).submit(Submit.SyncCond.db_success);
        System.out.println(object);
    }

    public void createStuTable(){
        // 创建学生信息表
        c.beginTran();
        JSONObject object = c.createTable("StuTable",
                c.array("{'field':'id','type':'varchar'}",
                        "{'field':'name','type':'varchar'}",
                        "{'field':'classId','type':'varchar'}",
                        "{'field':'password','type':'varchar'}",
                        "{'field':'tableIndex','type':'varchar'}")).submit(
                                Submit.SyncCond.db_success);
        System.out.println(object);
        c.commit();
    }

    // 老师表建立
    public void createTehTable(){
        c.beginTran();
        JSONObject object = c.createTable("TehTable",
                c.array("{'field':'id','type':'varchar'}",
                        "{'field':'name','type':'varchar'}",
                        "{'field':'classId','type':'varchar'}",
                        "{'field':'password','type':'varchar'}",
                        "{'field':'tableIndex','type':'varchar'}")).submit(
                Submit.SyncCond.db_success);
        System.out.println(object);
        c.commit();
    }
}
