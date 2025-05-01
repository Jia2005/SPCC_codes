//Parser to check if we accept or reject the input string
import java.util.*; 
class LL1Parser { 
    static class Rule { 
        String left; 
        List<String> right; 
        Rule(String left, List<String> right) { 
            this.left = left; 
            this.right = right; 
        } 
    } 
    private Map<String, List<Rule>> grammar; 
    private Map<String, Set<String>> firstSet; 
    private Map<String, Set<String>> followSet; 
    private Map<String, Map<String, List<String>>> parseTable; 
    private Set<String> nonTerminals; 
    private Set<String> terminals; 
    private String startSymbol; 
    public LL1Parser(List<Rule> rules, String startSymbol) { 
        this.grammar = new HashMap<>(); 
        this.firstSet = new HashMap<>(); 
        this.followSet = new HashMap<>(); 
        this.parseTable = new HashMap<>(); 
        this.nonTerminals = new HashSet<>(); 
        this.terminals = new HashSet<>(); 
        this.startSymbol = startSymbol; 
        for (Rule rule : rules) { 
            grammar.computeIfAbsent(rule.left, k -> new ArrayList<>()).add(rule); 
            nonTerminals.add(rule.left); 
            for (String symbol : rule.right) { 
                if (!Character.isUpperCase(symbol.charAt(0))) { 
                    terminals.add(symbol); 
                } 
            } 
        } 
 
        for (String nonTerminal : nonTerminals) { 
            firstSet.put(nonTerminal, new HashSet<>()); 
            followSet.put(nonTerminal, new HashSet<>()); 
        } 
        firstSet.put("EPSILON", new HashSet<>()); 
        followSet.put(startSymbol, new HashSet<>(Collections.singleton("$"))); 
        computeFirstSets(); 
        computeFollowSets(); 
        buildParseTable(); 
    } 
    private void computeFirstSets() { 
        boolean updated; 
        do { 
            updated = false; 
            for (String nonTerminal : grammar.keySet()) { 
                for (Rule rule : grammar.get(nonTerminal)) { 
                    Set<String> currentFirstSet = getFirstOfRightSide(rule.right); 
                    if (firstSet.get(nonTerminal).addAll(currentFirstSet)) { 
                        updated = true; 
                    } 
                } 
            } 
        } while (updated); 
    } 
    private Set<String> getFirstOfRightSide(List<String> rightSide) { 
        Set<String> first = new HashSet<>(); 
        for (String symbol : rightSide) { 
            if (terminals.contains(symbol)) { 
                first.add(symbol); 
                break; 
            } else { 
                first.addAll(firstSet.get(symbol)); 
                if (!firstSet.get(symbol).contains("EPSILON")) { 
                    break; 
                } 
            } 
        } 
        return first; 
    } 
    private void computeFollowSets() { 
        boolean updated; 
        do { 
            updated = false; 
            for (String nonTerminal : grammar.keySet()) { 
                for (Rule rule : grammar.get(nonTerminal)) { 
                    List<String> rightSide = rule.right; 
                    for (int i = 0; i < rightSide.size(); i++) { 
                        String symbol = rightSide.get(i); 
                        if (nonTerminals.contains(symbol)) { 
                            Set<String> followOfCurrentSymbol = (i == rightSide.size() - 1) 
                                    ? followSet.get(nonTerminal) 
                                    : getFirstOfRightSide(rightSide.subList(i + 1, rightSide.size())); 
                            if (followSet.get(symbol).addAll(followOfCurrentSymbol)) { 
                                updated = true; 
                            } 
                        } 
                    } 
                } 
            } 
        } while (updated); 
    } 
    private void buildParseTable() { 
        for (String nonTerminal : grammar.keySet()) { 
            parseTable.put(nonTerminal, new HashMap<>()); 
            for (Rule rule : grammar.get(nonTerminal)) { 
                Set<String> firstSetOfRule = getFirstOfRightSide(rule.right); 
                for (String terminal : firstSetOfRule) { 
                    if (!terminal.equals("EPSILON")) { 
                        parseTable.get(nonTerminal).put(terminal, rule.right); 
                    } 
                } 
                if (firstSetOfRule.contains("EPSILON")) { 
                    for (String followSymbol : followSet.get(nonTerminal)) { 
                        parseTable.get(nonTerminal).put(followSymbol, new ArrayList<>()); 
                    } 
                } 
            } 
        } 
    } 
    public boolean parse(String input) { 
        Stack<String> stack = new Stack<>(); 
        stack.push("$"); 
        stack.push(startSymbol); 
        input += "$"; 
        int index = 0; 
        while (!stack.isEmpty()) { 
            String top = stack.peek(); 
            String currentSymbol = String.valueOf(input.charAt(index)); 
            if (terminals.contains(top)) { 
                if (top.equals(currentSymbol)) { 
                    stack.pop(); 
                    index++; 
                } else { 
                    return false; 
                } 
            } else { 
                if (parseTable.get(top).containsKey(currentSymbol)) { 
                    stack.pop(); 
                    List<String> production = parseTable.get(top).get(currentSymbol); 
                    if (!production.isEmpty()) { 
                        for (int i = production.size() - 1; i >= 0; i--) { 
                            stack.push(production.get(i)); 
                        } 
                    } 
                } else { 
                    return false; 
                } 
            } 
        } 
        return index == input.length(); 
    } 
    public static List<Rule> readGrammar() { 
        Scanner scanner = new Scanner(System.in); 
        List<Rule> rules = new ArrayList<>(); 
        System.out.println("Enter grammar rules (in the form: A -> B C, or type 'done' to finish):"); 
        while (true) { 
            String input = scanner.nextLine().trim(); 
            if (input.equalsIgnoreCase("done")) break; 
            String[] parts = input.split("->"); 
            if (parts.length != 2) { 
                System.out.println("Invalid format, try again."); 
                continue; 
            } 
            String left = parts[0].trim(); 
            String[] rightParts = parts[1].trim().split("\\s+"); 
            List<String> right = Arrays.asList(rightParts); 
            rules.add(new Rule(left, right)); 
         
        return rules; 
    } 
    public static String readInputString() { 
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Enter the string to parse:"); 
        return scanner.nextLine().trim(); 
    } 
    public static void main(String[] args) { 
        List<Rule> grammar = readGrammar(); 
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Enter the start symbol:"); 
        String startSymbol = scanner.nextLine().trim(); 
        LL1Parser parser = new LL1Parser(grammar, startSymbol); 
        String inputString = readInputString(); 
        boolean result = parser.parse(inputString); 
        if (result) { 
            System.out.println("The string is accepted by the grammar."); 
        } else { 
            System.out.println("The string is not accepted by the grammar."); 
        } 
    } 
} 
