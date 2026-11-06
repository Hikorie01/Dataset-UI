import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;

public class DataSetUI extends JFrame implements ActionListener {
    private JComboBox<String> typeBox;
    private JTextField nameField, valueField;
    private JButton addBtn, sampleBtn, clearBtn;
    private JTextArea output;

    private DataSet bankData = new DataSet();
    private DataSet coinData = new DataSet();
    private DataSet studentData = new DataSet();
    private DecimalFormat df = new DecimalFormat("0.00");

    public DataSetUI() {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout(5, 5));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // input form
        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        typeBox = new JComboBox<>(new String[] {"Bank Account", "Coin", "Student"});
        nameField = new JTextField(15);
        valueField = new JTextField(15);
        form.add(new JLabel("Type:"));
        form.add(typeBox);
        form.add(new JLabel("Name (Coin / Student):"));
        form.add(nameField);
        form.add(new JLabel("Balance / Value / Height:"));
        form.add(valueField);

        // buttons
        JPanel buttons = new JPanel(new FlowLayout());
        addBtn = new JButton("Add");
        sampleBtn = new JButton("Load Sample Data");
        clearBtn = new JButton("Clear");
        buttons.add(addBtn);
        buttons.add(sampleBtn);
        buttons.add(clearBtn);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        output = new JTextArea(12, 40);
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));

        pane.add(top, BorderLayout.NORTH);
        pane.add(new JScrollPane(output), BorderLayout.CENTER);

        // register event listeners
        addBtn.addActionListener(this);
        sampleBtn.addActionListener(this);
        clearBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            addItem();
        } else if (e.getSource() == sampleBtn) {
            bankData.add(new BankAccount(0));
            bankData.add(new BankAccount(10000));
            bankData.add(new BankAccount(2000));
            coinData.add(new Coin(0.25, "Quarter"));
            coinData.add(new Coin(0.1, "Dime"));
            coinData.add(new Coin(0.05, "Nickel"));
            studentData.add(new Student("Ali", 157.50));
            studentData.add(new Student("Alex", 165.00));
            studentData.add(new Student("Maria", 147.00));
            showResult();
        } else if (e.getSource() == clearBtn) {
            bankData = new DataSet();
            coinData = new DataSet();
            studentData = new DataSet();
            output.setText("");
        }
    }

    private void addItem() {
        String type = (String) typeBox.getSelectedItem();
        String name = nameField.getText().trim();
        double value;
        try {
            value = Double.parseDouble(valueField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            return;
        }

        if (type.equals("Bank Account")) {
            bankData.add(new BankAccount(value));
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name.");
            return;
        } else if (type.equals("Coin")) {
            coinData.add(new Coin(value, name));
        } else {
            studentData.add(new Student(name, value));
        }
        nameField.setText("");
        valueField.setText("");
        showResult();
    }

    private void showResult() {
        String text = "";
        if (bankData.getMax() != null) {
            text += "Bank Average Balance: " + bankData.getAverage() + "\n";
            text += "Bank Highest Balance: " + bankData.getMax().getMeasure() + "\n\n";
        }
        if (coinData.getMax() != null) {
            text += "Average Coin Value: " + df.format(coinData.getAverage()) + "\n";
            text += "Highest Coin Balance: " + df.format(coinData.getMax().getMeasure()) + "\n";
            text += "Highest Coin's Name: " + coinData.getMax().getName() + "\n\n";
        }
        if (studentData.getMax() != null) {
            text += "Average Student's Height: " + df.format(studentData.getAverage()) + "\n";
            text += "Tallest Height: " + df.format(studentData.getMax().getMeasure()) + "\n";
            text += "Tallest Student's Name: " + studentData.getMax().getName() + "\n";
        }
        output.setText(text);
    }

    public static void main(String[] args) {
        DataSetUI frame = new DataSetUI();
        frame.setTitle("DataSet");
        frame.setSize(500, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
