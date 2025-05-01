//Follow of Grammar with User-Input
import java.util.*; 
import java.io.*; 
public class FOLLOW  
{ 
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
                System.out.println("Enter production " + (j + 1) + " (use spaces between symbols, 
and use 'z' for ε):"); 
                String prod = br.readLine().trim(); 
                prodList.add(prod); 
            } 
            productions.put(nt, prodList); 
        } 
        System.out.println("\nNow enter FIRST sets for each non-terminal:"); 
        HashMap<String, Set<String>> firstSets = new HashMap<>(); 
         
        for (String nt : productions.keySet()) { 
            System.out.println("Enter number of symbols in FIRST(" + nt + "):"); 
            int numSymbols = Integer.parseInt(br.readLine()); 
            Set<String> firstSet = new HashSet<>(); 
             
            for (int i = 0; i < numSymbols; i++) { 
                System.out.println("Enter symbol " + (i + 1) + " in FIRST(" + nt + "):"); 
                String symbol = br.readLine().trim(); 
                firstSet.add(symbol); 
            } 
            firstSets.put(nt, firstSet);         
        } 
        System.out.println("\nInput FIRST sets:"); 
        for (String nt : firstSets.keySet()) { 
            System.out.println("FIRST(" + nt + ") = " + firstSets.get(nt)); 
        } 
        // Compute FOLLOW sets using the input FIRST sets 
        HashMap<String, Set<String>> followSets = new HashMap<>(); 
        for (String nt : productions.keySet()) { 
            followSets.put(nt, new HashSet<>()); 
        } 
        followSets.get(startSymbol).add("$"); 
        boolean changed; 
        do { 
            changed = false; 
            for (String nt : productions.keySet()) { 
                for (String production : productions.get(nt)) { 
                    String[] symbols = production.trim().split(" "); 
                    for (int i = 0; i < symbols.length; i++) { 
                        String symbol = symbols[i]; 
                        if (productions.containsKey(symbol)) { 
                            Set<String> follow = new HashSet<>(); 
                            boolean allNullable = true; 
                            for (int j = i + 1; j < symbols.length; j++) { 
                                String nextSymbol = symbols[j]; 
                                Set<String> firstOfNext; 
                                if (productions.containsKey(nextSymbol)) { 
                                    firstOfNext = firstSets.get(nextSymbol); 
                                } else { 
                                    firstOfNext = new HashSet<>(); 
                                    firstOfNext.add(nextSymbol); 
                                } 
                                Set<String> firstWithoutEpsilon = new HashSet<>(firstOfNext); 
                                firstWithoutEpsilon.remove("z"); 
                                follow.addAll(firstWithoutEpsilon); 
                                if (!firstOfNext.contains("z")) { 
                                    allNullable = false; 
                                    break; 
                                } 
                            } 
                            if (allNullable || i == symbols.length - 1) { 
                                follow.addAll(followSets.get(nt)); 
                            } 
                            Set<String> currentFollow = followSets.get(symbol); 
                            if (!currentFollow.containsAll(follow)) { 
                                currentFollow.addAll(follow); 
                                followSets.put(symbol, currentFollow); 
                                changed = true; 
                            } 
                        } 
                    } 
                } 
            } 
        } while (changed); 
        System.out.println("\nFOLLOW sets:"); 
        for (String nt : followSets.keySet()) { 
            System.out.println("FOLLOW(" + nt + ") = " + followSets.get(nt)); 
        } 
    } 
} 
