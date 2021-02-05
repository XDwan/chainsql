package messageCollection;

public interface MessageType {
    int nullMessage = 0; // 0 代表message为空
    //登陆事件 1~10
    int Stu_Login = 1; // 学生登陆
    int Stu_Login_Return = 2; //学生登陆信息返回
    int Teacher_Login = 3; // 教师登陆
    int Teacher_Login_Return = 4; // 教师登陆信息返回

    //主界面事件 11~20
    int Stu_Request = 11; // 周期向服务器请求信息
    int Stu_Return = 12 ; // 学生请求信息返回
}
