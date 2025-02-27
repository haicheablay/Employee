import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EmployeeManagementUI extends JFrame {
    private JTextField txtName, txtAge, txtDepartment, txtSalary;
    private JTable table;
    private DefaultTableModel model;
    private EmployeeDAO dao = new EmployeeDAO();
    private int selectedEmployeeId = -1; // Stores selected employee ID

    public EmployeeManagementUI() {
        setTitle("Employee Management System");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        panel.add(txtAge);

        panel.add(new JLabel("Department:"));
        txtDepartment = new JTextField();
        panel.add(txtDepartment);

        panel.add(new JLabel("Salary:"));
        txtSalary = new JTextField();
        panel.add(txtSalary);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        // Style buttons
        btnAdd.setBackground(new Color(46, 204, 113)); // Green
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);

        btnUpdate.setBackground(new Color(52, 152, 219)); // Blue
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);

        btnDelete.setBackground(new Color(231, 76, 60)); // Red
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        panel.add(buttonPanel);

        add(panel, BorderLayout.NORTH);

        // Table Setup
        model = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Department", "Salary"}, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Event Listeners
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                int age = Integer.parseInt(txtAge.getText());
                String department = txtDepartment.getText();
                double salary = Double.parseDouble(txtSalary.getText());

                Employee emp = new Employee(0, name, age, department, salary);
                dao.addEmployee(emp);
                loadEmployees();
                clearFields();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedEmployeeId == -1) {
                    JOptionPane.showMessageDialog(null, "Please select an employee to update!");
                    return;
                }
                String name = txtName.getText();
                int age = Integer.parseInt(txtAge.getText());
                String department = txtDepartment.getText();
                double salary = Double.parseDouble(txtSalary.getText());

                Employee emp = new Employee(selectedEmployeeId, name, age, department, salary);
                dao.updateEmployee(emp);
                loadEmployees();
                clearFields();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    dao.deleteEmployee(id);
                    loadEmployees();
                    clearFields();
                }
            }
        });

        // Table Row Click Event - Fills Input Fields
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                selectedEmployeeId = (int) model.getValueAt(selectedRow, 0);
                txtName.setText(model.getValueAt(selectedRow, 1).toString());
                txtAge.setText(model.getValueAt(selectedRow, 2).toString());
                txtDepartment.setText(model.getValueAt(selectedRow, 3).toString());
                txtSalary.setText(model.getValueAt(selectedRow, 4).toString());
            }
        });

        loadEmployees();
    }

    private void loadEmployees() {
        model.setRowCount(0);
        List<Employee> employees = dao.getAllEmployees();
        for (Employee emp : employees) {
            model.addRow(new Object[]{emp.getId(), emp.getName(), emp.getAge(), emp.getDepartment(), emp.getSalary()});
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtAge.setText("");
        txtDepartment.setText("");
        txtSalary.setText("");
        selectedEmployeeId = -1; // Reset selected ID
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeManagementUI().setVisible(true));
    }
}
