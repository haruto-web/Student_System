
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


public class UserCart extends JFrame {
    private static final ArrayList<Product> productList = new ArrayList<>();
    private JTable productTable;
    private JTextField txtItemName;


    public static void main(String[] args) {


        UserCart frame = new UserCart();




        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);




        frame.setVisible(true);




        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public UserCart() {
        setTitle("CampusCart");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1200, 800);


        Color backgroundColor = new Color(30, 30, 30);
        Color fieldColor = new Color(60, 60, 60);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(100, 100, 100);




        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(backgroundColor);




        JLabel lblStudentName = new JLabel("Student Name:");
        lblStudentName.setBounds(200, 65, 150, 50);
        lblStudentName.setForeground(textColor);
        lblStudentName.setFont(new Font("Arial", Font.PLAIN, 20));


        JTextField txtStudentName = new JTextField();
        txtStudentName.setBounds(500, 65, 300, 50);
        txtStudentName.setBackground(fieldColor);
        txtStudentName.setForeground(textColor);
        txtStudentName.setCaretColor(textColor);
        txtStudentName.setBorder(BorderFactory.createLineBorder(textColor));


        JLabel lblStudentNumber = new JLabel("Student Number:");
        lblStudentNumber.setBounds(200, 180, 150, 50);
        lblStudentNumber.setForeground(textColor);
        lblStudentNumber.setFont(new Font("Arial", Font.PLAIN, 20));


        JTextField txtStudentNumber = new JTextField();
        txtStudentNumber.setBounds(500, 180, 300, 50);
        txtStudentNumber.setBackground(fieldColor);
        txtStudentNumber.setForeground(textColor);
        txtStudentNumber.setCaretColor(textColor);
        txtStudentNumber.setBorder(BorderFactory.createLineBorder(textColor));


        JLabel lblYearLevel = new JLabel("Year Level:");
        lblYearLevel.setBounds(200, 290, 150, 50);
        lblYearLevel.setForeground(textColor);
        lblYearLevel.setFont(new Font("Arial", Font.PLAIN, 20));




        String[] yearLevels = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
        JComboBox<String> cbYearLevel = new JComboBox<>(yearLevels);
        cbYearLevel.setBounds(500, 290, 300, 50);
        cbYearLevel.setBackground(fieldColor);
        cbYearLevel.setForeground(textColor);
        cbYearLevel.setBorder(BorderFactory.createLineBorder(textColor));


        JLabel lblProgram = new JLabel("Program/Course:");
        lblProgram.setBounds(200, 400, 180, 50);
        lblProgram.setForeground(textColor);
        lblProgram.setFont(new Font("Arial", Font.PLAIN, 20));


        JTextField txtProgram = new JTextField();
        txtProgram.setBounds(500, 400, 300, 50);
        txtProgram.setBackground(fieldColor);
        txtProgram.setForeground(textColor);
        txtProgram.setCaretColor(textColor);
        txtProgram.setBorder(BorderFactory.createLineBorder(textColor));




        JLabel lblProducts = new JLabel("MENU");
        lblProducts.setBounds(1150, 20, 200, 25);
        lblProducts.setForeground(textColor);
        lblProducts.setFont(new Font("Arial", Font.BOLD, 16));




        String[] columnNames = {"Item Name", "Quantity", "Price", "Date Added"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        productTable.setBackground(fieldColor);
        productTable.setForeground(textColor);
        productTable.setGridColor(Color.GRAY);
        productTable.setRowHeight(30);
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));
        productTable.getTableHeader().setBackground(buttonColor);
        productTable.getTableHeader().setForeground(textColor);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));




        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(1150, 60, 700, 800);
        panel.add(scrollPane);




        JLabel lblItemName = new JLabel("Item Name:");
        lblItemName.setBounds(200, 670, 150, 50);
        lblItemName.setForeground(textColor);
        lblItemName.setFont(new Font("Arial", Font.PLAIN, 20));




        txtItemName = new JTextField();
        txtItemName.setBounds(500, 670, 300, 50);
        txtItemName.setBackground(fieldColor);
        txtItemName.setForeground(textColor);
        txtItemName.setCaretColor(textColor);
        txtItemName.setBorder(BorderFactory.createLineBorder(textColor));


        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(200, 800, 150, 50);
        lblQuantity.setForeground(textColor);
        lblQuantity.setFont(new Font("Arial", Font.PLAIN, 20));


        JTextField txtQuantity = new JTextField();
        txtQuantity.setBounds(500, 800, 300, 50);
        txtQuantity.setBackground(fieldColor);
        txtQuantity.setForeground(textColor);
        txtQuantity.setCaretColor(textColor);
        txtQuantity.setBorder(BorderFactory.createLineBorder(textColor));




        JButton btnAddItem = new JButton("Add To Cart");
        btnAddItem.setBounds(200, 900, 200, 25);
        btnAddItem.setBackground(buttonColor);
        btnAddItem.setForeground(textColor);


        JButton btnViewItems = new JButton("View Cart");
        btnViewItems.setBounds(450, 900, 200, 25);
        btnViewItems.setBackground(buttonColor);
        btnViewItems.setForeground(textColor);


        JButton btnSubmit = new JButton("Check Out");
        btnSubmit.setBounds(690, 900, 200, 25);
        btnSubmit.setBackground(buttonColor);
        btnSubmit.setForeground(textColor);


        JButton btnAdminLogin = new JButton("Admin Login");
        btnAdminLogin.setBounds(1150, 900, 200, 25);
        btnAdminLogin.setBackground(buttonColor);
        btnAdminLogin.setForeground(textColor);




        btnAddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = txtItemName.getText();
                String quantityText = txtQuantity.getText();

                // Check if the item name or quantity is empty
                if (itemName.isEmpty() || quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Item name and quantity must be entered.");
                    return;  // Exit if fields are empty
                }

                // Fetch the price and stock quantity from the database based on the item name
                double price = 0.0;
                int availableQuantity = 0;  // Variable to store available stock

                try (Connection connection = db.getConnection()) {
                    // SQL query to fetch both price and available quantity of the product
                    String sql = "SELECT price, quantity FROM products WHERE name = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, itemName);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                price = resultSet.getDouble("price");
                                availableQuantity = resultSet.getInt("quantity");
                            } else {
                                JOptionPane.showMessageDialog(null, "Item not found in the store.");
                                return;  // Exit if item is not found
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error fetching data from store: " + ex.getMessage());
                    return;  // Exit if there is a database error
                }

                // Parse the entered quantity
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(quantityText);  // Parse quantity value
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid quantity format.");
                    return;  // Exit if quantity is not a valid number
                }

                // Check if the entered quantity exceeds available stock
                if (quantity > availableQuantity) {
                    JOptionPane.showMessageDialog(null, "The quantity of this item available is " + availableQuantity + ". You cannot buy more than this.");
                    return;  // Exit if quantity exceeds available stock
                }

                String addDate = java.time.LocalDate.now().toString(); // Use current date

                try (Connection connection = db.getConnection()) {
                    String sql = "INSERT INTO activity_log (item_name, quantity, price, add_date) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, itemName);
                        preparedStatement.setInt(2, quantity);
                        preparedStatement.setDouble(3, price);
                        preparedStatement.setString(4, addDate);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Product added!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error adding product to store: " + ex.getMessage());
                }

                // Create Product and add to list
                Product product = new Product(itemName, quantity, price, addDate);
                productList.add(product);

                // Reset fields
                txtItemName.setText("");
                txtQuantity.setText("");
            }
        });




        btnViewItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                StringBuilder productDetails = new StringBuilder("Purchased Items:\n");




                for (Product product : productList) {
                    productDetails.append("Item Name: ").append(product.getItemName())
                            .append("\nQuantity: ").append(product.getQuantity())
                            .append("\nPrice: ").append(product.getPrice())
                            .append("\nDate Added: ").append(product.getAddDate())
                            .append("\n\n");
                }




                if (productList.isEmpty()) {
                    productDetails.append("No products added yet.");
                }


                JOptionPane.showMessageDialog(null, productDetails.toString(), "Purchased Items", JOptionPane.INFORMATION_MESSAGE);
            }
        });




        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double totalPrice = 0;
                StringBuilder receipt = new StringBuilder();

                // Build the receipt content
                receipt.append("**************************************************\n")
                        .append("                CAMPUS CART\n")
                        .append("                 363 P. Casal Street,\n")
                        .append("                  Quiapo, Manila\n")
                        .append("**************************************************\n")
                        .append("\n");

                receipt.append("Date: ").append(java.time.LocalDate.now()).append("\n\n");

                receipt.append("Item Name                Qty      Price     Total\n")
                        .append("------------------------------------------------------\n");

                for (Product product : productList) {
                    double itemTotal = product.getQuantity() * product.getPrice();
                    receipt.append(String.format("%-20s %-8d %-10.2f %-10.2f\n", product.getItemName(), product.getQuantity(), product.getPrice(), itemTotal));
                    totalPrice += itemTotal;
                }

                receipt.append("------------------------------------------------------\n");

                receipt.append(String.format("Total Price:                          %.2f\n", totalPrice));

                receipt.append("\n**************************************************\n")
                        .append("Thank you for shopping with Campus Cart!\n")
                        .append("Visit us again for more great deals!\n")
                        .append("**************************************************\n");

                JOptionPane.showMessageDialog(null, receipt.toString(), "E-Receipt", JOptionPane.INFORMATION_MESSAGE);

                txtStudentName.setText("");
                txtStudentNumber.setText("");
                txtProgram.setText("");

                txtItemName.setText("");
                txtQuantity.setText("");
            }
        });







        btnAdminLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true); // Make the login screen visible
                dispose();
            }
        });




        panel.add(lblStudentName);
        panel.add(txtStudentName);
        panel.add(lblStudentNumber);
        panel.add(txtStudentNumber);
        panel.add(lblYearLevel);
        panel.add(cbYearLevel);
        panel.add(lblProgram);
        panel.add(txtProgram);
        panel.add(lblProducts);
        panel.add(lblItemName);
        panel.add(txtItemName);
        panel.add(lblQuantity);
        panel.add(txtQuantity);
        panel.add(btnAddItem);
        panel.add(btnViewItems);
        panel.add(btnSubmit);
        panel.add(btnAdminLogin);

        add(panel);
        updateProductTable();
    }
    private void updateProductTable() {
        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
        tableModel.setRowCount(0); // Clear the table
        for (Product product : productList) {
            Object[] row = {product.getItemName(), product.getQuantity(), product.getPrice(), product.getAddDate()};
            tableModel.addRow(row);
        }
    }

    public void refreshProductTable(ArrayList<Product> productList) {
        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
        tableModel.setRowCount(0);
        for (Product product : productList) {
            Object[] row = {product.getItemName(), product.getQuantity(), product.getPrice(), product.getAddDate()};
            tableModel.addRow(row);
        }
    }
}
