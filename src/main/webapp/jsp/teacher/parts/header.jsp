<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <link rel="stylesheet" href="../../../css/bootstrap.css">
  <link rel="icon" href="../../../fonts/favicon.ico">
</head>

<body>
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li><a href="/controller?command=getPage&expectedPage=teacherpage"><fmt:message key="label.navbar.teacher.main"  bundle="${rb}"/></a></li>
        <li><a href="/controller?command=taketeacherrelatedcourses&expectedpage=relatedcourses"><fmt:message key="label.navbar.teacher.related-courses"  bundle="${rb}"/></a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message key="label.navbar.teacher.tasks-management"  bundle="${rb}"/><span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/controller?command=taketeacherrelatedcourses&expectedpage=relatedtasks"><fmt:message key="label.navbar.teacher.tasks-bycourse"  bundle="${rb}"/></a></li>
            <li><a href="/controller?command=taketeacherrelatedcourses&expectedpage=addtask"><fmt:message key="label.navbar.teacher.add-task" bundle="${rb}"/></a></li>
          </ul>
        </li>
      </ul>
      <ul class="nav navbar-nav active pull-right">
        <li>
          <form action="/controller">
            <input type="hidden" name="command" value="locale">
            <button type="submit" class="btn btn-default navbar-btn" name="locale" value="ru_RU" >RU</button>
          </form>
        </li>
        <li>
          <form action="/controller">
            <input type="hidden" name="command" value="locale">
            <button type="submit" class="btn btn-default navbar-btn" name="locale" value="en_US" >EN</button>
          </form>
        </li>
        <li>
          <form action="/controller">
            <input type="hidden" name="command" value="locale">
            <button type="submit" class="btn btn-default navbar-btn" name="locale" value="zh_CN" >CN</button>
          </form>
        </li>
        <li>
          <form method="POST" action="/controller">
            <input type="hidden" name="command" value="logout" />
            <button class="btn btn-info navbar-btn" ><fmt:message key="label.navbar.logout"  bundle="${rb}"/></button>
          </form>
        </li>
      </ul>
    </div>
  </div>
</nav>
<%--message processor--%>
<div class="container">
  <div class="col-md-2"></div>
  <div class="col-md-8">
    <c:if test="${not empty sessionScope.actionSuccessful}">
      <div class="alert alert-success" role="alert" align="center">
        <fmt:message key="${sessionScope.actionSuccessful}" bundle="${rb}"/>
      </div>
      <c:remove var="actionSuccessful" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.actionFail}">
      <div class="alert alert-danger" role="alert" align="center">
        <fmt:message key="${sessionScope.actionFail}" bundle="${rb}"/>
      </div>
      <c:remove var="actionFail" scope="session"/>
    </c:if>
  </div>
  <div class="col-md-2"></div>
</div>
</body>
</html>