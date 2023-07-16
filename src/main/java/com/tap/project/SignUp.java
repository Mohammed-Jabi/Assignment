package com.tap.project;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


	@WebServlet("/SignUp")
	public class SignUp extends HttpServlet {
	    private static final long serialVersionUID = 1L;

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String Name = request.getParameter("name");
	        int mob = Integer.parseInt(request.getParameter("mob"));
	        String email = request.getParameter("email");
	        String dob = request.getParameter("dob");
	        String address = request.getParameter("address");
	        
	        String path = "C:\\Users\\moham\\eclipse-workspace\\Form\\src\\main\\java\\com\\project\\utilities\\sql.properties";

	        FileInputStream fis = null;
	        Properties p = null;
	        Connection con = null;

	        String sql = "INSERT INTO userlist.list (name, pno, email, address, password, dob) VALUES (?, ?, ?, ?, ?, ?)";

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            fis = new FileInputStream(path);
	            p = new Properties();
	            p.load(fis);

	            con = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
	                    p.getProperty("password"));

	            PreparedStatement pstmt = con.prepareStatement(sql);

	            pstmt.setString(1, request.getParameter("name"));
	            pstmt.setInt(2, mob);
	            pstmt.setString(3, request.getParameter("email"));
	            pstmt.setString(4, request.getParameter("address"));
	            pstmt.setString(5, request.getParameter("password"));
	            pstmt.setString(6, request.getParameter("dob"));

	            pstmt.executeUpdate();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (con != null) {
	                    con.close();
	                }
	                if (fis != null) {
	                    fis.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        PrintWriter out = response.getWriter();
	        out.println("Thanks for Registration " + request.getParameter("name"));
	    }

	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doPost(request, response);
	    }
	}

	
