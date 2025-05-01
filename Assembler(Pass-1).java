//Multi-Pass Assembler 
//PASS_1
import java.util.*; 
public class Pass1 { 
    private static final Map<String, String[]> MOT = new HashMap<>(); 
    private static final Map<String, String> POT = new HashMap<>(); 
    private static final Map<String, Integer[]> symbolTable = new HashMap<>(); 
    private static final Map<String, Integer[]> literalTable = new HashMap<>(); 
    private static final Map<Integer, String[]> baseTable = new HashMap<>(); 
    private static List<String> intermediateCode = new ArrayList<>(); 
    private static int locationCounter = 0; 
    static { 
        MOT.put("L", new String[]{"01011000", "4", "RX", "--"}); 
        MOT.put("A", new String[]{"01011010", "4", "RX", "--"}); 
        MOT.put("ST", new String[]{"01010000", "4", "RX", "--"}); 
        POT.put("START", "PA_START"); 
        POT.put("USING", "PA_USING"); 
        POT.put("DC", "PA_DC"); 
        POT.put("DS", "PA_DS"); 
        POT.put("END", "PA_END"); 
        for (int i = 1; i <= 15; i++) { 
            baseTable.put(i, new String[]{"N", ""}); 
        } 
    } 
    public static void main(String[] args) { 
        String[] input = { 
            "PRG START 0", 
            "USING *, 15", 
            "L 1, FOUR", 
            "A 1, FIVE", 
            "A 1, =F'2'", 
            "ST 1, TEMP", 
            "FOUR DC F'4'", 
            "FIVE DC F'5'",  
            "TEMP DS 1F", 
            "END" 
        }; 
        firstPass(input); 
        displayResults(); 
    } 
    private static void firstPass(String[] input) { 
        String programName = ""; 
        for (String line : input) { 
            String[] tokens = line.split("\\s+");             
            if (tokens.length <= 1) continue;             
            int tokenIndex = 0; 
            String label = null; 
            String operation;             
            if (!MOT.containsKey(tokens[0]) && !POT.containsKey(tokens[0])) { 
                label = tokens[tokenIndex++]; 
            }             
            if (tokenIndex >= tokens.length) continue;             
            operation = tokens[tokenIndex++];             
            if (operation.equals("START")) { 
                programName = label; 
                locationCounter = Integer.parseInt(tokens[tokenIndex]); 
                symbolTable.put(label, new Integer[]{locationCounter, 1, 1}); 
            }  
            else if (operation.equals("USING")) { 
                String baseReg = tokens[tokenIndex + 1]; 
                baseTable.put(Integer.parseInt(baseReg), new String[]{"Y", "00"}); 
            } 
            else if (operation.equals("END")) { 
                for (Map.Entry<String, Integer[]> entry : literalTable.entrySet()) { 
                    entry.getValue()[0] = locationCounter; 
                    locationCounter += 4; 
                } 
            } 
            else if (MOT.containsKey(operation)) { 
                String operand = (tokenIndex < tokens.length) ? tokens[tokenIndex] : ""; 
                String address = (tokenIndex + 1 < tokens.length) ? tokens[tokenIndex + 1] : "";                 
                intermediateCode.add(locationCounter + " " + operation + " " + operand + ", - (0,15)");                 
                if (address.startsWith("=")) { 
                    if (!literalTable.containsKey(address)) { 
                        literalTable.put(address, new Integer[]{-1, 4, 1}); 
                    } 
                }                 
                locationCounter += 4; 
            } 
            else if (operation.equals("DC")) { 
                if (label != null) { 
                    symbolTable.put(label, new Integer[]{locationCounter, 4, 1}); 
                }                 
                String value = tokens[tokenIndex].substring(2, tokens[tokenIndex].length() - 1); 
                locationCounter += 4; 
            } 
            else if (operation.equals("DS")) { 
                if (label != null) { 
                    symbolTable.put(label, new Integer[]{locationCounter, 4, 1}); 
                }                 
                String size = tokens[tokenIndex].substring(0, tokens[tokenIndex].length() - 1); 
                int bytes = Integer.parseInt(size) * 4; 
                locationCounter += bytes; 
            } 
        } 
    } 
    private static void displayResults() { 
        System.out.println("First Pass:"); 
        System.out.println("Relative"); 
        System.out.println("Address"); 
        System.out.println("Mnemonic Instruction");         
        for (String line : intermediateCode) { 
            System.out.println(line); 
        }         
        System.out.println("\nMOT"); 
        System.out.println("Mnemonic\topcode\tBinary\tLength\tFormat\tNot Used in this\n\t\tOpcode\t\t\tdesign"); 
        for (Map.Entry<String, String[]> entry : MOT.entrySet()) { 
            System.out.println(entry.getKey() + "\t" + entry.getValue()[0] + "\t" + entry.getValue()[1] + "\t" + entry.getValue()[2] + "\t" + entry.getValue()[3]); 
        }         
        System.out.println("\nPOT"); 
        System.out.println("Pseudo-Op\tAddress of routine"); 
        for (Map.Entry<String, String> entry : POT.entrySet()) { 
            System.out.println(entry.getKey() + "\t" + entry.getValue()); 
        }         
        System.out.println("\nSymbol Table"); 
        System.out.println("Symbol\tValue\tLength\tRelocation"); 
        for (Map.Entry<String, Integer[]> entry : symbolTable.entrySet()) { 
            System.out.println(entry.getKey() + "\t" + entry.getValue()[0] + "\t" + entry.getValue()[1] + "\t" + (entry.getValue()[2] == 1 ? "R" : "A")); 
        }         
        System.out.println("\nLiteral Table"); 
        System.out.println("Symbol\tValue\tLength\tRelocation"); 
        System.out.println("(8 Bytes)\t(4 Bytes)\t(1 Byte)\t(1 Byte)"); 
        for (Map.Entry<String, Integer[]> entry : literalTable.entrySet()) { 
            System.out.println(entry.getKey() + "\t" + entry.getValue()[0] + "\t" + entry.getValue()[1] + "\t" + (entry.getValue()[2] == 1 ? "R" : "A")); 
        }         
        System.out.println("\nBase Table"); 
        System.out.println("Availability\nIndicator\tContent of\n\t\tBase Register"); 
        for (int i = 1; i <= 15; i++) { 
            String[] info = baseTable.get(i); 
            System.out.println(i + "\t" + info[0] + "\t" + info[1]); 
        } 
    }
}
