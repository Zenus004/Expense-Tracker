import java.awt.*;
import javax.swing.*;

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExpenseTracker app = new ExpenseTracker();
            app.setVisible(true);
        });
    }

    public ExpenseTracker() {
        setTitle("Expense Tracker App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        totalAmountField = new JTextField(10);
        productTitleField = new JTextField(10);
        userAmountField = new JTextField(10);
        amountLabel = new JLabel("0");
        expenditureLabel = new JLabel("0");
        balanceLabel = new JLabel("0");
        listContainer = new JPanel();

        setLayout(new BorderLayout());
        setBackground(Color.decode("#f0f0f0"));

        add(createGridPanel(), BorderLayout.NORTH);
        add(createOutputPanel(), BorderLayout.CENTER);

        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Budget input
        gridPanel.add(new JLabel("Budget"), gbc);
        gbc.gridx = 1;
        gridPanel.add(totalAmountField, gbc);
        gbc.gridx = 2;
        JButton setBudgetButton = new JButton("Set Budget");
        setButtonStyle(setBudgetButton);
        setBudgetButton.addActionListener(e -> setBudget());
        gridPanel.add(setBudgetButton, gbc);

        // Expense input
        gbc.gridx = 0;
        gbc.gridy = 1;
        gridPanel.add(new JLabel("Product Title"), gbc);
        gbc.gridx = 1;
        gridPanel.add(productTitleField, gbc);
        gbc.gridy = 2;
        gridPanel.add(new JLabel("Cost of Product"), gbc);
        gbc.gridx = 1;
        gridPanel.add(userAmountField, gbc);
        gbc.gridx = 2;
        JButton checkAmountButton = new JButton("Check Amount");
        setButtonStyle(checkAmountButton);
        checkAmountButton.addActionListener(e -> checkAmount());
        gridPanel.add(checkAmountButton, gbc);

        return gridPanel;
    }

    private JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout());

        // Output
        JPanel labelsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        labelsPanel.setBackground(Color.decode("#673AB7"));
        labelsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        labelsPanel.add(createOutputLabel("Total Budget", amountLabel));
        labelsPanel.add(createOutputLabel("Expenses", expenditureLabel));
        labelsPanel.add(createOutputLabel("Balance", balanceLabel));

        outputPanel.add(labelsPanel, BorderLayout.NORTH);

        // Expense List
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(Color.decode("#FFC107"));
        listContainer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        listContainer.add(new JLabel("Expense List"));

        outputPanel.add(listContainer, BorderLayout.CENTER);

        return outputPanel;
    }

    private JPanel createOutputLabel(String title, JLabel valueLabel) {
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(Color.decode("#2196F3"));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setForeground(Color.white);

        labelPanel.add(titleLabel, BorderLayout.NORTH);
        labelPanel.add(valueLabel, BorderLayout.CENTER);

        return labelPanel;
    }

    private void setButtonStyle(JButton button) {
        button.setBackground(Color.decode("#4CAF50")); // Green color
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void setBudget() {
        try {
            totalAmount = Double.parseDouble(totalAmountField.getText());
            amountLabel.setText(String.valueOf(totalAmount));
            balanceLabel.setText(String.valueOf(totalAmount - totalExpenditure));
            totalAmountField.setText("");
        } catch (NumberFormatException e) {
            showAlert("Invalid input for budget");
        }
    }

    private void checkAmount() {
        try {
            double expense = Double.parseDouble(userAmountField.getText());
            totalExpenditure += expense;
            expenditureLabel.setText(String.valueOf(totalExpenditure));

            // Create list item
            JPanel listItem = new JPanel(new FlowLayout(FlowLayout.LEFT));
            listItem.setBackground(Color.decode("#FFEB3B"));
            listItem.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            listItem.add(new JLabel(productTitleField.getText()));
            listItem.add(new JLabel(String.valueOf(expense)));
            listContainer.add(listItem);

            balanceLabel.setText(String.valueOf(totalAmount - totalExpenditure));

            // Clear input fields
            productTitleField.setText("");
            userAmountField.setText("");
        } catch (NumberFormatException e) {
            showAlert("Invalid input for expense");
        }
    }

    private void showAlert(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}