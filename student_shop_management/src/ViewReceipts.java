import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;




public class ViewReceipts extends JFrame {
    private JTable receiptTable;
    private DefaultTableModel receiptTableModel;
    private JButton generateReceiptButton;




    public ViewReceipts() {
        setTitle("View Receipts");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);




        setLayout(new BorderLayout());
        Color backgroundColor = new Color(30, 30, 30);
        Color textColor = Color.WHITE;
        getContentPane().setBackground(backgroundColor);




        String[] receiptColumnNames = {"Receipt ID", "Student Name", "Total Amount", "Date"};
        receiptTableModel = new DefaultTableModel(receiptColumnNames, 0);
        receiptTable = new JTable(receiptTableModel);
        receiptTable.setBackground(new Color(50, 50, 50));
        receiptTable.setForeground(textColor);
        receiptTable.setGridColor(Color.GRAY);
        receiptTable.setFont(new Font("Arial", Font.PLAIN, 18));
        receiptTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        receiptTable.setRowHeight(40);
        JScrollPane receiptScrollPane = new JScrollPane(receiptTable);
        add(receiptScrollPane, BorderLayout.CENTER);






        generateReceiptButton = new JButton("Generate Receipt");
        generateReceiptButton.setBackground(new Color(70, 70, 70));
        generateReceiptButton.setForeground(textColor);
        generateReceiptButton.setFont(new Font("Arial", Font.PLAIN, 18));
        generateReceiptButton.addActionListener(e -> generateReceipt());
        add(generateReceiptButton, BorderLayout.SOUTH);  // Add button to the bottom




        loadReceiptsFromDB();




        setVisible(true);
    }




    private void loadReceiptsFromDB() {
        String query = "SELECT * FROM receipts";
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {




            while (resultSet.next()) {
                int receiptId = resultSet.getInt("id");
                String studentName = resultSet.getString("student_name");
                double totalAmount = resultSet.getDouble("total_amount");
                String date = resultSet.getString("date");






                receiptTableModel.addRow(new Object[]{receiptId, studentName, totalAmount, date});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error occurred while loading receipts.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }




    private void generateReceipt() {
        int selectedRow = receiptTable.getSelectedRow();
        if (selectedRow != -1) {
            int receiptId = (int) receiptTable.getValueAt(selectedRow, 0);
            String studentName = (String) receiptTable.getValueAt(selectedRow, 1);
            double totalAmount = (double) receiptTable.getValueAt(selectedRow, 2);
            String date = (String) receiptTable.getValueAt(selectedRow, 3);






            String receipt = "Receipt ID: " + receiptId + "\n"
                    + "Student Name: " + studentName + "\n"
                    + "Total Amount: " + totalAmount + "\n"
                    + "Date: " + date;






            JOptionPane.showMessageDialog(this, receipt, "Receipt", JOptionPane.INFORMATION_MESSAGE);






        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a receipt to generate.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
