//Multi-Pass Assembler
//Pass-2
import java.util.*; 
public class Pass2 { 
    private static final Map<String, String> MOT = new HashMap<>(); 
    private static final Map<String, Integer> symbolTable = new HashMap<>(); 
    private static final Map<String, Integer> literalTable = new HashMap<>(); 
    private static final Map<Integer, Boolean> baseTable = new HashMap<>(); 
    private static List<String> outputCode = new ArrayList<>(); 
    static { 
        MOT.put("L", "01011000"); 
        MOT.put("A", "01011010"); 
        MOT.put("ST", "01010000"); 
        symbolTable.put("PRG", 0); 
        symbolTable.put("FOUR", 16); 
        symbolTable.put("FIVE", 20); 
        symbolTable.put("TEMP", 24); 
        literalTable.put("=F'2'", 28); 
        for (int i = 1; i <= 15; i++) { 
            baseTable.put(i, false); 
        } 
        baseTable.put(15, true); 
    } 
    public static void main(String[] args) { 
        String[] intermediateCode = { 
            "0 L 1, - (0,15)", 
            "4 A 1, - (0,15)", 
            "8 A 1, - (0,15)", 
            "12 ST 1, - (0,15)", 
            "16 4", 
            "20 5", 
            "24 " 
        }; 
        secondPass(intermediateCode); 
        displayResults(); 
    } 
    private static void secondPass(String[] intermediateCode) { 
        for (String line : intermediateCode) { 
            if (line.trim().isEmpty()) continue; 
            String[] tokens = line.split("\\s+"); 
            int address = Integer.parseInt(tokens[0]);             
            if (tokens.length >= 3 && MOT.containsKey(tokens[1])) { 
                String operation = tokens[1]; 
                String operand1 = tokens[2]; 
                String operand2 = tokens[4].replace("-", "");                 
                int targetAddress = resolveOperand(tokens[3]);                 
                outputCode.add(address + " " + operation + " " + operand1 + ", " + targetAddress + " (0,15)"); 
            } else if (tokens.length == 2) { 
                outputCode.add(address + " " + tokens[1]); 
            } else { 
                outputCode.add(line); 
            } 
        } 
    } 
    private static int resolveOperand(String operand) { 
        operand = operand.replaceAll("[^a-zA-Z0-9=']", "");         
        if (symbolTable.containsKey(operand)) { 
            return symbolTable.get(operand); 
        }         
        if (literalTable.containsKey(operand)) { 
            return literalTable.get(operand); 
        }         
        try { 
            return Integer.parseInt(operand); 
        } catch (NumberFormatException e) { 
            return 0; 
        } 
    } 
    private static void displayResults() { 
        System.out.println("Second Pass Output:"); 
        System.out.println("Relative Address\tMnemonic Instruction");         
        for (String line : outputCode) { 
            System.out.println(line); 
        } 
    } 
}
