package com.example.servlet03;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {


    @Resource(name="jdbc/web_student_tracker")
    private DataSource dataSource;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //1- set up print writer
        //2- get a connection database
        //3- create a sql statement
        //4-execute sql
        //5- process the result set


        //1
        PrintWriter out = response.getWriter();

        response.setContentType("text/plain");


        //2-
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;


        try {

            myConnection = dataSource.getConnection();

            //3
            String sql = "select * from student";
            myStatement = myConnection.createStatement();



            //4
            myResultSet = myStatement.executeQuery(sql);

            //5
            while(myResultSet.next()) {

                String email = myResultSet.getString("email");
                out.println(email);


            }


        }catch( Exception e) {
            e.printStackTrace();
        }



    }

}



