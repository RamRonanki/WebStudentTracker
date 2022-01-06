package com.freshlicious.web.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.freshlicious.web.jdbc.StudentDBUtil;
import com.freshlicious.web.model.Student;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/mydb")
	private DataSource dataSource;

	private StudentDBUtil studentDbutil;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			studentDbutil = new StudentDBUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String theCommand = request.getParameter("command");

			if (theCommand == null) {
				theCommand = "LIST";
			}

			switch (theCommand) {

			case "LIST":
				listStudents(request, response);
				break;
			
			case "LOAD":
				loadStudent(request, response);
				break;
			case "UPDATE":
				updateStudent(request, response);
				break;
			case "DELETE":
				deleteStudent(request, response);
				break;
			default:
				listStudents(request, response);
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	        try {
	            // read the "command" parameter
	            String theCommand = request.getParameter("command");
	                    
	            // route to the appropriate method
	            switch (theCommand) {
	                            
	            case "ADD":
	                addStudent(request, response);
	                break;
	                                
	            default:
	                listStudents(request, response);
	            }
	                
	        }
	        catch (Exception exc) {
	            throw new ServletException(exc);
	        }
	        
	    }

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = Integer.parseInt(request.getParameter("studentId"));
		
		studentDbutil.deleteStudent(id);

		listStudents(request, response);
		
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Student tempStudent = new Student(id, firstName, lastName, email);

		studentDbutil.updateStudent(tempStudent);

		listStudents(request, response);

	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("studentId"));

		Student tempStudent = studentDbutil.loadStudent(id);

		request.setAttribute("loaded_student", tempStudent);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/update_student_form.jsp");
		dispatcher.forward(request, response);

	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Student tempStudent = new Student(firstName, lastName, email);

		studentDbutil.addStudent(tempStudent);

		// send back to main page (the student list)
        // SEND AS REDIRECT to avoid multiple-browser reload issue
        response.sendRedirect(request.getContextPath() + "/StudentControllerServlet?command=LIST");

	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Student> students = studentDbutil.getStudents();

		request.setAttribute("student_list", students);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_students.jsp");

		dispatcher.forward(request, response);

	}

}
