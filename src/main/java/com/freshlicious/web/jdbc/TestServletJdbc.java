package com.freshlicious.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServletJdbc
 */
@WebServlet("/TestServletJdbc")
public class TestServletJdbc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/mydb")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		Connection myconnection = null;
		Statement mystatement = null;
		ResultSet myresult = null;

		try {
			myconnection = dataSource.getConnection();
			String sql = "select * from student";
			mystatement = myconnection.createStatement();

			myresult = mystatement.executeQuery(sql);

			while (myresult.next()) {
				String email = myresult.getString("email");
				out.println(email);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
