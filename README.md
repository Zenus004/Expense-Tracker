# 💰 Expense Tracker

A modern, user-friendly desktop expense tracking application built with Java Swing. This application helps you manage your personal finances by tracking expenses against a set budget with an intuitive graphical interface and real-time budget monitoring.

## ✨ Features

- **💰 Budget Management**: Set and manage your total budget with input validation
- **📊 Real-time Dashboard**: Visual overview of budget, expenses, and remaining balance
- **💸 Expense Tracking**: Add expenses with product names and amounts
- **📈 Progress Monitoring**: Visual progress bar showing budget usage percentage
- **🗑️ Expense Management**: Delete individual expenses with one-click removal
- **🔄 Data Reset**: Clear all data with confirmation dialog
- **⚠️ Smart Alerts**: Color-coded warnings when budget is running low
- **🎨 Modern UI**: Clean, responsive interface with custom styling
- **💱 Currency Formatting**: Professional currency display in Indian Rupees (₹)
- **✅ Input Validation**: Comprehensive error handling and user feedback

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher
- **Java Runtime Environment (JRE)**: For running the compiled application

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/Zenus004/Expense-Tracker.git
   cd Expense-Tracker
   ```

2. **Compile the Java file**
   ```bash
   javac ExpenseTracker.java
   ```

3. **Run the application**
   ```bash
   java ExpenseTracker
   ```

### Alternative: Using IDE

1. Open your preferred Java IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Import or open the `ExpenseTracker.java` file
3. Run the main method in the `ExpenseTracker` class

## 🛠️ Built With

- **Java Swing**: GUI framework for the desktop interface
- **Java AWT**: Advanced Window Toolkit for graphics and UI components
- **Java Standard Library**: Built-in libraries for formatting and collections

## 📱 How to Use

### Setting Up Your Budget
1. Enter your total budget amount in the "Set Budget" field
2. Click "Set Budget" to initialize your expense tracking
3. Your budget will be displayed in the dashboard

### Adding Expenses
1. Enter the product/service name in the "Product name" field
2. Enter the expense amount in the "Amount" field
3. Click "Add Expense" to record the transaction
4. The expense will appear in your expense history below

### Monitoring Your Budget
- **Dashboard Cards**: View your total budget, total expenses, and remaining balance
- **Progress Bar**: Visual indicator of budget usage percentage
- **Color Coding**: 
  - 🟢 Green: Safe spending (< 70% of budget)
  - 🟡 Yellow: Moderate spending (70-90% of budget)
  - 🔴 Red: High spending (> 90% of budget)

### Managing Expenses
- **Delete Expenses**: Click the "X" button next to any expense to remove it
- **Clear All Data**: Use "Clear All" button to reset everything (with confirmation)

## 🎨 User Interface

The application features a modern, card-based design with:
- **Clean Layout**: Organized sections for budget setting, statistics, and expense history
- **Responsive Design**: Adapts to different window sizes
- **Modern Color Scheme**: Professional blue and orange accent colors
- **Smooth Interactions**: Hover effects and visual feedback
- **Progress Visualization**: Real-time budget usage tracking

## 📊 Key Components

- **Budget Setting Panel**: Input field and controls for setting your budget
- **Statistics Dashboard**: Three cards showing budget, expenses, and balance
- **Progress Tracking**: Visual progress bar with percentage display
- **Expense History**: Scrollable list of all recorded expenses
- **Action Buttons**: Styled buttons for all user interactions

## 🔧 Technical Features

- **Input Validation**: Prevents negative amounts and empty fields
- **Number Formatting**: Professional currency display with comma separators
- **Memory Management**: Efficient ArrayList-based expense storage
- **Event Handling**: Responsive button clicks and user interactions
- **Custom Styling**: Hand-crafted UI components with modern aesthetics

## 🚨 Error Handling

The application includes comprehensive error handling for:
- Invalid number inputs
- Negative budget amounts
- Empty product names
- Expenses exceeding available budget
- Budget not set before adding expenses

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. **Make your changes**
4. **Commit your changes**
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
5. **Push to the branch**
   ```bash
   git push origin feature/AmazingFeature
   ```
6. **Open a Pull Request**

### Ideas for Contributions
- Add expense categories
- Implement data persistence (save/load from file)
- Add expense filtering and search
- Create charts and graphs for spending analysis
- Add multiple currency support
- Implement expense editing functionality

## 🙏 Acknowledgments

- Java Swing documentation and community
- Modern UI design principles for desktop applications
- Open source Java development community

---
