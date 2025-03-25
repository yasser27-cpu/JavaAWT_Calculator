
package calculatorui;

import java.awt.*;
import java.awt.event.*;

public class CalculatorUI extends Frame implements ActionListener, KeyListener {
    private TextField textField;
    private String operator = "";
    private double num1, num2, result;
    private boolean isOperatorClicked = false;

    public CalculatorUI() {
        
        setTitle("Simple Calculator");
        setSize(350, 500);
        setLayout(new BorderLayout());

       
        textField = new TextField();
        textField.setFont(new Font("Arial", Font.BOLD, 30));
        textField.setEditable(false);
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setPreferredSize(new Dimension(350, 70));
        textField.addKeyListener(this);
        textField.setFocusable(true);
        add(textField, BorderLayout.NORTH);

        
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+",
            "<-", "(", ")", "%"
        };

        
        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setPreferredSize(new Dimension(50, 50));
            button.addActionListener(this);
            panel.add(button);
        }

        
        add(panel, BorderLayout.CENTER);

        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        handleInput(e.getActionCommand());
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();
        
        if (Character.isDigit(keyChar)) {
            handleInput(String.valueOf(keyChar));
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            handleInput("<-");
        } else if (keyCode == KeyEvent.VK_ENTER) {
            handleInput("=");
        } else if (keyCode == KeyEvent.VK_ADD || keyChar == '+') {
            handleInput("+");
        } else if (keyCode == KeyEvent.VK_SUBTRACT || keyChar == '-') {
            handleInput("-");
        } else if (keyCode == KeyEvent.VK_MULTIPLY || keyChar == '*') {
            handleInput("*");
        } else if (keyCode == KeyEvent.VK_DIVIDE || keyChar == '/') {
            handleInput("/");
        }
        e.consume(); 
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {
        e.consume(); 
    }

    private void handleInput(String command) {
        if (command.matches("[0-9]")) {
            if (isOperatorClicked) {
                textField.setText("");
                isOperatorClicked = false;
            }
            textField.setText(textField.getText() + command);
        } else if (command.equals("C")) {
            textField.setText("");
            num1 = num2 = result = 0;
            operator = "";
        } else if (command.equals("<-") && !textField.getText().isEmpty()) {
            textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
        } else if (command.equals("=") && !operator.isEmpty() && !textField.getText().isEmpty()) {
            try {
                num2 = Double.parseDouble(textField.getText().trim());
                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "*": result = num1 * num2; break;
                    case "/": result = num2 != 0 ? num1 / num2 : 0; break;
                    case "%": result = num1 % num2; break;
                }
                textField.setText((result == (int) result) ? String.valueOf((int) result) : String.valueOf(result));
                operator = "";
            } catch (NumberFormatException ex) {
                textField.setText("Error");
            }
        } else if (!textField.getText().isEmpty() && operator.isEmpty()) {
            try {
                num1 = Double.parseDouble(textField.getText().trim());
                operator = command;
                isOperatorClicked = true;
            } catch (NumberFormatException ex) {
                textField.setText("Error");
            }
        }
    }

    public static void main(String[] args) {
        new CalculatorUI();
    }
}