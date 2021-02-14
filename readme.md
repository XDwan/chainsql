[toc]

# 基于chainsql的考勤记录系统


# 客户端结构（登陆->主界面）

## *登陆*

### 用户登陆（选择用户按钮）

1. 输入账号 密码 确认登陆（与服务器建立websocket联系）
2. 向服务器传递message信息（包含账号 密码）
3. 服务器确认账号密码是否正确（数据库查询）
4. 服务器返回账号密码（message success/error）
5. 客户端接收message（登入主界面，关闭登陆窗口）
6. 进入主界面

### 管理员登陆（选择管理员按钮）

1. 输入账号 密码 确认登陆（与服务器建立websocket联系）
2. 向服务器传递message信息（包含学生账号 密码）
3. 服务器确认账号密码是否正确（数据库查询）
4. 服务器返回账号密码（message success/error）
5. 客户端接收message（登入主界面，关闭登陆窗口）
6. 进入主界面

### 注册

管理员设置注册选项

## *主界面*

### 用户界面（每秒向服务器提交请求更新面板）

1. 签到按钮（向服务器提交签到请求，更新当前状态）
> - 当前只能存在一个签到任务（只设置一个签到任务）
>
2. 状态面板
> 显示当前状态（空闲、签到中）
>
> 签到状态（无签到、已签到、未签到）
>
> 显示用户基本信息（姓名 工号 当前状态）

3. 查看签到历史

> - 按群组区分查询
> - 按时间序列查询

### 管理员界面（每秒向服务器提交请求更新面板）

1. 发布按钮（选择群组发布签到）

2. 查询按钮（查询某群组签到情况）

3. 群组管理（增添删除群组）

4. 人员管理（添加或移除人员）（入网）



# 基础信息设置 info

## 用户

> Userinfo
> - 姓名 20
> - 工号 11
> - 登陆密码 20
> - 群组 group 

## 管理员

> AdminInfo
> - 姓名 20
> - 工号 11
> - 登陆密码 20
> - 多群组 groups

# 服务器端

## 服务器响应

1.  登陆服务器

> - 登陆信息接收
> - 账号信息验证

2.  签到、签退
> - 发送签到信息
> - 返回签到确认信息

3. 入网
> - 存入数据库中
> - 与账号进行绑定

4. 处理签到、签退
> - 发送发起信息（所属群组）
> - 向所有有关人发送签到信息（带声音提示）
>
5. 查询
> 1. 考勤记录查询
> 2. 考勤人员查询（是否签到）
> 3. 考勤记录导出

## 后台运行
> - 签到队列更新
> - 响应事件队列
>

## 数据库结构

> 用户信息表 保存用户信息用于登陆验证，以及个人签到情况查询
>
> 签到索引表 用于保存所有签到表索引，可按群组 索引查询
>
> 管理员信息表 保存管理员信息 用于登陆验证，以及用户签到情况查询
> 
> 签到状态 0：未签到 1：签到成功

### 用户信息表 UserTable 

> - 工号 id
> - 姓名 name
> - 登陆密码 password
> - 群组 group 
> - 管理员 admin

### 管理员信息表 AdminTable

> - 工号 id
> - 姓名 name
> - 登陆密码 password
> - 群组 groups

### 管理员属下群组表  AdminId
> - 群组名

### 签到表索引（全索引）（通过索引）

> - 签到索引 index
> - 签到人 id
> - 姓名 name
> - 群组 group
> - 管理员 admin
> - 发布时间 releaseTime
> - 结束时间 endTime
> - 签到状态 status
> - 签到时间 signTime

### 数据库操作

> - 增删查改用户 insertUser checkUser renewUser 
> - 查询表(id name 时间区间) check
> - 更新表 updateStatus
> 

## chainSQL

依chainSQL教程进行搭建 各个节点设置数据库





