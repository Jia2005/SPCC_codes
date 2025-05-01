//Left_Recursion
import java.util.*;   
public class LeftRecursion {  
    public static void main(String[] args) {  
        Scanner scanner = new Scanner(System.in);  
        System.out.println("Enter the grammar with productions in the format: Nonterminal -> Production1 | Production2 | ...");  
        System.out.println("Type 'DONE' when finished.");           
        Map<String, Set<String>> grammar = new HashMap<>();           
        while (true) {  
            String line = scanner.nextLine().trim();  
            if (line.equalsIgnoreCase("DONE")) {  
                break;  
            }  
            if (line.contains("->")) {  
                String[] parts = line.split("->");  
                String nonterminal = parts[0].trim();  
                String[] productions = parts[1].split("\\|");  
                Set<String> productionSet = new HashSet<>();                   
                for (String production : productions) {  
                    productionSet.add(production.trim());  
                }                   
                grammar.merge(nonterminal, productionSet, (oldSet, newSet) -> {  
                    oldSet.addAll(newSet);  
                    return oldSet;  
                });  
            }  
 
            else {  
                System.out.println("Invalid input format, please enter in the correct format (e.g., S -> S+T | S-T | T)");  
            }  
        }           
        Map<String, List<String>> newGrammar = eliminateLeftRecursion(grammar);           
        System.out.println("\nTransformed Grammar (after removing left recursion):");  
        for (Map.Entry<String, List<String>> entry : newGrammar.entrySet()) {  
            System.out.println(entry.getKey() + " -> " + String.join(" | ", entry.getValue()));  
        }           
        scanner.close();  
    }  
      
    public static Map<String, List<String>> eliminateLeftRecursion(Map<String, Set<String>> grammar) {  
        Map<String, List<String>> newGrammar = new HashMap<>();  
        for (String nonterminal : grammar.keySet()) {  
            List<String> rules = new ArrayList<>(grammar.get(nonterminal));  
            List<String> alpha = new ArrayList<>();  
            List<String> beta = new ArrayList<>();               
            for (String rule : rules) {  
                if (rule.startsWith(nonterminal)) {  
                    alpha.add(rule.substring(nonterminal.length()));  
                } else {  
                    beta.add(rule);  
                }  
            }               
            if (alpha.isEmpty()) {  
                newGrammar.put(nonterminal, new ArrayList<>(rules));  
            }  
            else {  
                String newNonterminal = nonterminal + "'";  
                List<String> newAlpha = new ArrayList<>();                   
                for (String b : beta) {  
                    newAlpha.add(b + newNonterminal);  
                }                   
                List<String> newBeta = new ArrayList<>();  
                for (String a : alpha) {  
                    newBeta.add(a + newNonterminal);  
                }  
                newBeta.add("?");                   
                newGrammar.put(nonterminal, newAlpha);  
                newGrammar.put(newNonterminal, newBeta);  
            }  
        }           
        return newGrammar;  
    }  
}
