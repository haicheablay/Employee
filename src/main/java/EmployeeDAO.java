import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // ✅ Method បន្ថែមបុគ្គលិកថ្មី
    public void addEmployee(Employee emp) {
        String query = "INSERT INTO employees (name, age, department, salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = CreateConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, emp.getName());
            stmt.setInt(2, emp.getAge());
            stmt.setString(3, emp.getDepartment());
            stmt.setDouble(4, emp.getSalary());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
    }

    // ✅ Method ស្វែងរកបុគ្គលិកទាំងអស់
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Connection conn = CreateConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
        return employees;
    }

    // ✅ Method លុបបុគ្គលិក
    public void deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = CreateConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }

    // ✅ Method ធ្វើបច្ចុប្បន្នភាពបុគ្គលិក
    public void updateEmployee(Employee emp) {
        String query = "UPDATE employees SET name = ?, age = ?, department = ?, salary = ? WHERE id = ?";
        try (Connection conn = CreateConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, emp.getName());
            stmt.setInt(2, emp.getAge());
            stmt.setString(3, emp.getDepartment());
            stmt.setDouble(4, emp.getSalary());
            stmt.setInt(5, emp.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
    }
}
