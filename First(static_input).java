import java.util.*; 
public class FIRST { 
public static Set<String> computeFirst(String nt, HashMap<String, ArrayList<String>> 
productions, HashMap<String, Set<String>> firstSets, Set<String> visited) { 
        if (!productions.containsKey(nt)) { 
            Set<String> first = new HashSet<>(); 
            first.add(nt); 
            return first; 
        } 
        if (firstSets.containsKey(nt) && !firstSets.get(nt).isEmpty()) { 
            return firstSets.get(nt); 
        } 
        if (visited.contains(nt)) { 
            return new HashSet<>(); 
        } 
        visited.add(nt); 
        Set<String> result = new HashSet<>(); 
        for (String production : productions.get(nt)) { 
            String[] parts = production.trim().split(" "); 
            boolean allNullable = true; 
            for (String symbol : parts) { 
                Set<String> first = computeFirst(symbol, productions, firstSets, visited); 
                result.addAll(first); 
                if (!first.contains("z")) { 
                    allNullable = false; 
                    break; 
                } else { 
                    result.remove("z"); 
                } 
            } 
 
            if (allNullable) { 
                result.add("z"); 
            } 
        } 
 
        visited.remove(nt); 
        firstSets.put(nt, result); 
        return result; 
    } 
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
 
        HashMap<String, Set<String>> firstSets = new HashMap<>(); 
        for (String nt : productions.keySet()) { 
            firstSets.put(nt, new HashSet<>()); 
        } 
        for (String nt : productions.keySet()) { 
            computeFirst(nt, productions, firstSets, new HashSet<>()); 
        } 
        System.out.println("FIRST sets:"); 
        for (String nt : productions.keySet()) { 
            System.out.println("FIRST(" + nt + ") = " + firstSets.get(nt)); 
        } 
    } 
}
