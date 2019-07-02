<%@ page pageEncoding="UTF-8"  import="java.util.*" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Shiro认证授权管理框架</title>
    <jsp:include page="/pages/plugins/include_basepath.jsp"/>
</head>
<body>
<%
    Enumeration<String> names = session.getAttributeNames();
    while (names.hasMoreElements()) {
        String name = names.nextElement() ;
        out.println("<h2>" + name + " = " +session.getAttribute(name) + "</h2>");
    }
%>

<h1>用户登录成功，欢迎光临"<shiro:principal/>"！，系统<a href="/logout.servlet">注销</a> </h1>
<shiro:authenticated>
    <h3>您已经登录过了！</h3>
</shiro:authenticated>

<shiro:notAuthenticated>
    <h3>您还未登录，显示登陆表单！</h3>
</shiro:notAuthenticated>

<shiro:hasAnyRoles name="member">
    <h3>当前用户拥有"member"的角色</h3>
</shiro:hasAnyRoles>

<shiro:hasPermission name="member:add">
    <h3>当前用户拥有"member:add"的权限!</h3>
</shiro:hasPermission>


</body>
</html>
