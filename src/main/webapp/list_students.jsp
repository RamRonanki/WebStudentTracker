<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, com.freshlicious.web.model.Student"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Students Data Management</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>

	<div id="wrapper">
		<div id="header">
			<h2>My Hero Academia</h2>
		</div>
	</div>

	<div id="container">
		<div id="content">

			<!--  adding a new button -->

			<input type="button" value="Add Student"
				onclick="window.location.href='add-student.jsp';return false;"
				class="add-student-button">


			<table border=1>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<c:forEach var="tempStudent" items="${student_list }">

					<c:url var="tempLink" value="StudentControllerServlet">

						<c:param name="command" value="LOAD" />
						<c:param name="studentId" value="${tempStudent.id }" />

					</c:url>
					<c:url var="deleteLink" value="StudentControllerServlet">

						<c:param name="command" value="DELETE" />
						<c:param name="studentId" value="${tempStudent.id }" />

					</c:url>
					<tr>
						<td>${tempStudent.firstName }</td>
						<td>${tempStudent.lastName }</td>
						<td>${tempStudent.email }</td>
						<td><a href="${tempLink }">Update|</a>
						 <a
							href="${deleteLink }"
							onclick="if (!(confirm('Are you Sure you want to delete the student'))) return false">Delete</a>
						</td>
					</tr>

				</c:forEach>



			</table>
		</div>
	</div>
</body>
</html>