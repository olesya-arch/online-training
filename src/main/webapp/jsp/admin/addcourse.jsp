<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add new course</title>
</head>
<body>
<jsp:include page="../../jsp/admin/parts/header.jsp"/>

<H1 align="center"><fmt:message key="label.addcourse.title" bundle="${rb}"/></H1>
<div class="container" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4">

        <form name="addNewCourseForm" method="POST" action="/controller">
            <input type="hidden" name="command" value="addcourse"/>
            <input type="hidden" name="addCourse" value="go"/>

            <div class="control-group">
                <!-- Title -->
                <label class="control-label" for="c_title"><fmt:message
                        key="label.editcourse.course-title" bundle="${rb}"/><span class="required">*</span></label>
                <div class="controls">
                    <input type="text" class="form-control" id="c_title" name="c_title"
                           required="" pattern="[А-Яа-я\w\s.,?!-+#%_()]{5,75}"/>
                    <p class="help-block"><fmt:message key="label.editcourse.course-title-help" bundle="${rb}"/></p>
                </div>
            </div>

            <div class="control-group">
                <!-- Description -->
                <label class="control-label" for="c_description"><fmt:message
                        key="label.editcourse.course-description" bundle="${rb}"/><span class="required">*</span></label>
                <div class="controls">
                    <input type="text" class="form-control" id="c_description" name="c_description"
                           required="" pattern="[А-Яа-я\w\s.,?!-+#%_()]{5,275}"/>
                    <p class="help-block"><fmt:message key="label.editcourse.course-description-help" bundle="${rb}"/></p>
                </div>
            </div>
            <div class="control-group">
                <!-- Course type -->
                <label class="control-label" for="id_type"><fmt:message key="label.editcourse.course-type"
                                                                            bundle="${rb}"/><span
                        class="required">*</span></label>
                <div class="controls">
                    <select name="id_type" id="id_type" required class="form-control">
                        <c:forEach items="${sessionScope.allTypes}" var="types">
                            <option value="${types.id}">${types.language} </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <!-- Related teacher -->
                <label class="control-label"
                       for="teacher_id"><fmt:message key="label.editcourse.teacher"
                                                     bundle="${rb}"/></label>
                <div class="controls">
                    <select name="teacher_id" id="teacher_id" required class="form-control">
                        <option value="0"><fmt:message key="label.addcourse.course-without-teacher" bundle="${rb}"/></option>
                        <c:forEach items="${sessionScope.allTeachers}" var="teachers">
                            <option value="${teachers.id}">${teachers.lastName} ${teachers.firstName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <!-- Course status -->
                <label class="control-label" for="course_status"><fmt:message key="label.editcourse.status"
                                                                              bundle="${rb}"/><span
                        class="required">*</span></label>
                <div class="controls">
                    <select name="course_status" id="course_status" required class="form-control">
                        <option value="OPEN"><fmt:message key="label.addcourse.course-status-open" bundle="${rb}"/></option>
                        <option value="IN_PROCESS"><fmt:message key="label.addcourse.course-status-in-process" bundle="${rb}"/></option>
                        <option value="CLOSED"><fmt:message key="label.addcourse.course-status-closed" bundle="${rb}"/></option>
                    </select>
                </div>
            </div>


            <div class="control-group">
                <!-- Course Availability -->
                <label class="control-label" for="is_available"><fmt:message
                        key="label.editcourse.availability" bundle="${rb}"/><span class="required">*</span></label>
                <div class="controls">
                    <select name="is_available" id="is_available" required class="form-control">
                        <option value="1"><fmt:message key="label.addcourse.course-available" bundle="${rb}"/></option>
                        <option value="0"><fmt:message key="label.addcourse.course-unavailable" bundle="${rb}"/></option>
                    </select>
                </div>
            </div>

            <br>
            <p><span class="required">*</span> - <fmt:message key="label.form.required-fields" bundle="${rb}"/></p>
            <button type="submit" class="btn btn-danger"><fmt:message key="label.editcourse.add-btn"
                                                                      bundle="${rb}"/></button>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>
<jsp:include page="../../jsp/admin/parts/footer.jsp"/>
</body>
</html>