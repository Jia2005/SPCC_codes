%{  
%{  
#include <stdio.h>  
int flag;  
void check(int, char*);  
%}  
%%  
[aeiou]  { flag = 1; check(flag, yytext); }  
.    
{ flag = 0; check(flag, yytext); }  
%%  
int main() {  
printf("Enter any character: ");  
yylex();  
return 0;  
}  
void check(int a, char* t) {  
if(a == 1) {  
printf("The input character %s is a vowel.\n", t);  
} else {  
printf("The input character %s is a consonant.\n", t);  
}  
}
