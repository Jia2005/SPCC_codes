%{ 
#include <stdio.h> 
int line_count = 0; 
int word_count = 0; 
int char_count = 0; 
%} 
%% 
\n              
character 
[ \t]+          
not words 
[^\t\n ]+       
{ line_count++; char_count++; }    // Newline: count both line and 
{ char_count += yyleng; }          
// Spaces and tabs: count characters, 
{ word_count++; char_count += yyleng; }  // Word: count both 
word and characters 
.               
%% 
{ char_count++; }                  
int main() { 
// Any other character 
printf("Enter your text (end with Ctrl+D for Linux/macOS or Ctrl+Z on 
Windows):\n"); 
yylex(); 
printf("\n--- Summary ---\n"); 
printf("Lines: %d\n", line_count); 
printf("Words: %d\n", word_count); 
printf("Characters: %d\n", char_count); 
return 0; 
} 
int yywrap() { 
return 1; 
}
