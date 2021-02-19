package messageCollection;

public interface MessageType {
    int nullMessage = 0; // 0 代表message为空
    //登陆事件 1~10
    int User_Login = 1; // 用户登陆
    int User_Login_Return = 2; //用户登陆信息返回
    int Admin_Login = 3; // 管理员登陆
    int Admin_Login_Return = 4; // 管理员登陆信息返回

    //主界面事件 11~20
    int User_Request = 11; // 周期向服务器请求信息
    int User_Return = 12 ; // 用户请求信息返回
    int Admin_Request = 13;
    int Admin_Return = 14;
}
