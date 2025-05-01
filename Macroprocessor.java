//Multi-Pass Macroprocessor
import java.util.*; 
public class MacroProcessor { 
    private List<String> mdt;   
    private Map<String, Integer> mnt;  
    private Map<Integer, String> ala;  
    private List<String> pass1Output;   
    private List<String> pass2Output; 
    private boolean inMacroDef; 
    private String currentMacro; 
    private int lineCounter; 
    public MacroProcessor() { 
        this.mdt = new ArrayList<>(); 
        this.mnt = new HashMap<>(); 
        this.ala = new HashMap<>(); 
        this.pass1Output = new ArrayList<>(); 
        this.pass2Output = new ArrayList<>(); 
        this.inMacroDef = false; 
        this.currentMacro = null; 
        this.lineCounter = 0; 
    } 
    public List<String> firstPass(List<String> inputLines) { 
        this.lineCounter = 0; 
        for (String line : inputLines) { 
            this.lineCounter++; 
            line = line.trim(); 
            if (line.isEmpty()) {  
                continue; 
            } 
            if (line.equals("MACRO")) { 
                this.inMacroDef = true; 
                continue; 
            } 
            
            if (this.inMacroDef) { 
                if (line.equals("MEND")) { 
                    this.inMacroDef = false; 
                    this.mdt.add(line); 
                    continue; 
                }                
                if (this.currentMacro == null) { 
                    String[] parts = line.split("\\s+"); 
                    if (parts[0].contains("&LAB")) { 
                        String macroName = parts[1]; 
                        this.currentMacro = macroName; 
                        this.mnt.put(macroName, this.mdt.size()); 
                    } else { 
                        String macroName = parts[0]; 
                        this.currentMacro = macroName; 
                        this.mnt.put(macroName, this.mdt.size()); 
                    } 
                } 
                this.mdt.add(line); 
            } else { 
                String[] parts = line.split("\\s+"); 
                String operation = parts.length > 1 ? parts[1] : parts[0]; 
                
                if (this.mnt.containsKey(operation)) { 
                    this.pass1Output.add(line); 
                } else { 
                    this.pass1Output.add(line); 
                } 
            } 
        } 
        
        return this.pass1Output; 
    } 
    public List<String> secondPass() { 
        for (String line : this.pass1Output) { 
            String[] parts = line.split("\\s+"); 
            
            if (parts.length > 1 && this.mnt.containsKey(parts[1])) { 
                String label = parts[0]; 
                String macroName = parts[1]; 
                
                List<String> args = new ArrayList<>(); 
                if (parts.length > 2) { 
                    String argsStr = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length)); 
                    String[] argArray = argsStr.split(","); 
                    for (String arg : argArray) { 
                        args.add(arg.trim()); 
                    } 
                }                
                this.ala = new HashMap<>(); 
                this.ala.put(0, label); 
                for (int i = 0; i < args.size(); i++) { 
                    this.ala.put(i + 1, args.get(i)); 
                } 
                expandMacro(macroName); 
            } else { 
                this.pass2Output.add(line); 
            } 
        }        
        return this.pass2Output; 
    } 
    private void expandMacro(String macroName) { 
        int mdtIndex = this.mnt.get(macroName); 
        int i = mdtIndex + 1;        
        while (i < this.mdt.size() && !this.mdt.get(i).equals("MEND")) { 
            String line = this.mdt.get(i); 
            String expandedLine = substituteParams(line); 
            this.pass2Output.add(expandedLine); 
            i++; 
        } 
    } 
    private String substituteParams(String line) { 
        String result = line; 
       if (result.contains("&LAB")) { 
            result = result.replace("&LAB", this.ala.getOrDefault(0, "")); 
        }        
        for (int i = 1; i < 10; i++) {   
            String argPattern = "&ARG" + i; 
            if (result.contains(argPattern)) { 
                result = result.replace(argPattern, this.ala.getOrDefault(i, "")); 
            } 
        }        
        for (int i = 0; i < 10; i++) {   
            String paramPattern = "#" + i; 
            if (result.contains(paramPattern)) { 
                result = result.replace(paramPattern, this.ala.getOrDefault(i, "")); 
            } 
        }        
        return result; 
    } 
    public void printTabularFormat(List<String> inputProgram) { 
        System.out.println("\nSample Program"); 
        for (String line : inputProgram) { 
            System.out.println(line); 
        } 
        
        System.out.println("\nPass 1 Output:"); 
        for (String line : this.pass1Output) { 
            System.out.println(line); 
        } 
        System.out.println("\nPass 1 Data Structures/Database used:");        
        System.out.println("\n1. Macro Definition Table (MDT)"); 
        System.out.printf("%-6s %s%n", "Index", "Name"); 
        System.out.println("-".repeat(50)); 
        for (int i = 0; i < this.mdt.size(); i++) { 
            String entry = this.mdt.get(i); 
            String displayEntry = entry; 
            for (int j = 1; j < 10; j++) { 
                displayEntry = displayEntry.replace("&ARG" + j, "#" + j); 
            }            
            if (i == 0) {  
                System.out.printf("%-6d %s%n", i + 1, displayEntry); 
            } else { 
                if (displayEntry.contains("&LAB")) { 
                    displayEntry = displayEntry.replace("&LAB", "#0"); 
                } 
                System.out.printf("%-6d %s%n", i + 1, displayEntry); 
            } 
        }        
        System.out.println("\n2. Macro Name Table (MNT)"); 
        System.out.printf("%-6s %-10s %-10s%n", "Index", "Name", "MDT Index"); 
        System.out.println("-".repeat(30)); 
        int index = 1; 
        for (Map.Entry<String, Integer> entry : this.mnt.entrySet()) { 
            System.out.printf("%-6d %-10s %-10d%n", index, entry.getKey(), entry.getValue() + 1); 
            index++; 
        } 
        
        System.out.println("\n3. Argument List Array (ALA)"); 
        System.out.printf("%-6s %s%n", "Index", "Arguments"); 
        System.out.println("-".repeat(20)); 
        for (int i = 0; i < 4; i++) {   
            System.out.printf("%-6d%n", i); 
        }        
        System.out.println("\nPass 2 Output:"); 
        for (String line : this.pass2Output) { 
            System.out.println(line); 
        }        
        System.out.println("\n1. Argument List Array (ALA)"); 
        System.out.printf("%-6s %s%n", "Index", "Arguments"); 
        System.out.println("-".repeat(25)); 
        int maxKey = 3; 
        if (!this.ala.isEmpty()) { 
            maxKey = Collections.max(this.ala.keySet()); 
        } 
        for (int i = 0; i <= maxKey; i++) { 
            System.out.printf("%-6d %s%n", i, this.ala.getOrDefault(i, "")); 
        } 
    } 
    public static void main(String[] args) { 
        List<String> inputProgram = Arrays.asList( 
            "PRG START 0", 
            "MACRO", 
            "&LAB INCR &ARG1, &ARG2, &ARG3", 
            "&LAB A 1, &ARG1", 
            "A 2, &ARG2", 
            "A 3, &ARG3", 
            "MEND", 
            "LOOP1 INCR DATA1, DATA2, DATA3", 
            "DATA1 DC F'5'", 
            "DATA2 DC F'10'", 
            "DATA3 DC F'15'" 
        );        
        MacroProcessor processor = new MacroProcessor(); 
        processor.firstPass(inputProgram); 
        processor.secondPass(); 
        processor.printTabularFormat(inputProgram); 
    } 
