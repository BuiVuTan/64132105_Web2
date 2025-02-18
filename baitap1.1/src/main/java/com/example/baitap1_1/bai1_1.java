package com.example.baitap1_1;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "subpage", value = "/subpage")
public class bai1_1 extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String value1 = request.getParameter ("param1");
        String value2 = request.getParameter ("param2");
        PrintWriter traVe = response.getWriter ();
        traVe.append ("Giá tri tham số param1 =");
        traVe.append(value1) ;
        traVe.append ("\nGiá tri tham số param2 =");
        traVe.append(value2);
    }
}