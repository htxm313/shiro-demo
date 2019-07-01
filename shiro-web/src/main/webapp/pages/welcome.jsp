<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Shiro认证授权管理框架</title>
    <jsp:include page="/pages/plugins/include_basepath.jsp"/>
</head>
<body>
<h1>用户登录成功，欢迎光临"<shiro:principal/>"！</h1>
</body>
</html>
