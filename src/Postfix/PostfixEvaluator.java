package Postfix;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class PostfixEvaluator {
    private Stack<Integer> stack;

    public PostfixEvaluator() {
        stack = new Stack<>();
    }

    public int evaluate(String expression) {
        String[] tokens = expression.split("\\s+");
        for (String token : tokens) {
            if (token.matches("-?\\d+")) {
                stack.push(Integer.parseInt(token));
            } else if (token.matches("[+\\-*/]")) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Error: Insuficientes operandos para la operación");
                }
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = performOperation(token.charAt(0), operand1, operand2);
                stack.push(result);
            } else {
                throw new IllegalArgumentException("Error: Token no válido en la expresión: " + token);
            }
        }

        if (stack.size() == 1) {
            return stack.pop();
        } else {
            throw new IllegalArgumentException("Error: Expresión incompleta o incorrecta");
        }
    }

    private int performOperation(char operator, int operand1, int operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Error: División entre cero");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Error: Operador no válido: " + operator);
        }
    }

    public static void main(String[] args) {
        System.out.println("Calculadora de expresiones en notación Postfix\n");

        try (BufferedReader reader = new BufferedReader(new FileReader("datos.txt"))) {
            String line;
            PostfixEvaluator calculator = new PostfixEvaluator();

            while ((line = reader.readLine()) != null) {
                try {
                    int result = calculator.evaluate(line.trim());
                    System.out.println("Expresión: " + line.trim() + " = " + result);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
