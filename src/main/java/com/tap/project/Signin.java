package com.tap.project;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Signin")
public class Signin extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String UName = req.getParameter("name");
        String password = req.getParameter("password");

        // JDBC
        String path = "C:\\Users\\moham\\eclipse-workspace\\Form\\src\\main\\java\\com\\project\\utilities\\sql.properties";

        String sql = "SELECT * FROM userlist.list WHERE name = ? AND password = ?";

        FileInputStream fis = null;
        Properties p = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            // Load the Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Get the credentials
            fis = new FileInputStream(path);
            p = new Properties();
            p.load(fis);

            // Establish a connection
            con = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
                    p.getProperty("password"));

            // Create a SQL statement
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, UName);
            pstmt.setString(2, password);

            // Execute the query
            resultSet = pstmt.executeQuery();

            PrintWriter out = resp.getWriter();
            if (resultSet.next()) {
                out.println("<h1>Welcome, " + UName + "!</h1>");
                out.println("<h2>Your Details:</h2>");
                out.println("<p>Name: " + resultSet.getString("name") + "</p>");
                out.println("<p>Mobile Number: " + resultSet.getInt("pno") + "</p>");
                out.println("<p>Email: " + resultSet.getString("email") + "</p>");
                out.println("<p>Date of Birth: " + resultSet.getString("dob") + "</p>");
                out.println("<p>Address: " + resultSet.getString("address") + "</p>");

                // Add edit and delete buttons
                out.println("<form action='EditUser' method='post'>");
                out.println("<input type='hidden' name='name' value='" + resultSet.getString("name") + "'>");
                out.println("<input type='submit' value='Edit'>");
                out.println("</form>");

                out.println("<form action='DeleteUser' method='post'>");
                out.println("<input type='hidden' name='name' value='" + resultSet.getString("name") + "'>");
                out.println("<input type='submit' value='Delete'>");
                out.println("</form>");
            } else {
                out.println("<h1>Wrong Username or Password.</h1>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
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
    }
}
