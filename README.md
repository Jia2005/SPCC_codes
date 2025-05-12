# 🚀 SPCC Code Repository

Hey there! 👋 Welcome to my compiler construction repo where I keep all my college assignment code. 

This is my personal collection of lexers, parsers, and other compiler components created for my Systems Programming and Compiler Construction course.

## 📂 What's Inside

This repo contains:

### Lex Codes
- **Charcount** - Character counting utility
- **Count** - Counts characters, words, etc.
- **Hello** - Generalized hello greeting
- **Lexical_Analyzer** - For tokenizing input code
- **Personal** - Personalized hello based on user's name input
- **Vowel** - Identifies if character input is vowel or consonant
- **Word** - Identifies if input is word or number

### Parsing & Syntax Analysis
- **Parser** - Basic parsing implementation
- **LL(1)** - LL(1) parsing table implementation
- **Left_Recursion** - Handles left recursion elimination
- **Lexical_Analyzer** - Lexical analysis implementation

### Macro Processing & Assembly
- **Macroprocessor** - Handles macro expansions
- **Assembler(Pass-1)** & **Assembler(Pass-2)** - Two-pass assembler implementation

### Other Components
- **First(Static_Input)** & **First(User_Input)** - Calculates FIRST sets for given grammar
- **Follow(Static_Input)** & **Follow(User_Input)** - Calculates FOLLOW sets for given grammar
- **First_Follow** - Combined implementation
- **TAC** - Three Address Code generation
- **Code_Optimization** - Optimized code for given TAC

## 🚦 How to Run

### For Java files:
```bash
# Compile the Java file
javac Filename.java

# Run the compiled program
java Filename
```

### For Lex files:
```bash
# Generate C code from Lex file
flex Filename.lex

# Compile the generated C code
gcc lex.yy.c -lfl

# Run the compiled program
./a.out
```

## 💬 Issues or Suggestions?

Found an issue or have suggestions for improvements? Feel free to open a pull request or reach out!

Happy coding! 💻✨
