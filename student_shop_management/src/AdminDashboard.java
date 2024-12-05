import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class AdminDashboard extends JFrame {
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private static ArrayList<Product> productList = new ArrayList<>();
    private UserCart userCart;


    public AdminDashboard(UserCart userCart) {
        this.userCart = userCart;


        setTitle("Admin Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setLayout(new BorderLayout());
        Color backgroundColor = new Color(30, 30, 30);
        Color textColor = Color.WHITE;
        getContentPane().setBackground(backgroundColor);




        String[] productColumnNames = {"Product Name", "Quantity", "Price", "Add Date"};
        productTableModel = new DefaultTableModel(productColumnNames, 0);
        productTable = new JTable(productTableModel);
        productTable.setBackground(new Color(50, 50, 50));
        productTable.setForeground(textColor);
        productTable.setGridColor(Color.GRAY);
        productTable.setFont(new Font("Arial", Font.PLAIN, 18));
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        productTable.setRowHeight(40);
        JScrollPane productScrollPane = new JScrollPane(productTable);


        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(backgroundColor);


        JButton addButton = new JButton("Add Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton backButton = new JButton("Open Campus-Cart");


        addButton.setBackground(new Color(70, 70, 70));
        deleteButton.setBackground(new Color(70, 70, 70));
        backButton.setBackground(new Color(70, 70, 70));


        addButton.setForeground(textColor);
        deleteButton.setForeground(textColor);
        backButton.setForeground(textColor);


        addButton.setFont(new Font("Arial", Font.PLAIN, 18));
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 18));
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));


        actionPanel.add(addButton);
        actionPanel.add(deleteButton);
        actionPanel.add(backButton);


        add(productScrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);


        loadProductsFromDB();




        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String productName = JOptionPane.showInputDialog("Enter Product Name:");
                if (productName == null || productName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AdminDashboard.this, "Product name is required.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                String quantityStr = JOptionPane.showInputDialog("Enter Quantity:");
                if (quantityStr == null || quantityStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AdminDashboard.this, "Quantity is required.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                String priceStr = JOptionPane.showInputDialog("Enter Price:");
                if (priceStr == null || priceStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AdminDashboard.this, "Price is required.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                String addDate = LocalDate.now().toString();




                int quantity = 0;
                double price = 0.0;
                try {
                    quantity = Integer.parseInt(quantityStr);
                    price = Double.parseDouble(priceStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AdminDashboard.this, "Invalid quantity or price format.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }




                String query = "INSERT INTO products (name, quantity, price, add_date) VALUES (?, ?, ?, ?)";
                try (Connection connection = db.getConnection();  // Assuming db.getConnection() works correctly
                     PreparedStatement statement = connection.prepareStatement(query)) {


                    statement.setString(1, productName);
                    statement.setInt(2, quantity);
                    statement.setDouble(3, price);
                    statement.setString(4, addDate);


                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {


                        Product newProduct = new Product(productName, quantity, price, addDate);
                        productList.add(newProduct);




                        productTableModel.addRow(new Object[]{productName, quantity, price, addDate});




                        userCart.refreshProductTable(productList);


                        JOptionPane.showMessageDialog(AdminDashboard.this,
                                "Product added successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AdminDashboard.this,
                            "Error occurred while adding the product: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });




        deleteButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();


            if (selectedRow != -1) {
                String productName = (String) productTable.getValueAt(selectedRow, 0);




                int confirm = JOptionPane.showConfirmDialog(AdminDashboard.this,
                        "Are you sure you want to delete the product: " + productName + "?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);


                if (confirm == JOptionPane.YES_OPTION) {


                    String query = "DELETE FROM products WHERE name = ?";
                    try (Connection connection = db.getConnection();
                         PreparedStatement statement = connection.prepareStatement(query)) {


                        statement.setString(1, productName);


                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {


                            productList.removeIf(product -> product.getName().equals(productName));
                            productTableModel.removeRow(selectedRow);




                            userCart.refreshProductTable(productList);


                            JOptionPane.showMessageDialog(AdminDashboard.this,
                                    "Product deleted successfully!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(AdminDashboard.this,
                                    "Error occurred while deleting the product.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(AdminDashboard.this,
                                "Error occurred while deleting the product.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(AdminDashboard.this,
                        "Please select a product to delete.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });




        backButton.addActionListener(e -> {


            AdminDashboard.this.setVisible(false);




            userCart.setVisible(true);
        });


        setVisible(true);
    }


    private void loadProductsFromDB() {
        String query = "SELECT * FROM products";
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {




            productTableModel.setRowCount(0);




            while (resultSet.next()) {
                String productName = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                String addDate = resultSet.getString("add_date");


                Product product = new Product(productName, quantity, price, addDate);
                productList.add(product);




                productTableModel.addRow(new Object[]{productName, quantity, price, addDate});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading products from database: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
