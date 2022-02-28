<%--
  Created by IntelliJ IDEA.
  User: ugalaxiacompany
  Date: 27/02/2022
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
<head>
    <title>Student Tracker App</title>

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div id="wrapper">
    <div id="header">
        <h3> Ali Nikpay University</h3>
    </div>

</div>

<div id="container">

    <div id="content">

        <%--        out new button add student--%>
        <input type="button" value=" Add Student" onclick="window.location.href='add-student-form.jsp';return false;"
               class="add-student-button">


        <table>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email Name</th>
                <th>Action</th>

            </tr>

            <c:forEach var="tempStudent" items="${STUDENT_LIST}">

                <%--                Set up alink for each student--%>
                <c:url var="tempLink" value="StudentControllerServlet">
                    <c:param name="command" value="LOAD"/>
                    <c:param name="studentId" value="${tempStudent.id}"/>

                </c:url>

                <%--                setup a link to delet a student--%>
                <c:url var="deleteLink" value="StudentControllerServlet">
                    <c:param name="command" value="DELETE"/>
                    <c:param name="studentId" value="${tempStudent.id}"/>

                </c:url>

                <tr>
                    <td>${tempStudent.firstName}
                    </td>
                    <td>${tempStudent.lastName}
                    </td>
                    <td>${tempStudent.email}
                    </td>
                    <td><a href="${tempLink}">Update</a>
                        |
                        <a href="${deleteLink}"
                           onclick="if(!(confirm('Are you sure you want to delete this student?'))) return false">Delete</a>

                    </td>

                </tr>

            </c:forEach>

        </table>
    </div>

</div>


</body>
</html>
