package com.chainsys.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RestController
public class EmployeeDao {
	private static Connection oracleConnection;
	// init-method

	public static void getConnection() {
		System.out.println("getconnection");
		String drivername = "oracle.jdbc.OracleDriver";
		String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "system";
		String password = "123";
		try {
			Class.forName(drivername);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			oracleConnection = DriverManager.getConnection(dbUrl, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static  List<Employee> getAllEmployee() {
		List<Employee> listOfEmployees = new ArrayList<Employee>();
		Employee emp = null;
		String selectquery = "select EMPLOYEE_ID,FIRST_NAME,LAST_NAME,EMAIL,HIRE_DATE,JOB_ID,SALARY  from Employees ";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
			getConnection();
			con = oracleConnection;
			ps = con.prepareStatement(selectquery);
			rs = ps.executeQuery();

			while (rs.next()) {
				emp = new Employee();
				emp.setEmp_id(rs.getInt(1));
				emp.setFirst_name(rs.getString(2));
				emp.setLast_name(rs.getString(3));
				emp.setEmail(rs.getString(4));
				java.util.Date date = new java.util.Date(rs.getDate(5).getTime());
				emp.setHire_date(date);
				emp.setJob_id(rs.getString(6));
				emp.setSalary(rs.getFloat(7));
				listOfEmployees.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null)
					{
					rs.close();
					}
			} catch (SQLException e1) {
				e1.printStackTrace(); //TODO: ExceptionManager
			}
			try {
				if(ps!=null)
				{
				ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(); //TODO: ExceptionManager
			}
		}
		return listOfEmployees;
	}
	// destroyed-method
}
