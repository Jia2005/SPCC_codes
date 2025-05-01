//Code Optimization
import java.util.*;  
class Optimization {  
    public static void main(String[] args) {  
        Scanner sc = new Scanner(System.in);  
        System.out.println("Enter no. of expressions");  
        int n = sc.nextInt();  
        sc.nextLine();  
        ArrayList<ArrayList<String>> finalExpressions = new ArrayList<>();  
        HashMap<String, String> uniqueRHS = new HashMap<>();  
        HashMap<String, String> duplicateMapping = new HashMap<>();  
        ArrayList<ArrayList<String>> allExpressions = new ArrayList<>();  
        System.out.println("Enter the expressions");  
        for (int i = 0; i < n; i++) {  
            String line = sc.nextLine().trim();  
            String[] tokens = line.split("\\s+");  
            ArrayList<String> tokenList = new ArrayList<>(Arrays.asList(tokens));  
            allExpressions.add(tokenList);  
        }  
        for (ArrayList<String> expr : allExpressions) {  
            String var = expr.get(0);  
            StringBuilder rhsBuilder = new StringBuilder();  
            for (int i = 2; i < expr.size(); i++) {  
                rhsBuilder.append(expr.get(i));  
                if(i != expr.size() - 1) {  
                    rhsBuilder.append(" ");  
                }  
            }  
            String rhs = rhsBuilder.toString();  
            if (uniqueRHS.containsKey(rhs)) {  
                duplicateMapping.put(var, uniqueRHS.get(rhs));  
            } else {  
                uniqueRHS.put(rhs, var);  
                finalExpressions.add(expr);  
            }  
        }  
        for (ArrayList<String> expr : finalExpressions) {  
            for (int i = 0; i < expr.size(); i++) {  
                String token = expr.get(i);  
                while(duplicateMapping.containsKey(token)) {  
                    token = duplicateMapping.get(token);  
                }  
                expr.set(i, token);  
            }  
        }  
        System.out.println("\nOutput:");  
        for (ArrayList<String> expr : finalExpressions) {  
            StringJoiner joiner = new StringJoiner(" ");  
            for (String token : expr) {  
                joiner.add(token);  
            }  
            System.out.println(joiner.toString());  
        }  
        sc.close();  
    }  
}
