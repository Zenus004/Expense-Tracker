import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTracker extends JFrame {
    private JTextField totalAmountField;
    private JTextField productTitleField;
    private JTextField userAmountField;
    private JLabel amountLabel;
    private JLabel expenditureLabel;
    private JLabel balanceLabel;
    private JPanel listContainer;
    private JProgressBar budgetProgressBar;
    private JLabel progressLabel;

    private double totalAmount = 0;
    private double totalExpenditure = 0;
    private final DecimalFormat currencyFormat = new DecimalFormat("#,##0.00");
    private List<ExpenseItem> expenses = new ArrayList<>();

    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(64, 81, 181);
    private static final Color ACCENT_COLOR = new Color(255, 87, 34);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color WARNING_COLOR = new Color(255, 193, 7);
    private static final Color DANGER_COLOR = new Color(244, 67, 54);
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    private static final Color TEXT_SECONDARY = new Color(117, 117, 117);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException e) {
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            ExpenseTracker app = new ExpenseTracker();
            app.setVisible(true);
        });
    }

    public ExpenseTracker() {
        setTitle("Expense Tracker");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
        setupLayout();
        setLocationRelativeTo(null);

        Timer timer = new Timer(50, null);
        final int[] alpha = { 0 };
        timer.addActionListener(e -> {
            alpha[0] += 5;
            if (alpha[0] >= 255) {
                alpha[0] = 255;
                timer.stop();
            }
            repaint();
        });
        timer.start();
    }

    private void initializeComponents() {
        totalAmountField = createStyledTextField("Enter your budget...");
        productTitleField = createStyledTextField("Product name...");
        userAmountField = createStyledTextField("Amount...");

        amountLabel = createValueLabel("₹0.00");
        expenditureLabel = createValueLabel("₹0.00");
        balanceLabel = createValueLabel("₹0.00");

        budgetProgressBar = new JProgressBar(0, 100);
        budgetProgressBar.setStringPainted(true);
        budgetProgressBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        budgetProgressBar.setForeground(SUCCESS_COLOR);
        budgetProgressBar.setBackground(new Color(240, 240, 240));
        budgetProgressBar.setBorder(BorderFactory.createEmptyBorder());

        progressLabel = new JLabel("Budget Usage: 0%", JLabel.CENTER);
        progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        progressLabel.setForeground(TEXT_SECONDARY);

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(BACKGROUND_COLOR);
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !hasFocus()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(TEXT_SECONDARY);
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    FontMetrics fm = g2.getFontMetrics();
                    int x = getInsets().left;
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                    g2.drawString(placeholder, x, y);
                    g2.dispose();
                }
            }
        };

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(createRoundedBorder(CARD_COLOR, 1));
        field.setBackground(CARD_COLOR);
        field.setForeground(TEXT_PRIMARY);
        field.setPreferredSize(new Dimension(200, 40));

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(createRoundedBorder(PRIMARY_COLOR, 2));
                field.repaint();
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(createRoundedBorder(new Color(220, 220, 220), 1));
                field.repaint();
            }
        });

        return field;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(CARD_COLOR);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding to prevent clipping
        return label;
    }

    private void setupLayout() {
        setLayout(new BorderLayout(0, 20));
        getContentPane().setBackground(BACKGROUND_COLOR);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);

        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Expense Tracker", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setOpaque(false);

        mainPanel.add(createInputPanel(), BorderLayout.NORTH);
        mainPanel.add(createStatsPanel(), BorderLayout.CENTER);
        mainPanel.add(createExpenseListPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = createCard();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        addSectionTitle(inputPanel, "Set Budget", gbc, 0);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(totalAmountField, gbc);

        gbc.gridx = 1;
        JButton setBudgetBtn = createStyledButton("Set Budget", PRIMARY_COLOR);
        setBudgetBtn.addActionListener(e -> setBudget());
        inputPanel.add(setBudgetBtn, gbc);

        gbc.gridx = 2;
        JButton clearBtn = createStyledButton("Clear All", DANGER_COLOR);
        clearBtn.addActionListener(e -> clearAll());
        inputPanel.add(clearBtn, gbc);

        addSectionTitle(inputPanel, "Add Expense", gbc, 2);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(productTitleField, gbc);

        gbc.gridx = 1;
        inputPanel.add(userAmountField, gbc);

        gbc.gridx = 2;
        JButton addExpenseBtn = createStyledButton("Add Expense", SUCCESS_COLOR);
        addExpenseBtn.addActionListener(e -> checkAmount());
        inputPanel.add(addExpenseBtn, gbc);

        return inputPanel;
    }

    private void addSectionTitle(JPanel panel, String title, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3;
        JLabel sectionLabel = new JLabel(title);
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionLabel.setForeground(TEXT_PRIMARY);
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(row == 0 ? 0 : 20, 0, 10, 0));
        panel.add(sectionLabel, gbc);
        gbc.gridwidth = 1;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatCard("Total Budget", amountLabel, PRIMARY_COLOR));
        statsPanel.add(createStatCard("Total Expenses", expenditureLabel, ACCENT_COLOR));
        statsPanel.add(createStatCard("Remaining", balanceLabel, SUCCESS_COLOR));

        return statsPanel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 5));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(CARD_COLOR);

        valueLabel.setForeground(CARD_COLOR);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createExpenseListPanel() {
        JPanel container = createCard();
        container.setLayout(new BorderLayout());

        JLabel listTitle = new JLabel("Expense History");
        listTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        listTitle.setForeground(TEXT_PRIMARY);
        listTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel progressPanel = new JPanel(new BorderLayout(0, 5));
        progressPanel.setOpaque(false);
        progressPanel.add(progressLabel, BorderLayout.NORTH);
        progressPanel.add(budgetProgressBar, BorderLayout.CENTER);
        progressPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(listTitle, BorderLayout.NORTH);
        headerPanel.add(progressPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(BACKGROUND_COLOR);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        scrollPane.setPreferredSize(new Dimension(750, 200));

        container.add(headerPanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);

        return container;
    }

    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(CARD_COLOR);
        card.setBorder(createRoundedBorder(new Color(230, 230, 230), 1));
        return card;
    }

    private Border createRoundedBorder(Color color, int thickness) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, thickness),
                BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 220));
                } else {
                    g2.setColor(bgColor);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void setBudget() {
        try {
            double inputBudget = Double.parseDouble(totalAmountField.getText());
            if (inputBudget < 0) {
                showStyledAlert("Budget cannot be negative.", "Invalid Budget", DANGER_COLOR);
                return;
            }
            totalAmount = inputBudget;
            totalExpenditure = 0;
            expenses.clear();

            updateLabels();
            updateProgress();
            listContainer.removeAll();
            listContainer.revalidate();
            listContainer.repaint();
            totalAmountField.setText("");

            showStyledAlert("Budget set successfully!", "Success", SUCCESS_COLOR);
        } catch (NumberFormatException e) {
            showStyledAlert("Please enter a valid number for budget.", "Invalid Input", WARNING_COLOR);
        }
    }

    private void clearAll() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to clear all data?",
                "Confirm Clear",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            totalAmount = 0;
            totalExpenditure = 0;
            expenses.clear();

            totalAmountField.setText("");
            productTitleField.setText("");
            userAmountField.setText("");

            amountLabel.setText("₹0.00");
            expenditureLabel.setText("₹0.00");
            balanceLabel.setText("₹0.00");

            budgetProgressBar.setValue(0);
            progressLabel.setText("Budget Usage: 0%");

            listContainer.removeAll();
            listContainer.revalidate();
            listContainer.repaint();
        }
    }

    private void checkAmount() {
        try {
            String product = productTitleField.getText().trim();
            double expense = Double.parseDouble(userAmountField.getText());

            if (product.isEmpty()) {
                showStyledAlert("Product title cannot be empty.", "Missing Information", WARNING_COLOR);
                return;
            }

            if (expense <= 0) {
                showStyledAlert("Expense amount should be greater than zero.", "Invalid Amount", WARNING_COLOR);
                return;
            }

            if (totalAmount == 0) {
                showStyledAlert("Please set a budget first.", "No Budget Set", WARNING_COLOR);
                return;
            }

            if (totalExpenditure + expense > totalAmount) {
                showStyledAlert("Expense exceeds available budget!\nRemaining: ₹" +
                        currencyFormat.format(totalAmount - totalExpenditure), "Budget Exceeded", DANGER_COLOR);
                return;
            }

            totalExpenditure += expense;
            expenses.add(new ExpenseItem(product, expense));

            updateLabels();
            updateProgress();
            addExpenseToList(product, expense);

            productTitleField.setText("");
            userAmountField.setText("");

            animateNewExpense();

        } catch (NumberFormatException e) {
            showStyledAlert("Please enter a valid number for expense.", "Invalid Input", WARNING_COLOR);
        }
    }

    private void addExpenseToList(String product, double expense) {
        JPanel listItem = new JPanel(new BorderLayout(10, 0));
        listItem.setBackground(CARD_COLOR);
        listItem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 240, 240), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        listItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);

        JLabel bulletLabel = new JLabel("*");
        bulletLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bulletLabel.setForeground(PRIMARY_COLOR);

        JLabel titleLabel = new JLabel(" " + product);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_PRIMARY);

        leftPanel.add(bulletLabel);
        leftPanel.add(titleLabel);

        JLabel amountLabel = new JLabel("₹" + currencyFormat.format(expense));
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        amountLabel.setForeground(ACCENT_COLOR);

        JButton deleteBtn = new JButton("X");
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteBtn.setForeground(DANGER_COLOR);
        deleteBtn.setContentAreaFilled(false);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.setToolTipText("Delete this expense");
        deleteBtn.setPreferredSize(new Dimension(25, 25));

        final JPanel itemRef = listItem;
        deleteBtn.addActionListener(e -> deleteExpense(product, expense, itemRef));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(amountLabel);
        rightPanel.add(deleteBtn);

        listItem.add(leftPanel, BorderLayout.WEST);
        listItem.add(rightPanel, BorderLayout.EAST);

        listContainer.add(listItem);
        listContainer.add(Box.createVerticalStrut(5));
        listContainer.revalidate();
        listContainer.repaint();
    }

    private void deleteExpense(String product, double expense, JPanel itemPanel) {
        totalExpenditure -= expense;
        expenses.removeIf(item -> item.name.equals(product) && item.amount == expense);

        listContainer.remove(itemPanel);
        updateLabels();
        updateProgress();
        listContainer.revalidate();
        listContainer.repaint();
    }

    private void updateLabels() {
        amountLabel.setText("₹" + currencyFormat.format(totalAmount));
        expenditureLabel.setText("₹" + currencyFormat.format(totalExpenditure));
        balanceLabel.setText("₹" + currencyFormat.format(totalAmount - totalExpenditure));

        double remaining = totalAmount - totalExpenditure;
        if (remaining < totalAmount * 0.2 && totalAmount > 0) {
            balanceLabel.getParent().setBackground(DANGER_COLOR);
        } else if (remaining < totalAmount * 0.5 && totalAmount > 0) {
            balanceLabel.getParent().setBackground(WARNING_COLOR);
        } else {
            balanceLabel.getParent().setBackground(SUCCESS_COLOR);
        }
    }

    private void updateProgress() {
        if (totalAmount > 0) {
            int percentage = (int) ((totalExpenditure / totalAmount) * 100);
            budgetProgressBar.setValue(percentage);
            progressLabel.setText("Budget Usage: " + percentage + "%");

            if (percentage >= 90) {
                budgetProgressBar.setForeground(DANGER_COLOR);
            } else if (percentage >= 70) {
                budgetProgressBar.setForeground(WARNING_COLOR);
            } else {
                budgetProgressBar.setForeground(SUCCESS_COLOR);
            }
        } else {
            budgetProgressBar.setValue(0);
            progressLabel.setText("Budget Usage: 0%");
        }
    }

    private void animateNewExpense() {
        Timer timer = new Timer(50, null);
        final int[] count = { 0 };
        timer.addActionListener(e -> {
            count[0]++;
            if (count[0] > 10) {
                timer.stop();
            }
            listContainer.repaint();
        });
        timer.start();
    }

    private void showStyledAlert(String message, String title, Color color) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(400, 180);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        contentPanel.setBackground(CARD_COLOR);

        JLabel messageLabel = new JLabel(
                "<html><div style='text-align: center; line-height: 1.4;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_PRIMARY);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton okButton = createStyledButton("OK", color);
        okButton.addActionListener(e -> dialog.dispose());
        okButton.setPreferredSize(new Dimension(100, 35));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.add(okButton);

        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(contentPanel);
        dialog.setVisible(true);
    }

    private static class ExpenseItem {
        String name;
        double amount;

        ExpenseItem(String name, double amount) {
            this.name = name;
            this.amount = amount;
        }
    }
}