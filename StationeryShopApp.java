import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StationeryShopApp {
    public static void main(String[] args) {
        // Initialize product data
        String[] products = {"Pens", "Pencils", "Notebooks", "Staplers", "Glue"};
        double[] prices = {5, 5, 50, 100, 15};
        int[] quantities = {100, 200, 50, 20, 30};
        double[] totalSales = {0}; // Use an array to allow modification inside event listeners

        // Create the application window
        JFrame frame = new JFrame("Stationery Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Create a JTextArea to display product details
        JTextArea productArea = new JTextArea(getProductList(products, prices, quantities, totalSales[0]));
        productArea.setEditable(false);
        frame.add(new JScrollPane(productArea), BorderLayout.CENTER);

        // Create a panel for purchase inputs
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Purchase Section"));

        JLabel productLabel = new JLabel("Product Number:");
        JTextField productField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();

        JButton purchaseButton = new JButton("Purchase");
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

        inputPanel.add(productLabel);
        inputPanel.add(productField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(purchaseButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(messageLabel, BorderLayout.SOUTH);

        // Handle the purchase action
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int productNum = Integer.parseInt(productField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());

                    // Perform purchase logic
                    String result = purchaseProduct(products, prices, quantities, productNum, quantity, totalSales);
                    messageLabel.setText(result);
                    productArea.setText(getProductList(products, prices, quantities, totalSales[0]));
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Please enter valid numbers!");
                }
            }
        });

        // Display the window
        frame.setVisible(true);
    }

    // Method to return the formatted product list
    private static String getProductList(String[] products, double[] prices, int[] quantities, double totalSales) {
        StringBuilder sb = new StringBuilder("Available Products:\n");
        sb.append("No.\tProduct\tPrice\tStock\n");
        sb.append("---------------------------------\n");
        for (int i = 0; i < products.length; i++) {
            sb.append((i + 1)).append(".\t")
              .append(products[i]).append("\t$")
              .append(prices[i]).append("\t")
              .append(quantities[i]).append("\n");
        }
        sb.append("---------------------------------\n");
        sb.append("Total Sales: $").append(totalSales).append("\n");
        return sb.toString();
    }

    // Method to handle product purchase logic
    private static String purchaseProduct(String[] products, double[] prices, int[] quantities, int productNum, int quantity, double[] totalSales) {
        if (productNum < 1 || productNum > products.length) {
            return "Invalid product number!";
        }

        int index = productNum - 1;
        if (quantity <= 0) {
            return "Quantity must be greater than 0!";
        }

        if (quantity > quantities[index]) {
            return "Insufficient stock for " + products[index] + "!";
        }

        double subtotal = prices[index] * quantity;
        totalSales[0] += subtotal;
        quantities[index] -= quantity;

        return "Purchased " + quantity + " " + products[index] + "(s) for $" + subtotal;
    }
}     