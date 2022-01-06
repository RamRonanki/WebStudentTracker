<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Student</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>My Hero Academia</h2>
		</div>
	</div>
	<div id="container">
		<h3>update Student</h3>
		<form action="StudentControllerServlet" method="GET">
			<input type="hidden" name="command" value="UPDATE">
			<input type="hidden" name="studentId" value="${loaded_student.id }">
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" value=${loaded_student.firstName }></td>
					</tr>
				</tbody>
				<tbody>
					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" value=${loaded_student.lastName }></td>
					</tr>
				</tbody>
				<tbody>
					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" value=${loaded_student.email }></td>
					</tr>
				</tbody>
				<tbody>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save"></td>
					</tr>
				</tbody>
			</table>
		</form>
		<div style="clear: both;"></div>
		<p>
		<a href="StudentControllerServlet">Back to List</a>
		</p>
	</div>
</body>
</html>