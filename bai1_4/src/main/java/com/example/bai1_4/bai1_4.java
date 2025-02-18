package com.example.bai1_4;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "BMI", value = "/BMI")
public class bai1_4 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Tính chỉ số BMI</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 50px; }");
        out.println(".container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px #ccc; display: inline-block; }");
        out.println("h2 { color: #333; }");
        out.println("input { padding: 8px; margin: 5px; width: 200px; }");
        out.println("button { background-color: #28a745; color: white; padding: 10px; border: none; cursor: pointer; }");
        out.println("button:hover { background-color: #218838; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h2>Tính chỉ số BMI</h2>");
        out.println("<form action='BMI' method='POST'>");
        out.println("<label>Chiều cao (m): </label>");
        out.println("<input type='text' name='height' required><br>");
        out.println("<label>Cân nặng (kg): </label>");
        out.println("<input type='text' name='weight' required><br>");
        out.println("<button type='submit'>Tính BMI</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // Lấy dữ liệu từ form
            String heightStr = request.getParameter("height");
            String weightStr = request.getParameter("weight");

            // Kiểm tra dữ liệu nhập vào
            if (heightStr == null || weightStr == null || heightStr.isEmpty() || weightStr.isEmpty()) {
                out.println("<h3 style='color:red;'>Vui lòng nhập đầy đủ chiều cao và cân nặng!</h3>");
                return;
            }

            // Chuyển đổi dữ liệu sang kiểu số
            double height = Double.parseDouble(heightStr);
            double weight = Double.parseDouble(weightStr);

            // Tính BMI
            double bmi = weight / (height * height);

            // Xác định phân loại BMI
            String category;
            if (bmi < 18.5) {
                category = "Gầy";
            } else if (bmi < 25) {
                category = "Bình thường";
            } else if (bmi < 30) {
                category = "Thừa cân";
            } else {
                category = "Béo phì";
            }

            // Xuất kết quả
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Kết quả BMI</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 50px; }");
            out.println(".container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px #ccc; display: inline-block; }");
            out.println("h2 { color: #333; }");
            out.println(".result { font-size: 20px; color: blue; }");
            out.println(".category { font-size: 18px; font-weight: bold; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h2>Kết quả BMI</h2>");
            out.println("<p class='result'>Chỉ số BMI của bạn: <strong>" + String.format("%.2f", bmi) + "</strong></p>");
            out.println("<p class='category'>Phân loại: <strong>" + category + "</strong></p>");
            out.println("<br><a href='BMI'><button>Nhập lại</button></a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (NumberFormatException e) {
            out.println("<h3 style='color:red;'>Lỗi: Vui lòng nhập đúng định dạng số!</h3>");
        }
    }
}