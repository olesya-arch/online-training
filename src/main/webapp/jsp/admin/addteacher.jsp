<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>

<c:if test="${empty sessionScope.failEmail}">
    <c:set var="failEmail" value="" scope="session"/>
</c:if>
<c:if test="${empty sessionScope.failFirstName}">
    <c:set var="failFirstName" value="" scope="session"/>
</c:if>
<c:if test="${empty sessionScope.failLastName}">
    <c:set var="failLastName" value="" scope="session"/>
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Registration</title>
</head>
<body>
<jsp:include page="../../jsp/admin/parts/header.jsp"/>
<H1 align="center"><fmt:message key="label.addteacher.title" bundle="${rb}"/></H1>
<div class="container" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4">

        <form name="teacherRegisterForm" method="POST" action="/controller">
            <input type="hidden" name="command" value="addteacher"/>
            <fieldset>

                <div class="control-group">
                    <!-- E-mail -->
                    <label class="control-label" for="e_mail"><fmt:message key="label.registration.email" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="email" id="e_mail" name="e_mail" value="${failEmail}" required="" class="form-control" placeholder="email@example.com" />
                        <p class="help-block"><fmt:message key="label.t.registration.email.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Password -->
                    <label class="control-label" for="u_password"><fmt:message key="label.registration.password" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="password" id="u_password" name="u_password" required="" class="form-control"  pattern="^[a-zA-Z0-9-_\.]{4,20}$"/>
                        <p class="help-block"><fmt:message key="label.registration.password.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Repeat password -->
                    <label class="control-label" for="check_password"><fmt:message key="label.registration.repeatpassword" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="password" id="check_password" name="check_password" required="" class="form-control" pattern="^[a-zA-Z0-9-_\.]{4,20}$"/>
                        <p class="help-block"><fmt:message key="label.registration.t.repeatpassword" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- First Name -->
                    <label class="control-label" for="first_name"><fmt:message key="label.registration.name" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" id="first_name" name="first_name" value="${failFirstName}" required="" class="form-control" placeholder="Иван" pattern="^[А-ЯA-Z][a-яa-z]{2,30}"/>
                        <p class="help-block"><fmt:message key="label.registration.name.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Last Name -->
                    <label class="control-label" for="last_name"><fmt:message key="label.registration.last-name" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" id="last_name" name="last_name" value="${failLastName}" required="" class="form-control" placeholder="Иванов" pattern="^[А-ЯA-Z][a-яa-z]{2,30}"/>
                        <p class="help-block"><fmt:message key="label.registration.last-name.help" bundle="${rb}"/></p>
                    </div>
                </div>
                <p><span class="required">*</span> - <fmt:message key="label.form.required-fields" bundle="${rb}"/></p>
                <button type="submit" class="btn btn-success" ><fmt:message key="label.login.t.registration" bundle="${rb}"/></button>
            </fieldset>
        </form>
    </div>
    <div class="col-md-4"></div>
    <jsp:include page="../../jsp/admin/parts/footer.jsp"/>
</body>
</html>
