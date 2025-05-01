//First of Grammar with User-Input
import java.util.*; 
import java.io.*; 
public class FIRSTUserInput { 
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
    public static void main(String[] args) throws IOException { 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        System.out.println("Enter number of non-terminals:"); 
        int n = Integer.parseInt(br.readLine()); 
        HashMap<String, ArrayList<String>> productions = new HashMap<>(); 
        for (int i = 0; i < n; i++) { 
            System.out.println("Enter the non-terminal:"); 
            String nt = br.readLine().trim(); 
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
            computeFirst(nt, productions, firstSets, new HashSet<>()); 
        } 
        System.out.println("\nFIRST sets:"); 
        for (String nt : productions.keySet()) { 
            System.out.println("FIRST(" + nt + ") = " + firstSets.get(nt)); 
        } 
    } 
}
