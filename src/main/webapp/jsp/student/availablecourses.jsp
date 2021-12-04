%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Available courses</title>
</head>
<body>
<jsp:include page="../../jsp/student/parts/header.jsp"/>

<H1 align="center"><fmt:message key="label.availablecourses.h1" bundle="${rb}"/></H1>

<div class="container theme-showcase" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4">

    </div>
    <div class="col-md-4"></div>
    <div class="row">
        <div class="col-md-12">
            <br/>
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th><fmt:message key="label.availablecourses.type-language" bundle="${rb}"/></th>
                    <th><fmt:message key="label.availablecourses.status" bundle="${rb}"/></th>
                    <th><fmt:message key="label.availablecourses.title" bundle="${rb}"/></th>
                    <th><fmt:message key="label.availablecourses.description" bundle="${rb}"/></th>
                    <th><fmt:message key="label.allcourses.teacher-fullname" bundle="${rb}"/></th>
                    <th width="80"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.availableCourses}" var="courses">
                    <tr>
                        <td>${courses.type}</td>
                        <td>${courses.status}</td>
                        <td>${courses.title}</td>
                        <td>${courses.description}</td>
                        <td>${courses.teacher.lastName} ${courses.teacher.firstName}</td>
                        <td>
                            <form method="post" action="/controller">
                                <input type="hidden"  name="command" value="joincourse" />
                                <input type="hidden"  name="id_course" value="${courses.id}" />
                                <button type="submit" class="btn btn-success"><fmt:message key="label.availablecourses.join-course" bundle="${rb}"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>
<jsp:include page="../../jsp/student/parts/footer.jsp"/>
</body>
</html>
