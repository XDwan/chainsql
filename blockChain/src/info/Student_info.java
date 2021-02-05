package info;


import Exception_Common.NameOutOfRangeException;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import setting.InfoLength_stu;

import java.util.List;

public class Student_info implements InfoLength_stu {
    String id;
    String name;
    String password;
    String classId;
    List<Course_info> course;


    public Student_info() {
        id = "";
        name = "";
        classId="";
    }

    public Student_info(JSONObject info){
        id = info.getString("id");
        name = info.getString("name");
        classId = info.getString("classId");
        password = info.getString("password");
    }

    public void setId(String id) throws NameOutOfRangeException {
        if (id.length() > InfoLength_stu.length_id) {
            throw new NameOutOfRangeException("id长度超出");
        }
        this.id = id;
    }

    public void setName(String name) throws NameOutOfRangeException {
        if (name.length() > InfoLength_stu.length_name) {
            throw new NameOutOfRangeException("姓名长度超出");
        }
        this.name = name;
    }

    public void setClassId(String classId) throws NameOutOfRangeException {
        if (classId.length()>InfoLength_stu.length_classId){
            throw new NameOutOfRangeException("班级ID长度超出");
        }
        this.classId = classId;
    }

    public JSONObject getStuInfo(JSONObject object){
        object.put("id",id);
        object.put("name",name);
        object.put("classId",classId);
        return object;
    }

    public void print(){
        System.out.println(getStuInfo(new JSONObject()));
    }

    public static void main(String[] args) {
        Student_info student_info = new Student_info();

        try {
            student_info.setName("万世杰");
            student_info.setId("19030100408");
            student_info.setClassId("19030150");
        } catch (NameOutOfRangeException e) {
            e.dialogOpen();
        }
        student_info.print();
    }
}