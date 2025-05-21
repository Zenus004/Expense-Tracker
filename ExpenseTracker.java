import java.awt.*;
import javax.swing.*;
import java.text.DecimalFormat;

public class ExpenseTracker extends JFrame {
    private JTextField totalAmountField;
    private JTextField productTitleField;
    private JTextField userAmountField;
    private JLabel amountLabel;
    private JLabel expenditureLabel;
    private JLabel balanceLabel;
    private JPanel listContainer;

    private double totalAmount = 0;
    private double totalExpenditure = 0;
    private final DecimalFormat currencyFormat = new DecimalFormat("#0.00");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExpenseTracker app = new ExpenseTracker();
            app.setVisible(true);
        });
    }

    public ExpenseTracker() {
        setTitle("Expense Tracker App");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        totalAmountField = new JTextField(10);
        productTitleField = new JTextField(10);
        userAmountField = new JTextField(10);
        amountLabel = new JLabel("0.00");
        expenditureLabel = new JLabel("0.00");
        balanceLabel = new JLabel("0.00");
        listContainer = new JPanel();

        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#f0f0f0"));

        add(createGridPanel(), BorderLayout.NORTH);
        add(createOutputPanel(), BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Budget input
        gbc.gridx = 0;
        gbc.gridy = 0;
        gridPanel.add(new JLabel("Budget:"), gbc);
        gbc.gridx = 1;
        gridPanel.add(totalAmountField, gbc);
        gbc.gridx = 2;
        JButton setBudgetButton = new JButton("Set Budget");
        setButtonStyle(setBudgetButton);
        setBudgetButton.addActionListener(e -> setBudget());
        gridPanel.add(setBudgetButton, gbc);

        //Clear button
        gbc.gridx = 3;
        JButton clearButton = new JButton("Clear All");
        setButtonStyle(clearButton);
        clearButton.addActionListener(e -> clearAll());
        gridPanel.add(clearButton, gbc);

        // Expense input
        gbc.gridx = 0;
        gbc.gridy = 1;
        gridPanel.add(new JLabel("Product Title:"), gbc);
        gbc.gridx = 1;
        gridPanel.add(productTitleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gridPanel.add(new JLabel("Cost of Product:"), gbc);
        gbc.gridx = 1;
        gridPanel.add(userAmountField, gbc);
        gbc.gridx = 2;
        JButton checkAmountButton = new JButton("Add Expense");
        setButtonStyle(checkAmountButton);
        checkAmountButton.addActionListener(e -> checkAmount());
        gridPanel.add(checkAmountButton, gbc);

        return gridPanel;
    }

    private JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout());

        // Output labels
        JPanel labelsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        labelsPanel.setBackground(Color.decode("#673AB7"));
        labelsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        labelsPanel.add(createOutputLabel("Total Budget", amountLabel));
        labelsPanel.add(createOutputLabel("Expenses", expenditureLabel));
        labelsPanel.add(createOutputLabel("Balance", balanceLabel));

        outputPanel.add(labelsPanel, BorderLayout.NORTH);

        // Expense list
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(Color.decode("#FFF8E1"));
        listContainer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.setPreferredSize(new Dimension(580, 200));

        outputPanel.add(scrollPane, BorderLayout.CENTER);

        return outputPanel;
    }

    private JPanel createOutputLabel(String title, JLabel valueLabel) {
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(Color.decode("#2196F3"));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        valueLabel.setForeground(Color.white);
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        valueLabel.setHorizontalAlignment(JLabel.CENTER);

        labelPanel.add(titleLabel, BorderLayout.NORTH);
        labelPanel.add(valueLabel, BorderLayout.CENTER);

        return labelPanel;
    }

    private void setButtonStyle(JButton button) {
        button.setBackground(Color.decode("#4CAF50"));
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void setBudget() {
        try {
            double inputBudget = Double.parseDouble(totalAmountField.getText());
            if (inputBudget < 0) {
                showAlert("Budget cannot be negative.");
                return;
            }
            totalAmount = inputBudget;
            totalExpenditure = 0;

            updateLabels();
            listContainer.removeAll();
            listContainer.revalidate();
            listContainer.repaint();
            totalAmountField.setText("");
        } catch (NumberFormatException e) {
            showAlert("Invalid input for budget.");
        }
    }

    private void clearAll() {
        totalAmount = 0;
        totalExpenditure = 0;

        totalAmountField.setText("");
        productTitleField.setText("");
        userAmountField.setText("");

        amountLabel.setText("₹0.00");
        expenditureLabel.setText("₹0.00");
        balanceLabel.setText("₹0.00");

        listContainer.removeAll();
        listContainer.revalidate();
        listContainer.repaint();
    }

    private void checkAmount() {
        try {
            String product = productTitleField.getText().trim();
            double expense = Double.parseDouble(userAmountField.getText());

            if (product.isEmpty()) {
                showAlert("Product title cannot be empty.");
                return;
            }

            if (expense <= 0) {
                showAlert("Expense amount should be greater than zero.");
                return;
            }

            if (totalExpenditure + expense > totalAmount) {
                showAlert("Expense exceeds available budget.");
                return;
            }

            totalExpenditure += expense;
            updateLabels();

            JPanel listItem = new JPanel(new FlowLayout(FlowLayout.LEFT));
            listItem.setBackground(Color.decode("#FFEB3B"));
            listItem.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel title = new JLabel("• " + product + ": ");
            title.setFont(new Font("Arial", Font.BOLD, 12));
            JLabel amount = new JLabel("₹" + currencyFormat.format(expense));
            amount.setFont(new Font("Arial", Font.PLAIN, 12));

            listItem.add(title);
            listItem.add(amount);
            listContainer.add(listItem);

            listContainer.revalidate();
            listContainer.repaint();

            productTitleField.setText("");
            userAmountField.setText("");
        } catch (NumberFormatException e) {
            showAlert("Invalid input for expense.");
        }
    }

    private void updateLabels() {
        amountLabel.setText("₹" + currencyFormat.format(totalAmount));
        expenditureLabel.setText("₹" + currencyFormat.format(totalExpenditure));
        balanceLabel.setText("₹" + currencyFormat.format(totalAmount - totalExpenditure));
    }

    private void showAlert(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
