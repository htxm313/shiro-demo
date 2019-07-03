<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Shiro认证授权管理框架</title>
    <jsp:include page="/WEB-INF/pages/plugins/include_basepath.jsp"/>
</head>
<body>
<%!
    public static final String LOGIN_URL = "login.action" ;
%>
${errors == 'com.yootk.ssm.filter.authc.exception.RandomCodeException'?"验证码输入错误！":""}
${errors == 'org.apache.shiro.authc.UnknownAccountException'?"未知的账户异常！":""}
${errors == 'org.apache.shiro.authc.IncorrectCredentialsException'?"错误的用户名或密码！":""}
${errors == 'org.apache.shiro.authc.LockedAccountException'?"账户已经锁定！":""}
<form action="<%=LOGIN_URL%>" method="post">
    用户名：<input type="text" name="mid" id="mid"><br>
    密码：<input type="password" name="password" id="password"><br>
    验证码：<input type="text" name="code" id="code" maxlength="4" size="4" ><img src="ImageCode"><br>
    <input type="checkbox" id="rememberme" name="rememberme" value="true" />记住我 </br>
    <button type="submit">登录</button>
    <button type="reset">重置</button>
</form>
</body>
</html>
