//attendance.jsp

<!DOCTYPE html>
<html>
<head>
    <title>Student Attendance</title>
</head>
<body>
    <h2>Student Attendance Form</h2>
    <form action="AttendanceServlet" method="post">
        Student ID: <input type="number" name="studentID" required><br><br>
        Date: <input type="date" name="date" required><br><br>
        Status:
        <select name="status" required>
            <option value="Present">Present</option>
            <option value="Absent">Absent</option>
            <option value="Leave">Leave</option>
        </select><br><br>
        <input type="submit" value="Submit Attendance">
    </form>
</body>
</html>


  //AttendanceServlet.java

  import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AttendanceServlet extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/college_db";
    private static final String USER = "root";
    private static final String PASS = "your_password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int studentID = Integer.parseInt(request.getParameter("studentID"));
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            String query = "INSERT INTO Attendance (StudentID, Date, Status) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentID);
            ps.setString(2, date);
            ps.setString(3, status);
            ps.executeUpdate();

            out.println("<h2>✅ Attendance Recorded Successfully!</h2>");
            out.println("<a href='attendance.jsp'>Record Another</a>");
            con.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}

//web.xml

<servlet>
    <servlet-name>AttendanceServlet</servlet-name>
    <servlet-class>AttendanceServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>AttendanceServlet</servlet-name>
    <url-pattern>/AttendanceServlet</url-pattern>
</servlet-mapping>


/
