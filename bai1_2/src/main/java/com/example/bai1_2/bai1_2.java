package com.example.bai1_2;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "testdoPost", value = "/testdoPost")
public class bai1_2 extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter traVe = response.getWriter ();
        traVe.append ("Ban vua moi gui yeu cau dang Get, day la dap ung cua toi");
        String noidungHTML = "<form method=POST action=\"/bai1_2/testdoPost\">"
                +"\t\t\t<label>Ho:</label>"+"<input type=\"text\" name=\"fname\"><br>\r\n"
                +"\t\t\t<label>Ten:</label>"+"<input type=\"text\" name=\"lname\"><br>\r\n"
                +"\t\t\t<input type=\"submit\" value=\"Gui Di\">\r\n"
                +"</form>";
        traVe.append(noidungHTML);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String value1 = request.getParameter("fname");
        String value2 = request.getParameter("lname");
        PrintWriter traVe = response.getWriter();
        traVe.append ("Giá tri tham số fname =");
        traVe.append(value1);
        traVe.append ("\nGiá tri tham số lname =");
        traVe.append(value2);
    }

}