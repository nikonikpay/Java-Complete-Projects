package com.example.servlet03;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil {

    private DataSource dataSource;

    public StudentDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<Student> getStudent() throws Exception {

        List<Student> students = new ArrayList<>();

        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        //1-get a connection
        //2- create sql statement
        //3-execute query
        //4- process result set
        //5- close JDBC object

        try {


            //1
            myConnection = dataSource.getConnection();

            //2

            String sql = "select * from student";
            myStatement = myConnection.createStatement();

            //3
            myResultSet = myStatement.executeQuery(sql);


            //4
            while (myResultSet.next()) {

                //1- retrieve data from result set
                //2- create new student object
                //3-add it to the list of student


                //1
                int id = myResultSet.getInt("id");
                String firstName = myResultSet.getString("first_name");
                String lastName = myResultSet.getString("last_name");
                String email = myResultSet.getString("email");


                //2-
                Student tempStudent = new Student(id, firstName, lastName, email);


                //3
                students.add(tempStudent);


            }


            return students;
        } finally {

            close(myConnection, myStatement, myResultSet);
        }


    }

    private void close(Connection myConnection, Statement myStatement, ResultSet myResultSet) {

        try {
            if (myResultSet != null) {
                myResultSet.close();
            }
            if (myStatement != null) {
                myStatement.close();
            }

            if (myConnection != null) {
                myConnection.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void addStudent(Student theStudent) throws Exception {
        //1- get db connection and create sql insert
        //2-set the param values for the student
        //3- execute sql insert
        //4-clean up JDBC objects

        Connection myConnection = null;
        PreparedStatement myStatement = null;

        try {

            //1
            myConnection = dataSource.getConnection();
            String sql = "insert into student " + "(first_name, last_name, email) " + "values (?, ?, ?)";

            //2-
            myStatement = myConnection.prepareStatement(sql);

            myStatement.setString(1, theStudent.getFirstName());
            myStatement.setString(2, theStudent.getLastName());
            myStatement.setString(3, theStudent.getEmail());

            //3
            myStatement.execute();


        } finally {
            //4
            close(myConnection, myStatement, null);

        }


    }

    public Student getStudent(String theStudentId) throws Exception {

        Student theStudent = null;
        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet myResultSet = null;
        int studentId;


        try {
            //1-convert student id to int
            // 2-get connection to database
            //3- create sql to get selected student
            //4-create prepaid statement
            //5-set params
            //6- execute statement
            //7- retrieve data from result set
            //8- clean up and close connections


            //1
            studentId = Integer.parseInt(theStudentId);

            //2
            myConnection = dataSource.getConnection();


            //3
            String sql = "select * from student where id=?";


            //4
            myPreparedStatement = myConnection.prepareStatement(sql);


            //5
            myPreparedStatement.setInt(1, studentId);

            //6
            myResultSet = myPreparedStatement.executeQuery();

            //7
            if (myResultSet.next()) {
                String firstName = myResultSet.getString("first_name");
                String lastName = myResultSet.getString("last_name");
                String email = myResultSet.getString("email");

                //use the Student id during construction
                theStudent = new Student(studentId, firstName, lastName, email);

            } else {
                throw new Exception("Could not find student id: " + studentId);
            }


            return theStudent;
        } finally {

            //8
            close(myConnection, myPreparedStatement, myResultSet);
        }
    }

    public void updateStudent(Student student) throws Exception {

        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;

        //1-get db connection
        //2- create Sql update statement
        //3- prepare statement
        //4- set params
        //5-execute SQL statement

        try {
            //1
            myConnection = dataSource.getConnection();

            //2
            String sql = "update student " + "set first_name=?, last_name=?, email=? " + "where id=?";


            //3
            myPreparedStatement = myConnection.prepareStatement(sql);

            //4
            myPreparedStatement.setString(1, student.getFirstName());
            myPreparedStatement.setString(2, student.getLastName());
            myPreparedStatement.setString(3, student.getEmail());
            myPreparedStatement.setInt(4, student.getId());


            //5
            myPreparedStatement.execute();
        } finally {
            close(myConnection, myPreparedStatement, null);
        }

    }

    public void deleteStudent(String studentId) throws Exception {

        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;

        try {
            //1-convert student id to int
            //2-get connection to dtabase
            //3-create sql to delete student
            //4-prepare statement
            //5-set params
            //6-execute sql statement
            //7-clean up jdbc


            //1
            int theStudentId = Integer.parseInt(studentId);

            //2-
            myConnection = dataSource.getConnection();

            //3-
            String sql = "delete from student where id=?";

            //4
            myPreparedStatement = myConnection.prepareStatement(sql);

            //5
            myPreparedStatement.setInt(1, theStudentId);


            //6
            myPreparedStatement.execute();


        } finally {
            //7-

            close(myConnection, myPreparedStatement, null);
        }


    }
}
