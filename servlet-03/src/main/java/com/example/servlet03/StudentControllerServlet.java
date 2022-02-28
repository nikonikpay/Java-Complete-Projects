package com.example.servlet03;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentControllerServlet", value = "/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {


    private StudentDbUtil studentDbUtil;


    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;


    @Override
    public void init() throws ServletException {
        super.init();

        //create student database util nad pass it to the connection pool

        try {

            studentDbUtil = new StudentDbUtil(dataSource);

        } catch (Exception e) {
            throw new ServletException(e);
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // list the student in MVC fashion
        try {
            //read  the command parameter
            String theCommand = request.getParameter("command");

            //if the command is missing, then default to listing student
            if (theCommand == null) {
                theCommand = "LIST";

            }

            //route to appropriate method
            switch (theCommand) {
                case "LIST":
                    listStudent(request, response);
                    break;

                case "ADD":
                    addStudent(request, response);
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
                    listStudent(request, response);
            }


        } catch (Exception e) {
            throw new ServletException(e);
        }


    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1-read student info from form data
        //2- delete student from db
        //3-send them back to the list students page

        //1
        String studentId = request.getParameter("studentId");

        //2
        studentDbUtil.deleteStudent(studentId);

        listStudent(request, response);


    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1-read student info from form data
        //2- create a new student object
        //3-perform update on database
        //4-send them back to the list students page

        //1
        int id = Integer.parseInt(request.getParameter("studentId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");


        //2-
        Student student = new Student(id, firstName, lastName, email);


        //3
        studentDbUtil.updateStudent(student);

        //4
        listStudent(request, response);


    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {


        //1- read student id from form data
        //2-get student from database(db util)
        //3-place student in the request attribute
        //4-send to jsp page: update-student-form.jsp


        //1
        String theStudentId = request.getParameter("studentId");

        //2-
        Student theStudent = studentDbUtil.getStudent(theStudentId);

        //3-
        request.setAttribute("THE_STUDENT", theStudent);

        //4-
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
        dispatcher.forward(request, response);


    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1-read student info from form data
        //2- create a new student object
        //3-add the student to the database
        //4-send back to main  page(the student list)

        //1
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");


        //2
        Student theStudent = new Student(firstName, lastName, email);

        //3
        studentDbUtil.addStudent(theStudent);

        //4
        listStudent(request, response);


    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1- get Student from db util
        //2-add student to the request
        //3-send to JSp page(view)


        //1
        List<Student> students = studentDbUtil.getStudent();

        //2
        request.setAttribute("STUDENT_LIST", students);


        //3
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(request, response);


    }


}
