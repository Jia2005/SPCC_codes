//Follow of Grammar with Static Input
import java.util.*; 
public class FOLLOW { 
    public static void main(String[] args) { 
        HashMap<String, ArrayList<String>> productions = new HashMap<>(); 
         
        ArrayList<String> prodS = new ArrayList<>(); 
        prodS.add("A B"); 
        prodS.add("b C"); 
        productions.put("S", prodS); 
         
        ArrayList<String> prodA = new ArrayList<>(); 
        prodA.add("a"); 
        prodA.add("z"); 
        productions.put("A", prodA); 
         
        ArrayList<String> prodB = new ArrayList<>(); 
        prodB.add("b C"); 
        prodB.add("d"); 
        productions.put("B", prodB); 
         
        ArrayList<String> prodC = new ArrayList<>(); 
        prodC.add("c"); 
        productions.put("C", prodC); 
 
        String startSymbol = "S"; 
 
        HashMap<String, Set<String>> firstSets = new HashMap<>(); 
         
        Set<String> firstS = new HashSet<>(); 
        firstS.add("a"); 
        firstS.add("b"); 
        firstS.add("d"); 
        firstSets.put("S", firstS); 
         
        Set<String> firstA = new HashSet<>(); 
        firstA.add("a"); 
        firstA.add("z"); 
        firstSets.put("A", firstA); 
         
        Set<String> firstB = new HashSet<>(); 
        firstB.add("b"); 
        firstB.add("d"); 
        firstSets.put("B", firstB); 
         
        Set<String> firstC = new HashSet<>(); 
        firstC.add("c"); 
        firstSets.put("C", firstC); 
 
        System.out.println("Input FIRST sets:"); 
        for (String nt : firstSets.keySet()) { 
            System.out.println("FIRST(" + nt + ") = " + firstSets.get(nt)); 
        } 
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
