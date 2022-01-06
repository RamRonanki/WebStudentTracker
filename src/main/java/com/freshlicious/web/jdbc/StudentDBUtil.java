package com.freshlicious.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.freshlicious.web.model.Student;

public class StudentDBUtil {

	private DataSource dataSource;

	public StudentDBUtil(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public List<Student> getStudents() throws Exception {

		List<Student> students = new ArrayList<>();

		Connection mycon = null;
		Statement myst = null;
		ResultSet myrs = null;

		try {
			mycon = dataSource.getConnection();

			String sql = "select * from student order by last_name";
			myst = mycon.createStatement();
			myrs = myst.executeQuery(sql);

			while (myrs.next()) {
				int id = myrs.getInt("id");
				String firstname = myrs.getString("first_name");
				String lastname = myrs.getString("last_name");
				String email = myrs.getString("email");
				Student st = new Student(id, firstname, lastname, email);
				students.add(st);

			}

			return students;
		} finally {
			close(mycon, myst, myrs);
		}
	}

	private void close(Connection mycon, Statement myst, ResultSet myrs) {
		try {
			if (myrs != null) {
				myrs.close();
			}

			if (myst != null) {
				myst.close();
			}

			if (mycon != null) {
				mycon.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addStudent(Student tempStudent) throws Exception {

		Connection mycon = null;
		PreparedStatement myst = null;

		try {
			mycon = dataSource.getConnection();

			String sql = "insert into student " + "(first_name,last_name,email) " + "values(?,?,?)";

			myst = mycon.prepareStatement(sql);

			myst.setString(1, tempStudent.getFirstName());
			myst.setString(2, tempStudent.getLastName());
			myst.setString(3, tempStudent.getEmail());

			myst.execute();

		} finally {
			close(mycon, myst, null);
		}

	}

	public Student loadStudent(int id) throws Exception {

		Student student = null;

		Connection mycon = null;
		PreparedStatement myst = null;
		ResultSet myrs = null;

		try {
			mycon = dataSource.getConnection();

			String sql = "select * from student where id=?";
			myst = mycon.prepareStatement(sql);

			myst.setInt(1, id);
			myrs = myst.executeQuery();

			if (myrs.next()) {
				String firstname = myrs.getString("first_name");
				String lastname = myrs.getString("last_name");
				String email = myrs.getString("email");
				student = new Student(id, firstname, lastname, email);
			} else {
				throw new Exception("Could not find student id: " + id);
			}
			
			
			return student;
		} finally {
			close(mycon, myst, myrs);
		}

	}

	public void updateStudent(Student tempStudent) throws Exception {
		Connection mycon = null;
		PreparedStatement myst = null;

		try {
			mycon = dataSource.getConnection();

			String sql = "update student " 
						+ "set first_name=?, last_name=?, email=? " 
					+ "where id=?";

			myst = mycon.prepareStatement(sql);

			myst.setString(1, tempStudent.getFirstName());
			myst.setString(2, tempStudent.getLastName());
			myst.setString(3, tempStudent.getEmail());
			myst.setInt(4, tempStudent.getId());

			myst.execute();

		} finally {
			close(mycon, myst, null);
		}
		
	}

	public void deleteStudent(int id) throws Exception {
		Connection mycon = null;
		PreparedStatement myst = null;

		try {
			mycon = dataSource.getConnection();

			String sql = "delete from student where id=?";

			myst = mycon.prepareStatement(sql);

			myst.setInt(1, id);

			myst.execute();

		} finally {
			close(mycon, myst, null);
		}
		
	}
}
