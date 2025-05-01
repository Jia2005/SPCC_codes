//First and Follow together in Java with user-input
import java.util.*;  
import java.io.*;  
public class first {  
    public static void main(String[] args) throws IOException {  
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
        System.out.println("Enter number of non-terminals:");  
        int n = Integer.parseInt(br.readLine());  
  
        HashMap<String, ArrayList<String>> productions = new HashMap<>();  
        String startSymbol = null;  
        for (int i = 0; i < n; i++) {  
            System.out.println("Enter the non-terminal:");  
            String nt = br.readLine().trim();  
            if (startSymbol == null) {  
                startSymbol = nt;  
            }  
            System.out.println("Enter number of productions for " + nt + ":");  
            int num = Integer.parseInt(br.readLine());  
            ArrayList<String> prodList = new ArrayList<>();  
            for (int j = 0; j < num; j++) {  
                System.out.println("Enter production " + (j + 1) + " (use spaces between symbols, and use 'z' for ε):");  
                String prod = br.readLine().trim();  
                prodList.add(prod);  
            }  
            productions.put(nt, prodList);  
        }  
  
        HashMap<String, Set<String>> firstSets = new HashMap<>();  
        for (String nt : productions.keySet()) {  
            firstSets.put(nt, new HashSet<>());  
        }  
        for (String nt : productions.keySet()) {  
            computeFirst(nt, productions, firstSets);  
        }  
  
        System.out.println("\nFIRST sets:");  
        for (String nt : productions.keySet()) {  
            System.out.println("FIRST(" + nt + ") = " +  firstSets.get(nt));  
        }  
  
        HashMap<String, Set<String>> followSets = new HashMap<>();  
        for (String nt : productions.keySet()) {  
            followSets.put(nt, new HashSet<>());  
        }  
        followSets.get(startSymbol).add("$");  
  
        boolean changed;  
        do {  
            changed = false;  
            for (String A : productions.keySet()) {  
                for (String production : productions.get(A)) {  
                    String[] symbols = production.split("\\s+");  
                    for (int i = 0; i < symbols.length; i++) {  
                        String B = symbols[i];  
                        if (productions.containsKey(B)) {  
                             
                            Set<String> firstBeta = new HashSet<>();  
                            boolean allNullable = true;  
                            for (int j = i + 1; j < symbols.length; j++) {  
                                Set<String> firstS = getFirst(symbols[j], productions, firstSets);  
                                 
                                for (String s : firstS) {  
                                    if (!s.equals("z")) {  
                                        firstBeta.add(s);  
                                    }  
                                }  
                                if (!firstS.contains("z")) {  
                                    allNullable = false;  
                                    break;  
                                }  
                            }  
                            int before = followSets.get(B).size();  
                            followSets.get(B).addAll(firstBeta);  
                             
                            if (allNullable) {                                   
                                followSets.get(B).addAll(followSets.get(A));  
                            }  
                            if (followSets.get(B).size() > before) {  
                                changed = true;  
                            }  
                        }  
                    }  
                }  
            }  
        } while (changed);  
  
        System.out.println("\nFOLLOW sets:");  
        for (String nt : productions.keySet()) {  
            System.out.println("FOLLOW(" + nt + ") = " +  followSets.get(nt));  
        }  
    }  
  
    public static Set<String> getFirst(String symbol, HashMap<String, ArrayList<String>> productions, HashMap<String, Set<String>> firstSets) {  
        if (!productions.containsKey(symbol)) {  
            Set<String> term = new HashSet<>();  
            term.add(symbol);  
            return term;  
        }  
        return firstSets.get(symbol);  
    }  
  
    public static Set<String> computeFirst(String symbol, HashMap<String, ArrayList<String>> productions, HashMap<String, Set<String>> firstSets) {  
        if (!productions.containsKey(symbol)) {  
            Set<String> first = new HashSet<>();  
            first.add(symbol);  
            return first;  
        }  
  
        if (!firstSets.get(symbol).isEmpty()) {  
            return firstSets.get(symbol);  
        }  
        Set<String> result = new HashSet<>();  
        for (String production : productions.get(symbol)) {  
            String[] parts = production.split("\\s+");  
            Set<String> prodFirst = new HashSet<>();  
            boolean productionNullable = true;  
            for (String part : parts) {  
                Set<String> firstPart = productions.containsKey(part) ? computeFirst(part, productions, firstSets) : new HashSet<>(Arrays.asList(part));       
                for (String s : firstPart) {  
                    if (!s.equals("z")) {  
                        prodFirst.add(s);  
                    }  
                }  
                if (!firstPart.contains("z")) {  
                    productionNullable = false;  
                    break;  
                }  
            }  
            if (productionNullable) {  
                prodFirst.add("z");  
            }  
            result.addAll(prodFirst);  
        }  
        firstSets.put(symbol, result);  
        return result;  
    }  
} 
