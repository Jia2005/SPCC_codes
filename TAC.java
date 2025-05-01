//Three Address Code
import java.util.*;  
public class TAC {  
    static int VarCount = 1;  
    public static String convert(String expression) {  
        Stack<String> ops = new Stack<>();  
        Stack<String> vals = new Stack<>();  
        StringBuilder result = new StringBuilder();  
        for (int i = 0; i < expression.length(); i++) {  
            char ch = expression.charAt(i);  
            if (Character.isLetterOrDigit(ch)) {  
                vals.push(String.valueOf(ch));  
            } else if (ch == '(') {  
                ops.push(String.valueOf(ch));  
            } else if (ch == ')') {  
                while (!ops.isEmpty() && ops.peek().charAt(0) != '(') {  
                    String op = ops.pop();  
                    String v2 = vals.pop();  
                    String v1 = vals.pop();  
                    String tempVar = "temp" + VarCount++;  
                    result.append(tempVar).append(" = ").append(v1).append(" ").append(op).append(" ").append(v2).append("\n");  
                    vals.push(tempVar);  
                }  
                ops.pop();  
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {  
                while (!ops.isEmpty() && precedence(ch) <= precedence(ops.peek().charAt(0))) {  
                    String op = ops.pop();  
                    String v2 = vals.pop();  
                    String v1 = vals.pop();  
                    String tempVar = "temp" + VarCount++;  
                    result.append(tempVar).append(" = ").append(v1).append(" ").append(op).append(" ").append(v2).append("\n");  
                    vals.push(tempVar);  
                }  
                ops.push(String.valueOf(ch));  
            }  
        }  
        while (!ops.isEmpty()) {  
            String op = ops.pop();  
            String v2 = vals.pop();  
            String v1 = vals.pop();  
            String tempVar = "temp" + VarCount++;  
            result.append(tempVar).append(" = ").append(v1).append(" ").append(op).append(" ").append(v2).append("\n");  
            vals.push(tempVar);  
        }  
        return result.toString();  
    }  
    private static int precedence(char operator) {  
        if (operator == '+' || operator == '-') {  
            return 1;  
        } else if (operator == '*' || operator == '/') {  
            return 2;  
        }  
        return 0;  
    }  
    public static void main(String[] args) {  
        Scanner sc = new Scanner(System.in);  
        System.out.println("Enter the expression: ");  
        String expression = sc.nextLine();  
        String tac = convert(expression);  
        System.out.println("Generated Three Address Code:");  
        System.out.println(tac);  
  } 
}
