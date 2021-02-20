package messageCollection;

public interface MessageType {
    int nullMessage = 0; // 0 代表message为空
    //登陆事件 1~10
    int User_Login = 1; // 用户登陆
    int User_Login_Return = 2; //用户登陆信息返回
    int Admin_Login = 3; // 管理员登陆
    int Admin_Login_Return = 4; // 管理员登陆信息返回

    //主界面用户事件 11~20
    int User_Request = 11; // 周期向服务器请求信息
    int User_Return = 12 ; // 用户请求信息返回
    int User_History = 15;// 用户查询历史记录
    int User_Sign = 16;//用户签到请求

    //主界面管理时间 21~30
    int Admin_Release = 21;// 管理发布签到
    int Admin_ChangeGroup = 22; //管理员改变群组
    int Admin_NewUser=23;
    int Admin_DelUser=24;
    int Admin_Group = 25;
    int Admin_ReNew=26;

}
