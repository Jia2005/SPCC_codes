%{  
#include <stdio.h>  
#include <string.h>  
#define MAX_SIZE 100   
char keywords[MAX_SIZE][50], variables[MAX_SIZE][50], constants[MAX_SIZE][50],  
     operators[MAX_SIZE][50], special_chars[MAX_SIZE][50];  
int k = 0, v = 0, c = 0, o = 0, s = 0;   
void print_summary() {  
    printf("\n--- Summary ---\n");  
    printf("Keywords: ");  
    for(int i = 0; i < k; i++) printf("%s ", keywords[i]);  
    printf("\nVariables: ");  
    for(int i = 0; i < v; i++) printf("%s ", variables[i]);  
    printf("\nConstants: ");  
    for(int i = 0; i < c; i++) printf("%s ", constants[i]);  
    printf("\nOperators: ");  
    for(int i = 0; i < o; i++) printf("%s ", operators[i]);  
    printf("\nSpecial Characters: ");  
    for(int i = 0; i < s; i++) printf("%s ", special_chars[i]);  
    printf("\n");  
}  
%}  
  
%%  
"int"|"float"|"double"|"char"|"return"|"if"|"else"|"while"|"for" {  
    strcpy(keywords[k++], yytext);  
    printf("[KEYWORD] %s\n", yytext);  
}   
[a-zA-Z_][a-zA-Z0-9_]* {  
strcpy(variables[v++], yytext);  
printf("[VARIABLE] %s\n", yytext);  
}   
[0-9]+ {  
strcpy(constants[c++], yytext);  
printf("[CONSTANT] %s\n", yytext);  
}   
"=="|"!="|"<="|">="|"&&"|"||"|"+"|"-"|"*"|"/"|"=" {  
strcpy(operators[o++], yytext);  
printf("[OPERATOR] %s\n", yytext);  
}  
[{}()\[\],;@#&] {  
strcpy(special_chars[s++], yytext);  
printf("[SPECIAL CHARACTER] %s\n", yytext);  
}  
[ \t\n] { /* Ignore whitespace */ }  
. { printf("[UNKNOWN] %s\n", yytext); }  
%%  
int main() {  
printf("Enter an expression: ");  
yylex();  
print_summary();  
return 0;  
}  
int yywrap() {  
return 1;  
}
