//employee.html

<!DOCTYPE html>
<html>
<head>
    <title>Employee Records</title>
</head>
<body>
    <h2>View All Employees</h2>
    <form action="EmployeeServlet" method="get">
        <input type="submit" value="View All Employees">
    </form>

    <h2>Search Employee by ID</h2>
    <form action="EmployeeServlet" method="get">
        Enter EmpID: <input type="number" name="empid" required>
        <input type="submit" value="Search">
    </form>
</body>
</html>



  //EmployeeServlet.java

  import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/college_db";
    private static final String USER = "root";
    private static final String PASS = "your_password";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String empIdParam = request.getParameter("empid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            if (empIdParam != null && !empIdParam.isEmpty()) {
                int empId = Integer.parseInt(empIdParam);
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Employee WHERE EmpID=?");
                ps.setInt(1, empId);
                ResultSet rs = ps.executeQuery();

                out.println("<h2>Employee Details</h2>");
                if (rs.next()) {
                    out.printf("EmpID: %d<br>Name: %s<br>Salary: %.2f",
                            rs.getInt("EmpID"), rs.getString("Name"), rs.getDouble("Salary"));
                } else {
                    out.println("No employee found with ID: " + empId);
                }
            } else {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");

                out.println("<h2>All Employees</h2>");
                out.println("<table border='1'><tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");
                while (rs.next()) {
                    out.printf("<tr><td>%d</td><td>%s</td><td>%.2f</td></tr>",
                            rs.getInt("EmpID"), rs.getString("Name"), rs.getDouble("Salary"));
                }
                out.println("</table>");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

  //web.xml

  <servlet>
    <servlet-name>EmployeeServlet</servlet-name>
    <servlet-class>EmployeeServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>EmployeeServlet</servlet-name>
    <url-pattern>/EmployeeServlet</url-pattern>
</servlet-mapping>

}
