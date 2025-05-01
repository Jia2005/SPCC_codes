//Lexical Analyzer
import java.io.*;  
import java.util.*;   
class lexical {   
    public static void main(String args[]) throws IOException {  
        String fileName = "code.txt";  
        BufferedReader reader = new BufferedReader(new FileReader(fileName));   
        Set<String> preKeywords = new HashSet<>(Arrays.asList("int", "float", "double", "long", "if", "else", "for", "while", "return"));  
        Set<String> preOperators = new HashSet<>(Arrays.asList("+", "-", "*", "/", "=", "==", "!=", "<", ">", "<=", ">=", "++", "--", "+=", "-="));  
        Set<Character> specialChars = new HashSet<>(Arrays.asList('(', ')', '{', '}', ';', ','));    
        List<String> keywords = new ArrayList<>();  
        List<String> variables = new ArrayList<>();  
        List<String> numbers = new ArrayList<>();  
        List<String> operators = new ArrayList<>();  
        List<Character> special = new ArrayList<>();   
        String line;  
        while ((line = reader.readLine()) != null) {  
            int i = 0;   
            if (line.contains("//")) {  
                line = line.substring(0, line.indexOf("//"));  
            }   
            if (line.contains("/*")) {  
                while (line != null && !line.contains("*/")) {  
                    line = reader.readLine();  
                }  
                if (line != null) {  
                    line = line.substring(line.indexOf("*/") + 2);  
                }  
                continue;  
            }  
            while (i < line.length()) {  
                char ch = line.charAt(i);  
                if (Character.isWhitespace(ch)) {  
                    i++;  
                    continue;  
                }  
                String op = "";  
                while (i < line.length() && preOperators.contains(op + line.charAt(i))) {  
                    op += line.charAt(i);  
                    i++;  
                }  
                if (!op.isEmpty()) {  
                    operators.add(op);  
                    continue;  
                }  
                if (Character.isDigit(ch) || (ch == '.' && i + 1 < line.length() && Character.isDigit(line.charAt(i + 1)))) {  
                    String num = "";  
                    while (i < line.length() && (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.')) {  
                        num += line.charAt(i);  
                        i++;  
                    }  
                    numbers.add(num);  
                    continue;  
                }  
                if (Character.isLetter(ch) || ch == '_') {  
                    String token = "";  
                    while (i < line.length() && (Character.isLetterOrDigit(line.charAt(i)) || line.charAt(i) == '_')) {  
                        token += line.charAt(i);  
                        i++;  
                    }  
                    if (preKeywords.contains(token)) {  
                        keywords.add(token);  
                    } else {  
                        variables.add(token);  
                    }  
                    continue;  
                }  
                if (specialChars.contains(ch)) {  
                    special.add(ch);  
                    i++;  
                    continue;  
                }  
                i++;  
            }  
        }  
        reader.close();  
  
        System.out.println("Keywords: " + keywords);  
        System.out.println("Operators: " + operators);  
        System.out.println("Numbers: " + numbers);  
        System.out.println("Variables: " + variables);  
        System.out.println("Special characters: " + special);  
        System.out.println("Total number of tokens: " + (keywords.size() + operators.size() + numbers.size() + variables.size() + special.size()));  
  }  
}  
