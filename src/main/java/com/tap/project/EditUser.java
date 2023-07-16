package com.tap.project;

import java.io.FileInputStream;
import java.io.IOException;
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

@WebServlet("/EditUser")
public class EditUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        // Retrieve other updated user details from request parameters

        String path = "C:\\Users\\moham\\eclipse-workspace\\Form\\src\\main\\java\\com\\project\\utilities\\sql.properties";
        String sql = "UPDATE userlist.list SET name = ?, email = ? WHERE name = ?";

        FileInputStream fis = null;
        Properties p = null;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            fis = new FileInputStream(path);
            p = new Properties();
            p.load(fis);

            con = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
                    p.getProperty("password"));

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, request.getParameter("email"));
            pstmt.setString(3, name);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                response.sendRedirect("edit_success.jsp");
            } else {
                response.sendRedirect("edit_failure.jsp");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
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
