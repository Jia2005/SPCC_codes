//LL(1) Parser
import java.util.*;  
class LL(1) {  
    public static void main(String[] args) {  
        HashMap<String, ArrayList<String>> Grammar = new HashMap<>();  
        HashMap<String, ArrayList<String>> First = new HashMap<>();  
        HashMap<String, ArrayList<String>> Follow = new HashMap<>(); 
 
        Grammar.put("E", new ArrayList<>(Arrays.asList("TM")));  
        Grammar.put("M", new ArrayList<>(Arrays.asList("+TM", "z")));  
        Grammar.put("T", new ArrayList<>(Arrays.asList("FR")));  
        Grammar.put("R", new ArrayList<>(Arrays.asList("*FR", "z")));  
        Grammar.put("F", new ArrayList<>(Arrays.asList("(E)", "i")));  
         
        First.put("E", new ArrayList<>(Arrays.asList("(", "i")));  
        First.put("M", new ArrayList<>(Arrays.asList("+", "z")));  
        First.put("T", new ArrayList<>(Arrays.asList("(", "i")));  
        First.put("R", new ArrayList<>(Arrays.asList("*", "z")));  
        First.put("F", new ArrayList<>(Arrays.asList("(", "i")));  
         
        Follow.put("E", new ArrayList<>(Arrays.asList("$", ")")));  
        Follow.put("M", new ArrayList<>(Arrays.asList("$", ")")));  
        Follow.put("T", new ArrayList<>(Arrays.asList("+", "$", ")")));  
        Follow.put("R", new ArrayList<>(Arrays.asList("+", "$", ")")));  
        Follow.put("F", new ArrayList<>(Arrays.asList("*", "+", "$", ")")));  
         
  
HashMap<String, HashMap<String, String>> ParsingTable = new HashMap<>();  
        for (String nonTerminal : Grammar.keySet()) {  
            ParsingTable.put(nonTerminal, new HashMap<>());  
        }  
        for (String nonTerminal : Grammar.keySet()) {  
            for (String production : Grammar.get(nonTerminal)) {  
                ArrayList<String> firstSet = getFirstSet(production, First);  
                for (String terminal : firstSet) {  
                    if (!terminal.equals("z")) {  
                        ParsingTable.get(nonTerminal).put(terminal, production);                     }  
                }  
                if (firstSet.contains("z")) {  
                    for (String terminal : Follow.get(nonTerminal)) {  
                        ParsingTable.get(nonTerminal).put(terminal, production);  
                    }  
                }  
            }  
        }  
        System.out.println("Parsing Table:");  
        for (String nonTerminal : ParsingTable.keySet()) {  
            System.out.println(nonTerminal + ":");  
            HashMap<String, String> row = ParsingTable.get(nonTerminal);  
            for (String terminal : row.keySet()) {  
                System.out.println("  " + terminal + " -> " + row.get(terminal));  
            }  
        }  
    }  
    
  
  private static boolean isTerminal(String s) {  
        return !s.equals("$") && !s.equals("E") && !s.equals("M") && !s.equals("T") && 
!s.equals("R") && !s.equals("F");  
    }   
 
    private static ArrayList<String> getFirstSet(String production, HashMap<String, 
ArrayList<String>> First) {  
        ArrayList<String> firstSet = new ArrayList<>();  
        if (production.equals("z")) {  
            firstSet.add("z");  
        } else {  
            char firstChar = production.charAt(0);  
            if (isTerminal(String.valueOf(firstChar))) {  
                firstSet.add(String.valueOf(firstChar));  
            } else {  
                firstSet.addAll(First.get(String.valueOf(firstChar)));  
            }  
        }  
        return firstSet;  
}  
} 
