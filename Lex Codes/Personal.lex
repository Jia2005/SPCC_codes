%{ 
#include <stdio.h> 
char Name[10]; 
%} 
%% 
\n { 
} 
%% 
printf("\nHi....%s...Good Morning...\n", Name); 
int main() { 
char opt; 
do { 
printf("\nWhat is your name: "); 
scanf("%s", Name); 
getchar(); 
printf("\nPress any key to continue (Y/y): "); 
scanf("%c", &opt); 
yylex(); 
} while ((opt == 'Y') || (opt == 'y')); 
return 0; 
}
