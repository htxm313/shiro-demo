<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Shiro认证授权管理框架</title>
    <jsp:include page="/WEB-INF/pages/plugins/include_basepath.jsp"/>
</head>
<body>
<%!
    public static final String LOGIN_URL = "login_handle.action" ;
%>
${errors}
<form action="<%=LOGIN_URL%>" method="post">
    用户名：<input type="text" name="mid" id="mid"><br>
    密码：<input type="password" name="password" id="password"><br>
    <button type="submit">登录</button>
    <button type="reset">重置</button>
</form>
</body>
</html>
